package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private final int number;
    private static Logger logger=Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Tablet(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }

    public Order createOrder(){
        Order order=null;
        try {
            order=new Order(this);
            processAdvertisement(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Console is unavailable.");
        } catch (NoVideoAvailableException e){
            logger.log(Level.INFO,"No video is available for the order "+order.toString());
        }
        return order;
    }

    private void processAdvertisement(Order order) throws NoVideoAvailableException{
        if (!order.isEmpty()) {
            AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
            advertisementManager.processVideos();
            queue.add(order);
        }
    }

    public void createTestOrder(){
        TestOrder order=null;
        try {
            order=new TestOrder(this);
            processAdvertisement(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Console is unavailable.");
        } catch (NoVideoAvailableException e){
            logger.log(Level.INFO,"No video is available for the order "+order.toString());
        }
    }
}
