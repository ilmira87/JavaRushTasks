package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.concurrent.LinkedBlockingQueue;

public class Waiter implements Runnable {
    private LinkedBlockingQueue<Order> queueReadyOrder;
    private boolean busy;

    public void setQueueReadyOrder(LinkedBlockingQueue<Order> queueReadyOrder) {
        this.queueReadyOrder = queueReadyOrder;
    }

    public boolean isBusy() {
        return busy;
    }

    public Waiter() {
    }

    public void deliverOrder(Order order){
        busy=true;
        ConsoleHelper.writeMessage(order.toString()+" was cooked.");
        busy=false;
    }

    @Override
    public void run() {
        while (true){
            if (!queueReadyOrder.isEmpty()){
                if (!this.isBusy()) {
                        Order order= queueReadyOrder.poll();
                        if (order!=null) {
                            this.deliverOrder(order);
                        }
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignor) {}
        }
    }
}
