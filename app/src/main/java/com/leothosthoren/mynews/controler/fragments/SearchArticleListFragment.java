package com.leothosthoren.mynews.controler.fragments;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.leothosthoren.mynews.model.SearchArticle;
import com.leothosthoren.mynews.model.Utils.NewYorkTimeStream;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapter;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapterMostPopular;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapterSearchArticle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchArticleListFragment extends Fragment {

    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private Disposable mDisposable;
    private RecyclerViewAdapterSearchArticle mAdapterSearchArticle;
    public static final List<SearchArticle.Doc> mDocArrayList = new ArrayList<>();

    public SearchArticleListFragment() {
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
        mAdapterSearchArticle = new RecyclerViewAdapterSearchArticle(mDocArrayList, Glide.with(this));
        //Set them with natives methods
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapterSearchArticle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //When user click on an item a new activity is launched to display a webView
        this.displayActivity();
    }

    /*
  * @method displayActivity
  *
  * Used to open a web view directly in the app, not by default application
  * */
    private void displayActivity() {
        mAdapterSearchArticle.setOnItemClickListener(new RecyclerViewAdapterSearchArticle.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                //Intent for the activity calling
                Intent intent = new Intent(getContext(), WebViewActivity.class);
//                intent.putExtra(ITEMPOSITION, position);
                startActivity(intent);
            }
        });
    }

    private void executeSearchArticleHttpRequest() {
        this.updateUIWhenStartingHTTPRequest();

        this.mDisposable = NewYorkTimeStream.streamFetchSearchArticle()
                .subscribeWith(new DisposableObserver<SearchArticle.Response>() {

                    @Override
                    public void onNext(SearchArticle.Response article) {
                        Log.d("Search Article", "On Next");
                        upDateUISearchArticle(article);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Search Article", "On Error" + Log.getStackTraceString(e));
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
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    private void progressBarHandler() {
        mProgressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
    }

    private void updateUIWhenStartingHTTPRequest() {
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    private void updateUIWhenStopingHTTPRequest(SwipeRefreshLayout refresh, ProgressBar bar, List<?> objectList) {
        bar.setVisibility(View.GONE);
        refresh.setRefreshing(false);
        objectList.clear();

    }

    private void internetDisable() {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(),
                getString(R.string.no_internet), Toast.LENGTH_LONG).show();
    }

    private void upDateUISearchArticle(SearchArticle.Response searchArticle) {
        updateUIWhenStopingHTTPRequest(mRefreshLayout, mProgressBar, mDocArrayList);
        List<SearchArticle.Doc> resultList = searchArticle.getDocs();
        mDocArrayList.addAll(resultList);
        mAdapterSearchArticle.notifyDataSetChanged();

    }

}
