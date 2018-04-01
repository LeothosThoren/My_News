package com.leothosthoren.mynews.model.Utils;

import com.leothosthoren.mynews.model.TopStories;
import com.leothosthoren.mynews.model.most.popular.MostPopular;
import com.leothosthoren.mynews.model.search.articles.SearchArticle;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/02/2018
 */
public interface NewYorkTimeService {
    String ApiKeyTopStories = "7aa6518af840427eb78832360465fa9e";
    String ApiKeyMostPopular = "7aa6518af840427eb78832360465fa9e";
    String ApiKeySearchArticle = "7aa6518af840427eb78832360465fa9e";
    String BaseUrl = "http://api.nytimes.com/svc/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    @GET("topstories/v2/{section}.json?api-key="+ApiKeyTopStories)
    Observable<TopStories> getTopStories(@Path("section") String section);


    @GET("mostpopular/v2/mostemailed/all-sections/1.json?api-key="+ApiKeyMostPopular)
    Observable<MostPopular> getMostPopular();

//    @GET("svc/search/v2/articlesearch.json?q={query}&fq={label}&api-key="+ApiKeySearchArticle)
//    Observable<?> getSearchArticle(@Query("query") String query, @Query("label") String label);
//
//    @GET("svc/search/v2/articlesearch.json?q=France&begin_date=20170315&fq=news_desk:(Arts)&page=2&api-key="+ApiKeySearchArticle)
//    Observable<SearchArticle> getSearchArticle();

    @GET("search/v2/articlesearch.json?q=new+york+time&page=2&sort=newest&api-key="+ApiKeySearchArticle)
    Observable<SearchArticle> getSearchArticle();

}


