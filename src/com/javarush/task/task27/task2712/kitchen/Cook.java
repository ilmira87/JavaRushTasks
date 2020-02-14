package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.concurrent.LinkedBlockingQueue;

public class Cook implements Runnable{
    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue;
    private LinkedBlockingQueue<Order> queueReadyOrder;

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void setQueueReadyOrder(LinkedBlockingQueue<Order> queueReadyOrder) {
        this.queueReadyOrder = queueReadyOrder;
    }

    public boolean isBusy() {
        return busy;
    }

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) throws InterruptedException {
        busy=true;
        ConsoleHelper.writeMessage(String.format("Start cooking - %s, cooking time %dmin",order.toString(),order.getTotalCookingTime()));
        Thread.sleep(order.getTotalCookingTime()*10);
        busy=false;
        queueReadyOrder.add(order);
    }

    @Override
    public void run() {
        while (true){
            if (!queue.isEmpty()){
                if (!this.isBusy()) {
                    try {
                        Order order= queue.poll();
                        if (order!=null) {
                            this.startCookingOrder(order);
                        }
                    } catch (InterruptedException ignor) { }
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignor) {}
        }
    }
}
