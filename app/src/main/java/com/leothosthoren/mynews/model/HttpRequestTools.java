package com.leothosthoren.mynews.model;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.leothosthoren.mynews.R;

/**
 * Created by Sofiane M. alias Leothos Thoren on 06/04/2018
 * <p>
 * This class provide several methods which handles the UI behavior before, during and after the http request execution
 */
public class HttpRequestTools {

    public HttpRequestTools() {
    }

    public void progressBarHandler(ProgressBar progressBar, Context context) {
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
    }

    public void updateUIWhenStartingHTTPRequest(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void updateUIWhenStopingHTTPRequest(SwipeRefreshLayout refresh, ProgressBar bar) {
        bar.setVisibility(View.GONE);
        refresh.setRefreshing(false);

    }

    public void internetDisable(ProgressBar progressBar, String text, Context context) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
