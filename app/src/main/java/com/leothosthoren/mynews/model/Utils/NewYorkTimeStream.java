package com.leothosthoren.mynews.model.Utils;

import com.leothosthoren.mynews.model.GithubUser;
import com.leothosthoren.mynews.model.NewYorkTimeTopStories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/02/2018
 */
public class NewYorkTimeStream {

    public static Observable<List<GithubUser>> streamFetchFollowing(String username) {
        NewYorkTimeService nyts = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
        return nyts.getFollowing(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

//    public static Observable<GithubUser> streamFetchUSersInfo(String username) {
//        NewYorkTimeService newYorkTimeService = NewYorkTimeService.retrofit.create(NewYorkTimeService.class);
//        return newYorkTimeService.getUserInfo(username)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .timeout(10, TimeUnit.SECONDS);
//    }
//
//    public static Observable<GithubUser> streamFetchUSerFollowingAndFetchFirstUSerInfo(final String username){
//        return streamFetchFollowing(username)
//                .map(new Function<List<GithubUser>, GithubUser>() {
//                    @Override
//                    public GithubUser apply(List<GithubUser> users) throws Exception {
//                        return users.get(0);
//                    }
//                })
//                .flatMap(new Function<GithubUser, Observable<GithubUser>>() {
//                    @Override
//                    public Observable<GithubUser> apply(GithubUser githubUser) throws Exception {
//                        return streamFetchUSersInfo(githubUser.getLogin());
//                    }
//                });
//    }
}
