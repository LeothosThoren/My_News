package com.leothosthoren.mynews.model.Utils;

import com.leothosthoren.mynews.model.NewYorkTimeTopStories;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/02/2018
 */
public interface NewYorkTimeService {


    @GET("/home.json/{section}/?api-key=7aa6518af840427eb78832360465fa9e")
    Call<List<NewYorkTimeTopStories>> getFollowing(@Path("section") String section);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}


