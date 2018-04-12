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

import com.bumptech.glide.Glide;
import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.controler.activities.WebViewActivity;
import com.leothosthoren.mynews.model.HttpRequestTools;
import com.leothosthoren.mynews.model.ModelTools;
import com.leothosthoren.mynews.model.utility.NewYorkTimeStream;
import com.leothosthoren.mynews.model.apis.articles.TopStories;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapterTopStories;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.leothosthoren.mynews.view.adapters.ViewPageAdapter.topStoriesSection;


public class TopStoriesFragment extends Fragment {
    //Ice pick

    public static final String ITEMPOSITION = "webView_position";
    private static final String KEY_POSITION = "position";
    public List<TopStories.Resultum> mTopStoriesArray = new ArrayList<>();
    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    private ModelTools mTools = new ModelTools();
    private Disposable mDisposable;
    private RecyclerViewAdapterTopStories mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int position;
    private HttpRequestTools mHttpRequestTools = new HttpRequestTools();

    public TopStoriesFragment() {
        // Required empty public constructor
    }

    public static TopStoriesFragment newInstance(int position) {
        TopStoriesFragment fragment = new TopStoriesFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
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
        View result = inflater.inflate(R.layout.fragment_top_stories_layout, container, false);
        ButterKnife.bind(this, result);
        //We get the index position of the viewPager
        position = getArguments().getInt(KEY_POSITION, -1);
        //A progress bar is loaded and setted
        this.mHttpRequestTools.progressBarHandler(mProgressBar, getContext());
        //Call the recyclerView builder method
        this.buildRecyclerView();
        this.executeHttpRequestWithRetrofit();
        //It's possible to refresh the Uri api on vertical swipe from the top to the bottom
        this.configureSwipeRefrechLayout();

        return result;
    }

    //Called for better performances
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    /*
    * @method buildRecyclerView
    *
    * This method manages the recyclerView set up
    * */
    private void buildRecyclerView() {
        //Calling the adapter
        this.mAdapter = new RecyclerViewAdapterTopStories(mTopStoriesArray, Glide.with(this));
        //Set them with natives methods
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //When user click on an item a new activity is launched to display a webView
        this.displayWebView();
    }

    /*
    * @method displayActivity
    *
    * Used to open a web view directly in the app, not by default application
    * */
    private void displayWebView() {
        this.mAdapter.setOnItemClickListener(new RecyclerViewAdapterTopStories.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mTools.openActivityAsBrowser(mTopStoriesArray.get(position).getUrl(), getContext(), WebViewActivity.class);
            }
        });
    }


    public void executeHttpRequestWithRetrofit() {
        this.mHttpRequestTools.updateUIWhenStartingHTTPRequest(mProgressBar);

        this.mDisposable = NewYorkTimeStream.streamFetchTopStories(topStoriesSection[position])
                .subscribeWith(new DisposableObserver<TopStories>() {

                    @Override
                    public void onNext(TopStories topStoriesItems) {
                        Log.d("TopStories Tag", "On Next");
                        upDateTopStoriesUI(topStoriesItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TopStories Tag", "On Error" + Log.getStackTraceString(e).toUpperCase());
                        mHttpRequestTools.internetDisable(mProgressBar, getString(R.string.no_internet), getContext());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TopStories Tag", "On Complete !");
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

    /*
   * @method configureSwipeRefrechLayout
   *
   * When the screen is swipe, the http request is executed
   * */
    private void configureSwipeRefrechLayout() {
        this.mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    private void upDateTopStoriesUI(TopStories topStories) {
        this.mHttpRequestTools.updateUIWhenStopingHTTPRequest(mRefreshLayout, mProgressBar);
        this.mTopStoriesArray.clear();
        this.mTopStoriesArray.addAll(topStories.getResults());
        this.mAdapter.notifyDataSetChanged();
    }


}




