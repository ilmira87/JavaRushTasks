package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DirectorTablet {
    private StatisticManager statisticManager=StatisticManager.getInstance();
    private DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
    private StatisticAdvertisementManager statisticAdvertisementManager=StatisticAdvertisementManager.getInstance();
    
    public void printAdvertisementProfit(){
        Map<LocalDate,Long> advertisementProfitForDates=statisticManager.getAdvertisementProfitForDates();
        long total=0;
        for (Map.Entry<LocalDate,Long> entry : advertisementProfitForDates.entrySet()){
            ConsoleHelper.writeMessage(dateFormat.format(entry.getKey())+" - "+String.format("%.2f", entry.getValue()*1.00/100).replace(',', '.'));
            total+=entry.getValue();
        }
        ConsoleHelper.writeMessage("Total - "+String.format("%.2f", total*1.00/100).replace(',', '.'));
    }
    
    public void printCookWorkloading(){
        Map<LocalDate,Map<String,Integer>> cookingTimeForCooksByDates=statisticManager.getCookingTimeForCooks();
        for (Map.Entry<LocalDate,Map<String,Integer>> entry : cookingTimeForCooksByDates.entrySet()){
            ConsoleHelper.writeMessage(dateFormat.format(entry.getKey()));
            Map<String,Integer> cookingTimeForCooks=entry.getValue();
            for (Map.Entry<String,Integer> entryset : cookingTimeForCooks.entrySet()){
                ConsoleHelper.writeMessage(entryset.getKey()+" - "+entryset.getValue()+" min");
            }
            ConsoleHelper.writeMessage("");
        }
    }
    
    public void printActiveVideoSet(){
        List<Advertisement> listActiveVideos=statisticAdvertisementManager.getListAdvertisement("activeAdvertisement");
        for (Advertisement advertisement : listActiveVideos){
            ConsoleHelper.writeMessage(advertisement.getName()+" - "+advertisement.getHits());
        }
    }
    
    public void printArchivedVideoSet(){
        List<Advertisement> listArchivedVideos=statisticAdvertisementManager.getListAdvertisement("nonActiveAdvertisement");

        for (Advertisement advertisement:listArchivedVideos){
            ConsoleHelper.writeMessage(advertisement.getName());
        }
    }
}