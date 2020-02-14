package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.Connection;
import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected=false;

    public static void main(String[] args) {
        Client client=new Client();
        client.run();
    }

    public void run(){
        SocketThread socketThread=getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        synchronized (this){
            try {
                this.wait();
                if (clientConnected==true){
                    ConsoleHelper.writeMessage("Соединение установлено.");
                    ConsoleHelper.writeMessage("Для выхода наберите команду 'exit'.");
                } else {
                    ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");
                }
                while (clientConnected==true){
                    String message=ConsoleHelper.readString();
                    if (message.equals("exit")){
                        break;
                    }
                    if (shouldSendTextFromConsole()){
                        sendTextMessage(message);
                    }
                }
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage(e.getMessage());
            }
        }
    }

    public class SocketThread extends Thread{

        protected void processIncomingMessage(String message){
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName){
            ConsoleHelper.writeMessage("Участник с именем "+userName+" присоединился к чату.");
        }

        protected void informAboutDeletingNewUser(String userName){
            ConsoleHelper.writeMessage("Участник с именем "+userName+" покинул чат.");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected){
            Client.this.clientConnected=clientConnected;
            synchronized (Client.this){
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException,ClassNotFoundException{
            while (true){
                Message message=connection.receive();
                if (message.getType()==MessageType.NAME_REQUEST){
                    String userName=getUserName();
                    Message newMessage=new Message(MessageType.USER_NAME,userName);
                    connection.send(newMessage);
                } else if (message.getType()==MessageType.NAME_ACCEPTED){
                    notifyConnectionStatusChanged(true);
                    break;
                } else {
                    throw new IOException("Unexpected MessageType.");
                }
            }
        }

        protected void clientMainLoop() throws IOException,ClassNotFoundException{
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    processIncomingMessage(message.getData());
                } else if (message.getType() == MessageType.USER_ADDED) {
                    informAboutAddingNewUser(message.getData());
                } else if (message.getType() == MessageType.USER_REMOVED) {
                    informAboutDeletingNewUser(message.getData());
                } else {
                    throw new IOException("Unexpected MessageType.");
                }
            }
        }

        public void run(){
            String serverAddress=getServerAddress();
            int port=getServerPort();
            Socket socket= null;
            try {
                socket = new Socket(serverAddress,port);
                connection=new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }
    }

    protected String getServerAddress(){
        ConsoleHelper.writeMessage("Введите адрес сервера.");
        return ConsoleHelper.readString();
    }

    protected int getServerPort(){
        ConsoleHelper.writeMessage("Введите порт сервера.");
        return ConsoleHelper.readInt();
    }

    protected String getUserName(){
        ConsoleHelper.writeMessage("Введите имя пользователя.");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole(){
        return true;
    }

    protected SocketThread getSocketThread(){
        return new SocketThread();
    }

    protected void sendTextMessage(String text){
        try {
            Message message=new Message(MessageType.TEXT,text);
            connection.send(message);
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Произошла ошибка при отправке сообщения серверу...");
            clientConnected=false;
        }
    }
}
