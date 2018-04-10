package com.leothosthoren.mynews.model.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sofiane M. alias Leothos Thoren on 10/04/2018
 */
public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Manager processing... "+ showDate(), Toast.LENGTH_SHORT).show();
        //Add http request and condition with the date
    }

    public String showDate() {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return simpleDateFormat.format(today);

    }
}
