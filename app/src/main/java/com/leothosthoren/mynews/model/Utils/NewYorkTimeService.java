package com.leothosthoren.mynews.model.Utils;

import com.leothosthoren.mynews.model.GithubUser;
import com.leothosthoren.mynews.model.NewYorkTimeTopStories;

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
    String BaseUrl = "https://api.nytimes.com/svc/topstories/v2/";

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


//    @GET("subsection")
//    Observable<List<NewYorkTimeTopStories>> getFollowing(@Path("subsection") String subsection);

    @GET("users/{username}/following")
    Observable<List<GithubUser>> getFollowing(@Path("username") String username);

//    @GET("users/{username}")
//    Observable<GithubUser> getUserInfo(@Path("username") String username);

}


