package com.leothosthoren.mynews.controler.fragments;


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
import com.leothosthoren.mynews.model.ModelTools;
import com.leothosthoren.mynews.model.Utils.NewYorkTimeStream;
import com.leothosthoren.mynews.model.apis.articles.SearchArticle;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapterSearchArticle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.leothosthoren.mynews.controler.activities.SearchArticlesActivity.SEARCH_ARTICLE_VALUES;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchArticleFragment extends Fragment {

    public List<SearchArticle.Doc> mDocArrayList = new ArrayList<>();
    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private Disposable mDisposable;
    private RecyclerViewAdapterSearchArticle mAdapterSearchArticle;
    private ModelTools mTools = new ModelTools();


    public SearchArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_article_list, container, false);
        ButterKnife.bind(this, v);
        this.buildRecyclerView();
        this.progressBarHandler();
        this.configureSwipeRefrechLayout();
        this.executeSearchArticleHttpRequest();

        return v;
    }

    /*
   * @method buildRecyclerView
   *
   * This method manages the recyclerView set up
   * */
    private void buildRecyclerView() {
        //Calling the adapter
        this.mAdapterSearchArticle = new RecyclerViewAdapterSearchArticle(mDocArrayList, Glide.with(this));
        //Set them with natives methods
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(mAdapterSearchArticle);
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
        this.mAdapterSearchArticle.setOnItemClickListener(new RecyclerViewAdapterSearchArticle.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mTools.openWebBrowser(mDocArrayList.get(position).getWebUrl(), getContext());
            }
        });

    }

    private void executeSearchArticleHttpRequest() {
        this.updateUIWhenStartingHTTPRequest();


        String[] mDataValues = getArguments().getStringArray(SEARCH_ARTICLE_VALUES);
        //mDataValues[0] == query, mDataValues[1] == new_desk, mDataValues[2] == begin_date, mDataValues[3] == endDate
        this.mDisposable = NewYorkTimeStream.streamFetchSearchArticle(mDataValues[0], "news_desk:(" + mDataValues[1] + ")", mDataValues[2], mDataValues[3])
                .subscribeWith(new DisposableObserver<SearchArticle>() {

                    @Override
                    public void onNext(SearchArticle article) {
                        Log.d("Search Article", "On Next");
                        upDateUISearchArticle(article);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Search Article", "On Error " + Log.getStackTraceString(e));
                        internetDisable();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Search Article", "On Complete !");
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
                executeSearchArticleHttpRequest();
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
        this.mDocArrayList.clear();

    }

    private void internetDisable() {
        this.mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(),
                getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    private void upDateUISearchArticle(SearchArticle searchArticle) {
        updateUIWhenStopingHTTPRequest(mRefreshLayout, mProgressBar);
        this.mDocArrayList.addAll(searchArticle.getResponse().getDocs());
        this.mAdapterSearchArticle.notifyDataSetChanged();
    }

}
