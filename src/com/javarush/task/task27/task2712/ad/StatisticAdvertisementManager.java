package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    private AdvertisementStorage storage=AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public static StatisticAdvertisementManager getInstance(){
        if (instance==null){
            instance=new StatisticAdvertisementManager();
        }
        return instance;
    }

    public List<Advertisement> getListAdvertisement(String option){
        List<Advertisement> result=new ArrayList<>();
        List<Advertisement> allAdvertisement=storage.list();
        for (Advertisement advertisement : allAdvertisement) {
            if (option.equals("activeAdvertisement") && advertisement.getHits()!=0){
                result.add(advertisement);
            }
            if (option.equals("nonActiveAdvertisement") && advertisement.getHits()==0){
                result.add(advertisement);
            }
        }
        Collections.sort(result);
        return result;
    }
}
