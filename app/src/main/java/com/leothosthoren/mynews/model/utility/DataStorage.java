package com.leothosthoren.mynews.model.utility;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sofiane M. alias Leothos Thoren on 13/04/2018
 * DataStorage is a class which handle the shared preferences and data
 */
public class DataStorage {

    public static final String SEARCH_ARTICLE_NOTIFICATION_VALUES = "SEARCH_ARTICLE_NOTIFICATION_VALUES";
    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";

    public DataStorage() {
    }

    /*
    * @method saveData
    * @param context
    * @param s
    *
    * Allow to save data with SharedPreferences
    * */
    public void saveData(Context context, String s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SEARCH_ARTICLE_NOTIFICATION_VALUES, s);
        editor.apply();
    }

    /*
    * @method loadData
    * @param context
    *
    * Allow to retrieve data with SharedPreferences and use it
    * */
    public String loadData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(SEARCH_ARTICLE_NOTIFICATION_VALUES, null);
    }

}
