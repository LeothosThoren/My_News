package com.leothosthoren.mynews.model.utility;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.controler.activities.WebViewActivity;
import com.leothosthoren.mynews.model.ModelTools;
import com.leothosthoren.mynews.model.apis.articles.SearchArticle;

import java.net.URL;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.leothosthoren.mynews.controler.activities.NotificationActivity.SEARCH_ARTICLE_NOTIFICATION_VALUES;
import static com.leothosthoren.mynews.controler.fragments.TopStoriesFragment.ITEMPOSITION;

/**
 * Created by Sofiane M. alias Leothos Thoren on 10/04/2018
 */
public class AlarmReceiver extends BroadcastReceiver {

    private Disposable mDisposable;
    private ModelTools mTools = new ModelTools();
    private String httpResult = "";
    private String subText = "";
    private String url = "";
    //todo : get datas from activity
    private String[] datas = new String[2];

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        //Load the view and variables
        datas = intent.getStringArrayExtra(SEARCH_ARTICLE_NOTIFICATION_VALUES);
        //First the http request is launched
        executeNotificationHttpRequest(context, intent);
        //During the process it check if new article are available

    }

    public void sendNotification(Context context) {

        final int NOTIFICATION_ID = 007;
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ITEMPOSITION, url);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //Get an instance of NotificationManager//

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(context.getString(R.string.text_notification))
                        .setSubText(subText)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    /**/
    private void executeNotificationHttpRequest(final Context context, Intent intent) {


        this.mDisposable = NewYorkTimeStream.streamFetchNotifications("France",
                "news_desk:(Foreign Politic Sports Environement Technology Culture)")
        //todo : know why it doesn't work
//        this.mDisposable = NewYorkTimeStream.streamFetchNotifications(datas[0],
//                "news_desk:("+datas[1]+")")
                .subscribeWith(new DisposableObserver<SearchArticle>() {

                    @Override
                    public void onNext(SearchArticle articleNotification) {
                        httpResult = articleNotification.getResponse().getDocs().get(0).getPubDate();
                        subText = articleNotification.getResponse().getDocs().get(0).getWebUrl();
                        url = articleNotification.getResponse().getDocs().get(0).getWebUrl();

                        Log.d("Notifications", "On Next: " + mTools.setFormatCalendar() + " VS " + mTools.getNotificationFormatDate(httpResult));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Notifications", "On Error " + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Notifications", "On Complete !");

                        if (mTools.getNotificationFormatDate(httpResult).equals(mTools.setFormatCalendar())) {
                            sendNotification(context);
                        }
                    }
                });
    }

    // Dispose subscription
    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
        onDestroy();
    }

    //Called for better performances
    public void onDestroy() {
        this.disposeWhenDestroy();
    }
}
