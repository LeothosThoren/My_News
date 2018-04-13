package com.leothosthoren.mynews.model.utility;

import com.leothosthoren.mynews.model.apis.articles.MostPopular;
import com.leothosthoren.mynews.model.apis.articles.SearchArticle;
import com.leothosthoren.mynews.model.apis.articles.TopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/02/2018
 */
public interface NewYorkTimeService {
    String ApiKey = "7aa6518af840427eb78832360465fa9e";
    String BaseUrl = "http://api.nytimes.com/svc/";
    String SearchArticleFl = "fl=web_url,snippet,pub_date,news_desk,multimedia,document_type,type_of_material";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    // Top Stories Fragment
    @GET("topstories/v2/{section}.json?api-key=" + ApiKey)
    Observable<TopStories> getTopStories(@Path("section") String section);

    // Most Popular Fragment
    @GET("mostpopular/v2/mostemailed/all-sections/1.json?api-key=" + ApiKey)
    Observable<MostPopular> getMostPopular();

    //Search Activity
    @GET("search/v2/articlesearch.json?" + SearchArticleFl + "&sort=newest&page=3&api-key=" + ApiKey)
    Observable<SearchArticle> getSearchArticle(@Query("q") String query,
                                               @Query("fq") String news_desk,
                                               @Query("begin_date") String begin_date,
                                               @Query("end_date") String end_date);

    //Notification Activity
    @GET("search/v2/articlesearch.json?" + SearchArticleFl + "&sort=newest&api-key=" + ApiKey)
    Observable<SearchArticle> getNotification(@Query("q") String query,
                                               @Query("fq") String news_desk);

}


