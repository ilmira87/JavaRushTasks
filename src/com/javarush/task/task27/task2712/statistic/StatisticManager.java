package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.time.LocalDate;
import java.util.*;

public class StatisticManager {
    private static StatisticManager instance;
    private StatisticStorage statisticStorage =new StatisticStorage();

    private StatisticManager() { }

    public static StatisticManager getInstance() {
        if (instance==null) {
            instance=new StatisticManager();
        }
        return instance;
    }

    public void register(EventDataRow data){
        statisticStorage.put(data);
    }
    
    public Map<LocalDate,Long> getAdvertisementProfitForDates(){
        List<EventDataRow> dataAdvertisement=statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);
        Map<LocalDate,Long> advertisementProfitForDate=new TreeMap<>(Collections.reverseOrder());
        for (EventDataRow eventDataRow : dataAdvertisement){
            LocalDate date=eventDataRow.getDate();
            if (!advertisementProfitForDate.containsKey(date)){
                long advertisementProfit=0;
                for (EventDataRow event : dataAdvertisement){
                    if (event.getDate().compareTo(date)==0){
                        advertisementProfit+=((VideoSelectedEventDataRow)event).getAmount();
                    }
                }
                advertisementProfitForDate.put(date,advertisementProfit);
            }
        }
        return advertisementProfitForDate;
    }
    
    public Map<LocalDate,Map<String,Integer>> getCookingTimeForCooks(){
        List<EventDataRow> dataAdvertisement=statisticStorage.getStorage().get(EventType.COOKED_ORDER);
        Map<LocalDate,Map<String,Integer>> cookingTimeForCooksByDates=new TreeMap<>(Collections.reverseOrder());
        
        for (EventDataRow eventDataRow : dataAdvertisement){
            LocalDate date=eventDataRow.getDate();
            Map<String,Integer> cookingTimeForCooks=new TreeMap<>();
            if (!cookingTimeForCooksByDates.containsKey(date)){
        
                for (EventDataRow event : dataAdvertisement){
                    if (event.getDate().compareTo(date)==0){
                        int cookingTime=((CookedOrderEventDataRow)event).getTime();
                        String cookName=((CookedOrderEventDataRow)event).getCookName();
                        if (!cookingTimeForCooks.containsKey(cookName)){
                            cookingTimeForCooks.put(cookName,cookingTime);
                        } else {
                            int totalCookingTime=cookingTimeForCooks.get(cookName)+cookingTime;
                            cookingTimeForCooks.put(cookName,totalCookingTime);
                        }
                    }
                }
                cookingTimeForCooksByDates.put(date,cookingTimeForCooks);
            }
        }
        return cookingTimeForCooksByDates;
    }

    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage=new HashMap<>();

        public StatisticStorage() {
            for (EventType eventType:EventType.values()) {
                storage.put(eventType,new ArrayList<EventDataRow>());
            }
        }

        public Map<EventType, List<EventDataRow>> getStorage(){
            return storage;
        }

        private void put(EventDataRow data){
            EventType eventType=data.getType();
            storage.get(eventType).add(data);
        }
    }
    
    
}
