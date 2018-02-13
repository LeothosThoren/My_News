package com.leothosthoren.mynews.model.Utils;

import android.support.annotation.Nullable;

import com.leothosthoren.mynews.model.NewYorkTimeTopStories;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/02/2018
 */

public class NewYorkTimesCalls {

    // 1 - Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable List<NewYorkTimeTopStories> section);
        void onFailure();
    }

    // 2 - Public method to start fetching users following by Jake Wharton
    public static void fetchFollowing(Callbacks callbacks, String section){

        // 2.1 - Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // 2.2 - Get a Retrofit instance and the related endpoints
        NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);

        // 2.3 - Create the call on Github API
        Call<List<NewYorkTimeTopStories>> call = newYorkTimeService.getFollowing(section);
        // 2.4 - Start the call
        call.enqueue(new Callback<List<NewYorkTimeTopStories>>() {

            @Override
            public void onResponse(Call<List<NewYorkTimeTopStories>> call, Response<List<NewYorkTimeTopStories>> response) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<NewYorkTimeTopStories>> call, Throwable t) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }
}