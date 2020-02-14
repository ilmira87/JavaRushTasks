package com.javarush.task.task27.task2712;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> listAllTablets;
    private int orderCreatingInterval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.listAllTablets=tablets;
        this.orderCreatingInterval=interval;
    }

    @Override
    public void run() {
        while (true){
            int numberTablet= ThreadLocalRandom.current().nextInt(0, listAllTablets.size());
            listAllTablets.get(numberTablet).createTestOrder();
            try {
                Thread.sleep(orderCreatingInterval);
            } catch (InterruptedException e) {
                
            }
        }
    }
}
