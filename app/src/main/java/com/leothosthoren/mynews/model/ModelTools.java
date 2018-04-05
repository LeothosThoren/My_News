package com.leothosthoren.mynews.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Sofiane M. alias Leothos Thoren on 03/04/2018
 */
public class ModelTools {

    //Empty constructor
    public ModelTools() {
    }

    /*
    * @method getEndDate
    * @param ed
    *
    * Format the date string for the Api, if it's empty it gets the current date
    * */
    public String getEndDate(String ed) {
        if (ed.isEmpty())
            return setFormatCalendar();
        String[] fDate = ed.split("/");
        return String.format("%s%s%s", fDate[2], fDate[1], fDate[0]);
    }

    /*
   * @method getBeginDate
   * @param bd
   *
   * Format the date string for the Api, if it's empty it gets the current date - 10 years
   * */
    public String getBeginDate(String bd) {
        if (bd.isEmpty())
            return setFormatCalendarMinusMonth();
        String[] fDate = bd.split("/");
        return String.format("%s%s%s", fDate[2], fDate[1], fDate[0]);
    }


    /*
    * @method getNewDesk
    * @param strings
    *
    * Get all the value of the checkBox widget into a single string (using Lucene syntax)
    * */
    public String getNewDesk(String[] strings) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != null ) {
                if (i > 0 && ((!strings[i].isEmpty())))
                    res.append(" ");
                res.append(strings[i]);
            }
        }
        //If result is empty it return "Culture" else it return the result content
        return res.toString().isEmpty() ?  "Culture" : res.toString() ;
    }


    /*
    * @method getItemArticleFormatedDate
    * @param dateToChange
    *
    *Handle the date string format on the item of the recycler view
    * */
    public String getItemArticleFormatedDate(String dateToChange) {
        String sub[] = dateToChange.substring(2, 10).split("-");
        return String.format("%s/%s/%s", sub[2], sub[1], sub[0]);
    }

    /*
    * @method setFormatCalendar
    *
    * Get the current date and format into "yyyyMMdd" pattern
    * Mandatory for Api HTTP request
    * */
    public String setFormatCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        return String.format("%s", sdf.format(cal.getTime()));
    }

    /*
    * @method setFormatCalendarMinusYear
    *
    * Get the current date minus 1 month and format into "yyyyMMdd" pattern
    * Mandatory for Api HTTP request
    * */
    public String setFormatCalendarMinusMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        return String.format("%s", sdf.format(cal.getTime()));
    }

    public void openWebBrowser(String url, Context context){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}
