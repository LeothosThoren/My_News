package com.leothosthoren.mynews.controler.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.HelperTools;
import com.leothosthoren.mynews.model.HttpRequestTools;
import com.leothosthoren.mynews.model.apis.articles.SearchArticle;
import com.leothosthoren.mynews.model.utility.NewYorkTimeStream;
import com.leothosthoren.mynews.view.adapters.RecyclerViewAdapterSearchArticle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.leothosthoren.mynews.controler.activities.SearchArticlesActivity.SEARCH_ARTICLE_VALUES;

public class SearchArticleListActivity extends AppCompatActivity {

    public List<SearchArticle.Doc> mDocArrayList = new ArrayList<>();
    @BindView(R.id.frag_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.frag_swipe_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private RecyclerView.LayoutManager mLayoutManager;
    private Disposable mDisposable;
    private RecyclerViewAdapterSearchArticle mAdapterSearchArticle;
    private HelperTools mTools = new HelperTools();
    private HttpRequestTools mHttpRequestTools = new HttpRequestTools();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article_list);
        ButterKnife.bind(this);

        this.buildRecyclerView();
        this.mHttpRequestTools.progressBarHandler(mProgressBar, this);
        this.configureSwipeRefrechLayout();
        this.executeSearchArticleHttpRequest();
        configureToolbar();
    }

    /*
   * @method configureToolbar
   *
   * This method call the toolbar layout and fixes it on the action bar,
   * a return home feature is displayed
   * */
    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                mTools.openActivityAsBrowser(mDocArrayList.get(position).getWebUrl(), SearchArticleListActivity.this, WebViewActivity.class);
            }
        });

    }

    /*
  * @method executeSearchArticleHttpRequest
  *
  * Http rest request which take a model object as parameter and display a result
  * */
    private void executeSearchArticleHttpRequest() {
        this.mHttpRequestTools.updateUIWhenStartingHTTPRequest(mProgressBar);
        //Get the array content to provide query to the http request
        String[] mDataValues = getIntent().getStringArrayExtra(SEARCH_ARTICLE_VALUES);

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
                        mHttpRequestTools.internetDisable(mProgressBar, getString(R.string.no_internet), getBaseContext());
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

    /*
    * @method upDateUISearchArticle
    * @param searchArticle
    *
    * Update the UI from the recycler view with the adapter
    * */
    private void upDateUISearchArticle(SearchArticle searchArticle) {
        this.mHttpRequestTools.updateUIWhenStopingHTTPRequest(mRefreshLayout, mProgressBar);
        this.mDocArrayList.clear();
        this.mDocArrayList.addAll(searchArticle.getResponse().getDocs());
        this.mAdapterSearchArticle.notifyDataSetChanged();
    }

}

