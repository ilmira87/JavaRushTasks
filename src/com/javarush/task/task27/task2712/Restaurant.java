package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL=100;
    private static final LinkedBlockingQueue<Order> orderQueue =new LinkedBlockingQueue<>();
    private static final LinkedBlockingQueue<Order> queueReadyOrders =new LinkedBlockingQueue<>();

    public static void main(String[] args) throws InterruptedException {

        Cook cook1=new Cook("Amigo");
        Cook cook2=new Cook("Serg");
        cook1.setQueue(orderQueue);
        cook1.setQueueReadyOrder(queueReadyOrders);
        cook2.setQueue(orderQueue);
        cook2.setQueueReadyOrder(queueReadyOrders);
        Thread threadCook1=new Thread(cook1);
        Thread threadCook2=new Thread(cook2);
        threadCook1.start();
        threadCook2.start();

        Waiter waiter1=new Waiter();
        Waiter waiter2=new Waiter();
        waiter1.setQueueReadyOrder(queueReadyOrders);
        waiter2.setQueueReadyOrder(queueReadyOrders);
        Thread threadWaiter1=new Thread(waiter1);
        Thread threadWaiter2=new Thread(waiter2);
        threadWaiter1.start();
        threadWaiter2.start();

        List<Tablet> tablets=new ArrayList<>();
        for (int i=1;i<=5;i++) {
            Tablet tablet=new Tablet(i);
            tablets.add(tablet);
            tablet.setQueue(orderQueue);
        }

        RandomOrderGeneratorTask randomOrderGeneratorTask=new RandomOrderGeneratorTask(tablets,ORDER_CREATING_INTERVAL);
        Thread thread=new Thread(randomOrderGeneratorTask);
        thread.start();

        Thread.sleep(10000);

        DirectorTablet directorTablet=new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
