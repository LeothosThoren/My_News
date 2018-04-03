package com.leothosthoren.mynews.model.Utils;

/**
 * Created by Leothos_Thoren on 18/03/2018.
 */

public class HttpConnectivity {


//    public static void executeSearchArticleHttpRequest(final Context context, Disposable mDisposable, final ProgressBar progressBar, final String msg ) {
//        updateUIWhenStartingHTTPRequest(progressBar,context);
//
//        mDisposable = NewYorkTimeStream.streamFetchSearchArticle(String query)
//                .subscribeWith(new DisposableObserver<SearchArticle>() {
//
//                    @Override
//                    public void onNext(SearchArticle article) {
//                        Log.d("Search Article Bis", "On Next");
//                       // upDateUISearchArticle(article);
////                            mTextViewTest.setText(article.getResponse().getDocs().get(0).getSectionName());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("Search Article", "On Error" + Log.getStackTraceString(e));
//                        internetDisable(progressBar,context, msg);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("Search Article", "On Complete !");
//                    }
//                });
//    }
//
//
//    // ------------------
//    //  UPDATE UI
//    // ------------------
//
//    /*
//   * @method configureSwipeRefrechLayout
//   *
//   * When the screen is swipe, the http request is executed
//   * */
//    public static void configureSwipeRefrechLayout(SwipeRefreshLayout refreshLayout) {
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });
//    }
//
//    public static ProgressBar progressBarHandler(ProgressBar progressBar, Context context) {
//        progressBar.getIndeterminateDrawable()
//                .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
//        return progressBar;
//    }
//
//    public static void updateUIWhenStartingHTTPRequest(ProgressBar progressBar, Context context) {
//        progressBarHandler(progressBar, context).setVisibility(View.VISIBLE);
//    }
//
//    public static void updateUIWhenStopingHTTPRequest(SwipeRefreshLayout refresh, ProgressBar bar, List<?> objectList) {
//        bar.setVisibility(View.GONE);
//        refresh.setRefreshing(false);
//        objectList.clear();
//
//    }
//
//    public static void internetDisable(ProgressBar progressBar, Context context, String msg) {
//        progressBar.setVisibility(View.GONE);
//        Toast.makeText(context,
//                msg, Toast.LENGTH_LONG).show();
//    }


}
