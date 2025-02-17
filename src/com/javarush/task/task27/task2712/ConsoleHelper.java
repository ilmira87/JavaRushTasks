package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> allDishesForOrder=new ArrayList<>();
        while (true){
            System.out.println(Dish.allDishesToString());
            System.out.println("Введите строку - название блюда");
            String line=readString();
            if (line.equals("exit")) break;
            if (Dish.allDishesToString().contains(line)){
                allDishesForOrder.add(Dish.valueOf(line));
            } else {
                writeMessage("Такого блюда нет");
            }
        }
        return allDishesForOrder;
    }
}
