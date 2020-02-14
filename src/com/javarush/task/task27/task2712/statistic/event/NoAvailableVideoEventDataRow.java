package com.javarush.task.task27.task2712.statistic.event;

import java.time.LocalDate;

public class NoAvailableVideoEventDataRow  implements EventDataRow{
    private int totalDuration;
    private LocalDate currentDate;

    public NoAvailableVideoEventDataRow(int totalDuration) {
        this.totalDuration = totalDuration;
        this.currentDate=LocalDate.now();
    }

    @Override
    public EventType getType() {
        return EventType.NO_AVAILABLE_VIDEO;
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
