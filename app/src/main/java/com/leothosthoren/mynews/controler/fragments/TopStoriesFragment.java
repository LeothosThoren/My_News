package com.leothosthoren.mynews.controler.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.leothosthoren.mynews.controler.activities.WebViewActivity;
import com.leothosthoren.mynews.model.MostPopular;
import com.leothosthoren.mynews.model.TopStories;
import com.leothosthoren.mynews.model.Utils.NewYorkTimeStream;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapter;

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
    public static final List<TopStories.Result> mTopStoriesArray = new ArrayList<>();
    private static final String KEY_POSITION = "position";
    private final List<MostPopular.Result> mResultList = new ArrayList<>();
    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    private Disposable mDisposable;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int position;


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
        position = getArguments().getInt(KEY_POSITION, -1);
        mProgressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        //Call the recyclerView builder method
        this.buildRecyclerView();
        //Http requests

        if (position == 1)
            this.executeSecondHttpRequestWithRetrofit();
        else
            this.executeHttpRequestWithRetrofit();

        this.configureSwipeRefrechLayout();

        // 6 - Update widgets with it

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
        this.displayWebView();
    }

    private void displayWebView() {
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Here we allow the toast text to appear only if the comment is not empty at his own position
                Toast.makeText(getContext(),
                        "Click on item number " + position + "URL : " + mTopStoriesArray.get(position).getShortUrl(),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra(ITEMPOSITION, position);
                startActivity(intent);

                //Here we are going to implements a web view
//                WebView webview = new WebView(getContext());
//                webview.loadUrl(mTopStoriesArray.get(position).getUrl());


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

    private void executeSecondHttpRequestWithRetrofit() {
        this.updateUIWhenStartingHTTPRequest();

        this.mDisposable = NewYorkTimeStream.streamFetchMostPopular(topStoriesSection[position])
                .subscribeWith(new DisposableObserver<MostPopular>() {

                    @Override
                    public void onNext(MostPopular MostpopularItems) {
                        Log.d("TAG", "On Next");
                        upDateUIMP(MostpopularItems);
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

    public void executeHttpRequestWithRetrofit() {
        this.updateUIWhenStartingHTTPRequest();

        this.mDisposable = NewYorkTimeStream.streamFetchTopStories(topStoriesSection[position])
                .subscribeWith(new DisposableObserver<TopStories>() {

                    @Override
                    public void onNext(TopStories topStoriesItems) {
                        Log.d("TAG", "On Next");
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

    private void upDateUIMP(MostPopular Mostpopular) {
        this.mProgressBar.setVisibility(View.GONE);
        mRefreshLayout.setRefreshing(false);
        mTopStoriesArray.clear();
        //Test
        List<MostPopular.Result> resultListMostPop = Mostpopular.getResults();
        mResultList.addAll(resultListMostPop);
        //Test

        mAdapter.notifyDataSetChanged();

    }

}




