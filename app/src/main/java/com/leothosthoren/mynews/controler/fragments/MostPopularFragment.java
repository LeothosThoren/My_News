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
import com.leothosthoren.mynews.model.apis.articles.MostPopular;
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
    public List<MostPopular.Result> mMostPopularList = new ArrayList<>();
    RecyclerViewAdapterMostPopular mAdapter;
    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    private HttpRequestTools mHttpRequestTools = new HttpRequestTools();
    private ModelTools mTools = new ModelTools();
    private RecyclerView.LayoutManager mLayoutManager;
    private Disposable mDisposable;


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
        this.buildRecyclerView();
        this.executeMostPopularHttpRequest();
        this.configureSwipeRefrechLayout();
        this.mHttpRequestTools.progressBarHandler(mProgressBar, getContext());

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
        this.displayWebView();
    }

    /*
   * @method displayActivity
   *
   * Used to open a web view directly in the app, not by default application
   * */
    private void displayWebView() {
        this.mAdapter.setOnItemClickListener(new RecyclerViewAdapterMostPopular.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mTools.openActivityAsBrowser(mMostPopularList.get(position).getUrl(), getContext(), WebViewActivity.class);
            }
        });
    }


    private void executeMostPopularHttpRequest() {
        this.mHttpRequestTools.updateUIWhenStartingHTTPRequest(mProgressBar);

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
                        mHttpRequestTools.internetDisable(mProgressBar, getString(R.string.no_internet), getContext());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Most Popular", "On Complete !");
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
                executeMostPopularHttpRequest();
            }
        });
    }

    private void upDateUIMP(MostPopular mostpopular) {
        mHttpRequestTools.updateUIWhenStopingHTTPRequest(mRefreshLayout, mProgressBar);
        this.mMostPopularList.clear();
        this.mMostPopularList.addAll(mostpopular.getResults());
        this.mAdapter.notifyDataSetChanged();

    }

}
