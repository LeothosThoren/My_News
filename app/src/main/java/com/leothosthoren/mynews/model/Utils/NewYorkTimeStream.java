package com.leothosthoren.mynews.model.Utils;

import com.leothosthoren.mynews.model.ModelApi;
import com.leothosthoren.mynews.model.MostPopular;
import com.leothosthoren.mynews.model.SearchArticle;
import com.leothosthoren.mynews.model.TopStories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/02/2018
 */
public class NewYorkTimeStream {

    public static Observable<ModelApi> streamFetchTopStories(String section) {
        NewYorkTimeService nyts1 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return nyts1.getTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ModelApi> streamFetchMostPopular() {
        NewYorkTimeService nyts2 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return nyts2.getMostPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

//Create observable for searchArticle

    public static Observable<SearchArticle.Response> streamFetchSearchArticle() {
        NewYorkTimeService nyts2 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return nyts2.getSearchArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
