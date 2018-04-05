package com.leothosthoren.mynews.model.Utils;

import com.leothosthoren.mynews.model.apis.articles.MostPopular;
import com.leothosthoren.mynews.model.apis.articles.SearchArticle;
import com.leothosthoren.mynews.model.apis.articles.TopStories;

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

    public static Observable<MostPopular> streamFetchMostPopular() {
        NewYorkTimeService nyts2 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return nyts2.getMostPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

//Create observable for searchArticle

    public static Observable<SearchArticle> streamFetchSearchArticle(String query, String news_desk, String begin_date, String end_date) {
        NewYorkTimeService nyts3 = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return nyts3.getSearchArticle(query, news_desk, begin_date, end_date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
