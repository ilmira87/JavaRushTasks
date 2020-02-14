package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BotClient extends Client {
    public static void main(String[] args) {
        BotClient botClient=new BotClient();
        botClient.run();
    }

    @Override
    protected String getUserName() {
        int x=(int) (Math.random()*100);
        return "date_bot_"+x;
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    public class BotSocketThread extends SocketThread{

        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            int index=message.indexOf(":");
            if (index!=-1) {
                String nameSender = message.substring(0, index);
                String text = message.substring(index + 2);
                SimpleDateFormat formatDate = null;
                switch (text) {
                    case "дата":
                        formatDate = new SimpleDateFormat("d.MM.YYYY");
                        break;
                    case "день":
                        formatDate = new SimpleDateFormat("d");
                        break;
                    case "месяц":
                        formatDate = new SimpleDateFormat("MMMM");
                        break;
                    case "год":
                        formatDate = new SimpleDateFormat("YYYY");
                        break;
                    case "время":
                        formatDate = new SimpleDateFormat("H:mm:ss");
                        break;
                    case "час":
                        formatDate = new SimpleDateFormat("H");
                        break;
                    case "минуты":
                        formatDate = new SimpleDateFormat("m");
                        break;
                    case "секунды":
                        formatDate = new SimpleDateFormat("s");
                        break;
                }
                if (formatDate != null) {
                    Calendar calendar = new GregorianCalendar();
                    Date date = calendar.getTime();
                    sendTextMessage("Информация для " + nameSender + ": " + formatDate.format(date));
                }
            }
        }
    }
}
