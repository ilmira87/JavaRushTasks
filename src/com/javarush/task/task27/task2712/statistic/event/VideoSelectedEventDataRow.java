package com.javarush.task.task27.task2712.statistic.event;

import com.javarush.task.task27.task2712.ad.Advertisement;

import java.time.LocalDate;
import java.util.List;

public class VideoSelectedEventDataRow implements EventDataRow{
    private List<Advertisement> optimalVideoSet;
    private long amount;
    private int totalDuration;
    private LocalDate currentDate;
    
    public long getAmount(){
        return amount;
    }

    public VideoSelectedEventDataRow(List<Advertisement> optimalVideoSet, long amount, int totalDuration) {
        this.optimalVideoSet = optimalVideoSet;
        this.amount = amount;
        this.totalDuration = totalDuration;
        this.currentDate=LocalDate.now();
    }

    @Override
    public EventType getType() {
        return EventType.SELECTED_VIDEOS;
    }
    
    @Override
    public LocalDate getDate(){
        return currentDate;
    }
    
    @Override
    public int getTime(){
        return totalDuration;
    }
}
