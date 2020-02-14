package com.javarush.task.task21.task2113;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hippodrome {

    private List<Horse> horses;
    static Hippodrome game;

    public List<Horse> getHorses() {
        return horses;
    }

    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public void run() throws InterruptedException {
        for (int i=1;i<=100;i++){
            move();
            print();
            Thread.sleep(200);
        }
    }

    private void move(){
        for (Horse horse:horses) {
            horse.move();
        }
    }

    private void print(){
        for (Horse horse:horses) {
            horse.print();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    private Horse getWinner(){
        List<Double> distances=new ArrayList<>();
        for (Horse horse:horses) {
            distances.add(horse.getDistance());
        }
        double maxDistance= Collections.max(distances);
        Horse winner=null;
        for (Horse horse :horses) {
            if (horse.getDistance()==maxDistance)
                winner=horse;
        }
        return winner;
    }

    public void printWinner(){
        System.out.println("Winner is "+getWinner().getName()+"!");
    }

    public static void main(String[] args) throws InterruptedException {
        Horse black=new Horse("Black",3,0);
        Horse white=new Horse("White",3,0);
        Horse gray=new Horse("Gray",3,0);
        game=new Hippodrome(new ArrayList<>());
        game.horses.add(black);
        game.horses.add(white);
        game.horses.add(gray);
        game.run();
        game.printWinner();
    }
}
