package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String,Connection> connectionMap=new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message){
        for (Map.Entry<String,Connection> user:connectionMap.entrySet()) {
            Connection connection=user.getValue();
            try {
                connection.send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Ваше сообщение не отправлено.");
            }
        }
    }

    public static void main(String[] args){
        ConsoleHelper.writeMessage("Введите порт.");
        int port = ConsoleHelper.readInt();
        try(ServerSocket server = new ServerSocket(port)) {
            ConsoleHelper.writeMessage("Сервер запущен.");
            while (true) {
                Socket socket = server.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (IOException ex) {
            ConsoleHelper.writeMessage("Ошибка. Прервана связь с сервером...");
        }
    }

    private static class Handler extends Thread{
        private Socket socket;

        public Handler(Socket socket){
            this.socket=socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{
            Message nameRequest=new Message(MessageType.NAME_REQUEST,"Введите имя пользователя.");
            connection.send(nameRequest);
            Message name=connection.receive();
            while (name.getType()!=MessageType.USER_NAME){
                connection.send(nameRequest);
                name=connection.receive();
            }
            String userName=name.getData();
            if (!userName.equals("") && !connectionMap.containsKey(userName)){
                connectionMap.put(userName,connection);
                connection.send(new Message(MessageType.NAME_ACCEPTED));
            } else {
                userName=serverHandshake(connection);
            }
            return userName;
        }

        private void notifyUsers(Connection connection, String userName) throws IOException{
            for (Map.Entry<String,Connection> user:connectionMap.entrySet()) {
                String name=user.getKey();
                if (!userName.equals(name)) {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                }
            }
        }

        private void serverMainLoop(Connection connection,String userName) throws IOException,ClassNotFoundException{
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    Message newMessage = new Message(MessageType.TEXT, userName + ": " + message.getData());
                    sendBroadcastMessage(newMessage);
                } else {
                    ConsoleHelper.writeMessage("Ошибка. Cообщение не является текстом.");
                }
            }
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage("Установлено новое соединение с удаленным адресом "+socket.getRemoteSocketAddress());
            String userName;
            try(Connection connection =new Connection(socket)){
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                notifyUsers(connection, userName);
                serverMainLoop(connection, userName);
                if (userName!=null) {
                    connectionMap.remove(userName);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED,userName));
                }
            } catch (IOException | ClassNotFoundException e){
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удаленным адресом...");
            } finally {
                ConsoleHelper.writeMessage("Cоединение с удаленным адресом закрыто.");
            }
        }
    }
}
