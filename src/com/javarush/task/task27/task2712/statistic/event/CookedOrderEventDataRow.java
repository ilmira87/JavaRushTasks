package com.javarush.task.task27.task2712.statistic.event;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.time.LocalDate;
import java.util.List;

public class CookedOrderEventDataRow  implements EventDataRow{
    private String tabletName;
    private String cookName;
    private int cookingTimeSeconds;
    private List<Dish> cookingDishs;
    private LocalDate currentDate;
    
    public String getCookName(){
        return cookName;
    }

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishs) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishs = cookingDishs;
        this.currentDate=LocalDate.now();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }
    
    @Override
    public LocalDate getDate(){
        return currentDate;
    }
    
    @Override
    public int getTime(){
        return cookingTimeSeconds;
    }
}
