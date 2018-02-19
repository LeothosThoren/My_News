package com.leothosthoren.mynews.model.Utils;

import com.leothosthoren.mynews.model.TopStories;

import java.util.List;

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
    String BaseUrl = "http://api.nytimes.com/";

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


    @GET("svc/topstories/v2/{section}.json?api-key="+ApiKeyTopStories)
    Observable<TopStories> getTopStories(@Path("section") String section);

    @GET("svc/topstories/v2/{section}.json?api-key="+ApiKeyTopStories)
    Observable<TopStories.Multimedium> getTopStoriesImage();

//    @GET("users/{username}/following")
//    Observable<List<GithubUser>> getFollowing(@Path("username") String username);

//    @GET("users/{username}")
//    Observable<GithubUser> getUserInfo(@Path("username") String username);

}


