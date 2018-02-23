package com.leothosthoren.mynews.controler.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.leothosthoren.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.leothosthoren.mynews.controler.fragments.PageFragment.ITEMPOSITION;
import static com.leothosthoren.mynews.controler.fragments.PageFragment.mTopStoriesArray;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)

    Toolbar mToolbar;
    @BindView(R.id.web_view)

    WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        configureToolbar();
        displayWebView();
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void displayWebView() {
        int pos = getIntent().getIntExtra(ITEMPOSITION, 0);

        mWebView.loadUrl(mTopStoriesArray.get(pos).getUrl());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
}
