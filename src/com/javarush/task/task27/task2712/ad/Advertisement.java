package com.javarush.task.task27.task2712.ad;

public class Advertisement implements Comparable{
    private Object content;
    private String name;
    private long initialAmount;
    private int hits;
    private int duration;
    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        this.amountPerOneDisplaying= (hits==0) ? 0 : initialAmount/hits;
    }

    public String getName() {
        return name;
    }

    public int getHits() {
        return hits;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
   }

   public void revalidate(){
        if (hits<=0){
            throw new UnsupportedOperationException();
        }
        hits-=1;
   }

    @Override
    public int compareTo(Object o) {
        return this.name.compareToIgnoreCase(((Advertisement)o).getName());
    }
}
