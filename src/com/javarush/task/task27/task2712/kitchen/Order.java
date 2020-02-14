package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes=new ArrayList<>();

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        initDishes();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    protected void initDishes() throws IOException {
        dishes=ConsoleHelper.getAllDishesForOrder();
    }

    @Override
    public String toString() {
        if (dishes.isEmpty()){
            return null;
        } else {
            StringBuilder order=new StringBuilder();
            for (int i=0;i<dishes.size();i++) {
                order.append(dishes.get(i).toString());
                if (i!=dishes.size()-1) {
                    order.append(", ");
                }
            }
            return String.format("Your order: [%s] of %s",order.toString(),tablet.toString());
        }
    }

    public int getTotalCookingTime(){
        int totalCookingTime=0;
        for (Dish dish : dishes) {
            totalCookingTime+=dish.getDuration();
        }
        return totalCookingTime;
    }

    public boolean isEmpty(){
        return dishes.isEmpty();
    }
}
