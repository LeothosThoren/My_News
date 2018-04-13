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
import com.leothosthoren.mynews.model.HelperTools;
import com.leothosthoren.mynews.model.apis.articles.SearchArticle;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.leothosthoren.mynews.controler.activities.NotificationActivity.SEARCH_ARTICLE_NOTIFICATION_VALUES;
import static com.leothosthoren.mynews.controler.fragments.TopStoriesFragment.ITEMPOSITION;

/**
 * Created by Sofiane M. alias Leothos Thoren on 10/04/2018
 */
public class AlarmReceiver extends BroadcastReceiver {

    private Disposable mDisposable;
    private HelperTools mTools = new HelperTools();
    private String httpResult = "";
    private String subTextUrl = "";

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        //Load the view and variables
        //Http request is launched, during the process it check if new article are available
        executeNotificationHttpRequest(context, intent);
    }

    /**/
    public void sendNotification(Context context) {

        final int NOTIFICATION_ID = 007;
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ITEMPOSITION, subTextUrl);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //Get an instance of NotificationManager//

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(context.getString(R.string.text_notification))
                        .setSubText(subTextUrl)
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

        String[] data = intent.getStringArrayExtra(SEARCH_ARTICLE_NOTIFICATION_VALUES);

        this.mDisposable = NewYorkTimeStream.streamFetchNotifications("France",
                "news_desk:(Foreign Politic Sports Environement Technology Culture)")
        //todo : know why it doesn't work
//        this.mDisposable = NewYorkTimeStream.streamFetchNotifications(data[0],
//                "news_desk:(" + data[1] + ")")
                .subscribeWith(new DisposableObserver<SearchArticle>() {

                    @Override
                    public void onNext(SearchArticle articleNotification) {
                            httpResult = articleNotification.getResponse().getDocs().get(0).getPubDate();
                            subTextUrl = articleNotification.getResponse().getDocs().get(0).getWebUrl();

                        Log.d("Notifications", "On Next: " + mTools.setFormatCalendar() + " VS " + mTools.getNotificationFormatDate(httpResult));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Notifications", "On Error " + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Notifications", "On Complete !");
                        Toast.makeText(context, "Alarm manager ok", Toast.LENGTH_SHORT).show();
                        if (httpResult != null && mTools.getNotificationFormatDate(httpResult).equals(mTools.setFormatCalendar())) {
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
