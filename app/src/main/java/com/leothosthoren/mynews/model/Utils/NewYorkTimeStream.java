package com.leothosthoren.mynews.model.Utils;

import com.leothosthoren.mynews.model.MostPopular;
import com.leothosthoren.mynews.model.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/02/2018
 */
public class NewYorkTimeStream {

    public static Observable<TopStories> streamFetchTopStories(String section) {
        NewYorkTimeService nyts1 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return nyts1.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<MostPopular> streamFetchMostPopular(String section) {
        NewYorkTimeService nyts2 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return nyts2.getMostPopular(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

//Create observable for searchArticle

//    public static Observable<TopStories.Multimedium> streamFetchTopStoriesImage() {
//        NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
//        return newYorkTimeService.getTopStoriesImage()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .timeout(10, TimeUnit.SECONDS);
//    }
//
//    public static Observable<TopStories.Multimedium> streamFetchTopStoriesDatasAndImage(String section){
//        return streamFetchTopStories(section)
//                .map(new Function<TopStories, TopStories>() {
//                    @Override
//                    public TopStories apply(TopStories topStoriesData) throws Exception {
//                        return topStoriesData;
//                    }
//                })
//                .flatMap(new Function<TopStories, Observable<TopStories.Multimedium>>() {
//                    @Override
//                    public Observable<TopStories.Multimedium> apply(TopStories topStoriesImage) throws Exception {
//                        return streamFetchTopStoriesImage();
//                    }
//                });
//    }
}
