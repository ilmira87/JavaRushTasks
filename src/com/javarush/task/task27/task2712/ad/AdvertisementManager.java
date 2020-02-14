package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdvertisementManager{
    private final AdvertisementStorage storage=AdvertisementStorage.getInstance();
    private int timeSeconds;
    List<Advertisement> bestSet;
    long bestAmount;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException {
        if (storage.list().isEmpty()){
            throw new NoVideoAvailableException();
        }

        List<Advertisement> videos=getAvailableVideos();

        makeAllSets(videos);

        Comparator<Advertisement> comparator=new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return Long.compare(o2.getAmountPerOneDisplaying(), o1.getAmountPerOneDisplaying());
            }
        };

        comparator.thenComparing(new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                if (o1.getAmountPerOneDisplaying()*1000/o1.getDuration()>o2.getAmountPerOneDisplaying()*1000/o1.getDuration())
                    return 1;
                else if (o1.getAmountPerOneDisplaying()*1000/o1.getDuration()>o2.getAmountPerOneDisplaying()*1000/o1.getDuration())
                    return -1;
                else
                    return 0;
            }
        });
        if (bestSet!=null) {
            Collections.sort(bestSet, comparator);
            VideoSelectedEventDataRow videoSelectedEventDataRow = new VideoSelectedEventDataRow(bestSet, bestAmount, calcDuration(bestSet));
            StatisticManager.getInstance().register(videoSelectedEventDataRow);
            for (Advertisement advertisement : bestSet) {
                ConsoleHelper.writeMessage(String.format("%s is displaying... %d, %d", advertisement.getName(), advertisement.getAmountPerOneDisplaying(), advertisement.getAmountPerOneDisplaying() * 1000 / advertisement.getDuration()));
                advertisement.revalidate();
            }
        }
    }
    private List<Advertisement> getAvailableVideos() {
        List<Advertisement> videos=new ArrayList<>();
        for (Advertisement video : storage.list()) {
            if (video.getHits() > 0 && video.getDuration() <= timeSeconds) {
                videos.add(video);
            }
        }
        return videos;
    }

    private int calcDuration(List<Advertisement> videos){
        int sumDurations=0;
        for (Advertisement video:videos) {
            sumDurations+=video.getDuration();
        }
        return sumDurations;
    }

    private long calcAmount(List<Advertisement> videos){
        int sumAmounts=0;
        for (Advertisement video:videos) {
            sumAmounts+=video.getAmountPerOneDisplaying();
        }
        return sumAmounts;
    }

    private void  checkSet(List<Advertisement> video){
        if (bestSet==null){
            if (calcDuration(video)<=timeSeconds){
                bestSet=video;
                bestAmount=calcAmount(video);
            }
        } else {
            bestSet=compareSets(bestSet,video);
            bestAmount=calcAmount(bestSet);
        }
    }

    private List<Advertisement> compareSets(List<Advertisement> bestSet, List<Advertisement> videos){
        if (calcDuration(videos)<=timeSeconds){
            if (calcAmount(videos)>bestAmount){
                return videos;
            } else if (calcAmount(videos)==bestAmount){
                if (calcDuration(videos)>calcDuration(bestSet)){
                    return videos;
                } else if (calcDuration(videos)==calcDuration(bestSet)){
                    if (videos.size()<bestSet.size()){
                        return videos;
                    }
                }
            }
        }
        return bestSet;
    }

    private void makeAllSets(List<Advertisement> videos){
        if (videos.size()>0){
            checkSet(videos);
        }
        for (int i = 0; i < videos.size(); i++) {
            List<Advertisement> newSet=new ArrayList<>(videos);
            newSet.remove(i);
            makeAllSets(newSet);
        }
    }
}
