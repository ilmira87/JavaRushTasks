package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class TestOrder extends Order {
    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() throws IOException {
        Dish[] allDishes=Dish.values();
        int qtyDishes= ThreadLocalRandom.current().nextInt(1, allDishes.length+1);
        for (int i=0;i<qtyDishes;i++){
            int numberChoosedDish=ThreadLocalRandom.current().nextInt(0, allDishes.length);
            dishes.add(allDishes[numberChoosedDish]);
        }
    }
}
