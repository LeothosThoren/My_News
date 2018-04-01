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
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.controler.activities.WebViewActivity;
import com.leothosthoren.mynews.model.Utils.NewYorkTimeStream;
import com.leothosthoren.mynews.model.most.popular.MostPopular;
import com.leothosthoren.mynews.model.most.popular.Result;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapterMostPopular;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends Fragment {

    private static final String KEY_POSITION = "position";
    public List<Result> mMostPopularList = new ArrayList<>();
    public static final String ITEMPOSITION = "webView_position";
    RecyclerViewAdapterMostPopular mAdapter;
    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private Disposable mDisposable;
    private int position;


    public MostPopularFragment() {
        // Required empty public constructor
    }

    public static MostPopularFragment newInstance(int position) {
        MostPopularFragment fragment = new MostPopularFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, result);
        position = getArguments().getInt(KEY_POSITION, -1);
        this.buildRecyclerView();
        this.executeMostPopularHttpRequest();
        this.configureSwipeRefrechLayout();
        this.progressBarHandler();


        return result;
    }

    /*
   * @method buildRecyclerView
   *
   * This method manages the recyclerView set up
   * */
    private void buildRecyclerView() {
        //Calling the adapter
        this.mAdapter = new RecyclerViewAdapterMostPopular(mMostPopularList, Glide.with(this));
        //Set them with natives methods
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //When user click on an item a new activity is launched to display a webView
        this.displayActivity();
    }

    /*
   * @method displayActivity
   *
   * Used to open a web view directly in the app, not by default application
   * */
    private void displayActivity() {
        this.mAdapter.setOnItemClickListener(new RecyclerViewAdapterMostPopular.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //Here we allow the toast text to appear on click
                Toast.makeText(getContext(),
                        "Click on item number " + position + "URL : " + mMostPopularList.get(position).getUrl(),
                        Toast.LENGTH_SHORT).show();

//                //Intent for the activity calling
//                Intent intent = new Intent(getContext(), WebViewActivity.class);
//                intent.putExtra(ITEMPOSITION, position);
//                startActivity(intent);

//                Here we are going to implements a web view
                WebView webview = new WebView(getContext());
                webview.loadUrl(mMostPopularList.get(position).getUrl());
            }
        });
    }

    private void executeMostPopularHttpRequest() {
        this.updateUIWhenStartingHTTPRequest();

        this.mDisposable = NewYorkTimeStream.streamFetchMostPopular()
                .subscribeWith(new DisposableObserver<MostPopular>() {

                    @Override
                    public void onNext(MostPopular MostpopularItems) {
                        Log.d("Most Popular", "On Next");
                        upDateUIMP(MostpopularItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Most Popular", "On Error" + Log.getStackTraceString(e));
                        internetDisable();
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

    //Called for better performances
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
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

            }
        });
    }

    private void progressBarHandler() {
        this.mProgressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
    }

    private void updateUIWhenStartingHTTPRequest() {
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    private void updateUIWhenStopingHTTPRequest(SwipeRefreshLayout refresh, ProgressBar bar) {
        bar.setVisibility(View.GONE);
        refresh.setRefreshing(false);
        this.mMostPopularList.clear();

    }

    private void internetDisable() {
        this.mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(),
                getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    private void upDateUIMP(MostPopular mostpopular) {
        updateUIWhenStopingHTTPRequest(mRefreshLayout, mProgressBar);
//        List<Result> resultList = mostpopular.getResults();
        this.mMostPopularList.addAll(mostpopular.getResults());
        this.mAdapter.notifyDataSetChanged();

    }

}
