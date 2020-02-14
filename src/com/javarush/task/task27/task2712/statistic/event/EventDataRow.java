package com.javarush.task.task27.task2712.statistic.event;
import java.time.LocalDate;

public interface EventDataRow {
    EventType getType();
    
    LocalDate getDate();
    
    int getTime();
}
