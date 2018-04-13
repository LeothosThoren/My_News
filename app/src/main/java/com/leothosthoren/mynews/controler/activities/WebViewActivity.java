package com.leothosthoren.mynews.controler.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.leothosthoren.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.leothosthoren.mynews.controler.fragments.TopStoriesFragment.ITEMPOSITION;


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
        WebViewReader();

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
    * @method displayWebView
    *
    * Load and display an Uri into a web page view
    * The webView get an Uri from an  array object method
    * */
    private void WebViewReader() {
        String uri = getIntent().getStringExtra(ITEMPOSITION);
            mWebView.loadUrl(uri);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

}
