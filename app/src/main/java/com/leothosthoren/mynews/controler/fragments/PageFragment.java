package com.leothosthoren.mynews.controler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.TopStories;
import com.leothosthoren.mynews.model.Utils.NewYorkTimeStream;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class PageFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    private static final String KEY_COLOR = "color";
    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    private Disposable mDisposable;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<TopStories.Result> mTopStoriesArray = new ArrayList<>();


    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance(int position, int color) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_COLOR, color);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, result);

        int position = getArguments().getInt(KEY_POSITION, -1);
        int color = getArguments().getInt(KEY_COLOR, -1);


        //Call the recyclerView builder method
        this.buildRecyclerView();
//        executeHttpRequest(0);
        this.executeHttpRequestWithRetrofit();
        this.configureSwipeRefrechLayout();
//        streamShowThing();
//        executeSecondHttpRequestWithRetrofit();

        // 5 - Get data from Bundle (created in method newInstance)

//        mItemNews.add(new ItemNews("mon titre", "02/02/2017",
//                R.mipmap.ic_launcher, "Long résumé", R.color.colorPrimary));
//        mItemNews.add(new ItemNews("mon titre", "02/12/2017",
//                R.mipmap.ic_launcher_round, "Très Long résumé", R.color.colorPrimary));
//        mItemNews.add(new ItemNews("mon titre", "18/10/2017",
//                R.mipmap.ic_launcher, "Très très Long résumé", R.color.colorPrimary));

        // 6 - Update widgets with it
        mRecyclerView.setBackgroundColor(color);
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void buildRecyclerView() {

        mAdapter = new RecyclerViewAdapter(mTopStoriesArray, Glide.with(this));
        //Set them with natives methods
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Here we allow the toast text to appear only if the comment is not empty at his own position
                Toast.makeText(getContext(), "Click on item number " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configureSwipeRefrechLayout() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    //---------------
    // Reactive X
    //---------------
    //1 - Create Observable
//    private Observable<String> getObservable() {
//        return Observable.just("Cool !");
//    }
//
//    //2 - Create subscriber or Observer
//    private DisposableObserver<String> getSubscriber() {
//        return new DisposableObserver<String>() {
//            @Override
//            public void onNext(String s) {
//                mTextView.setText("Observable emits : " + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("TAG", "On error" + Log.getStackTraceString(e));
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e("TAG", "On complete !");
//            }
//        };
//    }
//
//    // 3 - Create stream and execution
//    private void streamShowThing() {
//        this.mDisposable = this.getObservable()
//                .map(getFunctionUpperCase())
//                .flatMap(getSecondObservable())
//                .subscribeWith(getSubscriber());
//    }
//
//    //6 - create a function uppercase
//    private Function<String, String> getFunctionUpperCase() {
//        return new Function<String, String>() {
//            @Override
//            public String apply(String s) throws Exception {
//                return s.toUpperCase();
//            }
//        };
//    }
//
//    //7 - create a second function to use flatmap
//    private Function<String, Observable<String>> getSecondObservable() {
//        return new Function<String, Observable<String>>() {
//            @Override
//            public Observable<String> apply(String previous) throws Exception {
//                return Observable.just(previous + "I love coding !");
//            }
//        };
//    }


//    //---------------
//    // HTTP REQUEST
//    //---------------
//
//    private void executeHttpRequest() {
//        new NetworkAsyncTask(this).execute
//                ("http://api.nytimes.com/svc/topstories/v2/home.json?api-key=7aa6518af840427eb78832360465fa9e");
//    }
//
//    @Override
//    public void onPreExecute() {
//        this.updateUIWhenStartingHTTPRequest();
//    }
//
//    @Override
//    public void doInBackground() {
//    }
//
//    @Override
//    public void onPostExecute(String json) {
//        this.updateUIWhenStopingHTTPRequest(json);
//    }
//
//    // ------------------------------
//    //  HTTP REQUEST (Retrofit Way)
//    // ------------------------------
//
//    // 4 - Execute HTTP request and update UI
//    private void executeHttpRequestWithRetrofit() {
//        this.updateUIWhenStartingHTTPRequest();
//        NewYorkTimesCalls.fetchFollowing(this, "home");
//    }
//
//    // 2 - Override callback methods
//
//    @Override
//    public void onResponse(@Nullable List<TopStories> section) {
//        // 2.1 - When getting response, we update UI
//        if (section != null) this.updateUIWithListOfSection(section);
//    }
//
//    @Override
//    public void onFailure() {
//        // 2.2 - When getting error, we update UI
//        this.updateUIWhenStopingHTTPRequest("An error happened !");
//    }


    // ------------------
    //  HTTP (RxJava)
    // ------------------
//    private void executeSecondHttpRequestWithRetrofit() {
//        this.updateUIWhenStartingHTTPRequest();
//
//        this.mDisposable = NewYorkTimeStream.streamFetchUSerFollowingAndFetchFirstUSerInfo("JakeWharton")
//                .subscribeWith(new DisposableObserver<GithubUser>() {
//
//                    @Override
//                    public void onNext(GithubUser section) {
//                        Log.e("TAG", "On Next");
//                        updateUIWithUserInfo(section);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("TAG", "On Error" + Log.getStackTraceString(e));
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e("TAG", "On Complete !");
//                    }
//                });
//    }


    private void executeHttpRequestWithRetrofit() {
        this.updateUIWhenStartingHTTPRequest();

        this.mDisposable = NewYorkTimeStream.streamFetchTopStories("home")
                .subscribeWith(new DisposableObserver<TopStories>() {

                    @Override
                    public void onNext(TopStories topStoriesItems) {
                        Log.d("TAG", "On Next");
//                        updateUIWithListOfSection(users);
                        upDateUI(topStoriesItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "On Error" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "On Complete !");
                    }
                });
    }

    // 4 - Dispose subscription
    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    private void updateUIWhenStartingHTTPRequest() {
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

//    private void updateUIWhenStopingHTTPRequest(String response) {
//        mProgressBar.setVisibility(View.GONE);
////        this.mTextView.setText(response);
//    }

    private void upDateUI(TopStories topStories) {
        this.mProgressBar.setVisibility(View.GONE);
        mRefreshLayout.setRefreshing(false);
        mTopStoriesArray.clear();

        //Test
        List<TopStories.Result> resultList = topStories.getResults();
        mTopStoriesArray.addAll(resultList);
        //Test

        mAdapter.notifyDataSetChanged();

    }

//    // 3 - Update UI showing only name of users
//    private void updateUIWithListOfSection(List<GithubUser> section) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (GithubUser sect : section) {
//            stringBuilder.append("-" + sect.getLogin() + "\n");
//        }
//
//        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
//    }

//    private void updateUIWithUserInfo(GithubUser userinfo){
//        updateUIWhenStopingHTTPRequest("The following of Jake is "
//                +userinfo.getLogin()+" with "+userinfo.getFollowersUrl()+" followers");
//    }

}




