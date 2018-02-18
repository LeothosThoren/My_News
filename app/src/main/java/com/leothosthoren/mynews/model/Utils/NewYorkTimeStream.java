package com.leothosthoren.mynews.model.Utils;

import com.leothosthoren.mynews.model.TopStories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/02/2018
 */
public class NewYorkTimeStream {

    public static Observable<TopStories> streamFetchTopStories() {
        NewYorkTimeService nyts = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return nyts.getTopStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<List<TopStories.Multimedium>> streamFetchTopStoriesImage() {
        NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return newYorkTimeService.getTopStoriesImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<List<TopStories.Multimedium>> streamFetchTopStoriesDatasAndImage(){
        return streamFetchTopStories()
                .map(new Function<TopStories, TopStories>() {
                    @Override
                    public TopStories apply(TopStories topStoriesData) throws Exception {
                        return topStoriesData;
                    }
                })
                .flatMap(new Function<TopStories, Observable<List<TopStories.Multimedium>>>() {
                    @Override
                    public Observable<List<TopStories.Multimedium>> apply(TopStories topStoriesImage) throws Exception {
                        return streamFetchTopStoriesImage();
                    }
                });
    }
}
