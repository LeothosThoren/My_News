package com.leothosthoren.mynews.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leothosthoren.mynews.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.leothosthoren.mynews.controler.fragments.TopStoriesFragment.ITEMPOSITION;

/**
 * Created by Sofiane M. alias Leothos Thoren on 03/04/2018
 * We find here all kinds of useful methods that allow to avoid rewriting unnecessary code
 */
public class ModelTools {

    //Empty constructor
    public ModelTools() {
    }

    //=======================
    // STRING FORMATTER
    //=======================

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
            return setFormatCalendarMinusYear();
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
            if (strings[i] != null) {
                if (i > 0 && ((!strings[i].isEmpty())))
                    res.append(" ");
                res.append(strings[i]);
            }
        }
        //If result is empty it return "Culture" else it return the result content
        return res.toString().isEmpty() ? "Culture" : res.toString();
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

    //=======================
    // DATE FORMATTER
    //=======================

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
    * Get the current date minus 1 year and format into "yyyyMMdd" pattern
    * Mandatory for Api HTTP request
    * */
    public String setFormatCalendarMinusYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        return String.format("%s", sdf.format(cal.getTime()));
    }

    //=======================
    // OPEN WEB BROWSER
    //=======================

    /*
    * @method openWebBrowser
    * @param url
    * @param context
    *
    * Use to open a Uri thanks the WebViewer from the device
    * */
    @Deprecated
    public void openWebBrowser(String url, Context context) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /*
    * @method openActivityAsBrowser
    * @param url
    * @param context
    * @param aClass
    *
    * Start an activity by displaying a web content inside
    * */
    public void openActivityAsBrowser(String url, Context context, Class aClass) {
        Intent intent2 = new Intent(context, aClass);
        intent2.putExtra(ITEMPOSITION, url);
        context.startActivity(intent2);
    }

    //=======================
    // ALERTS & NOTIFICATIONS
    //=======================

    /*
    * @toastMessage
    * @param msg the message to display
    *
    * A simple reusable method which handle Toast message
    * */
    public void toastMessage(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /*
    * @method snackbarMessage
    * @param msg
    * @param view
    *
    * A simple reusable method which handle SnackBar message
    * Don't forget to use findViewById(R.id.activity...)
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void snackBarMessage(View view, int msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }

    //=======================
    //  CHECKBOX INPUT
    //=======================

    /*
    * @method
    * @param
    * @param
    *
    * Change color of all text boxes at once
    * */
    public void checkboxColorModifier(int color, CheckBox[] boxes) {
        for (CheckBox box : boxes) {
            box.setTextColor(color);
        }
    }

    public boolean onUncheckedBoxes(CheckBox[] boxes) {
        if (!(boxes[0].isChecked() || boxes[1].isChecked() || boxes[2].isChecked()
                || boxes[3].isChecked() || boxes[4].isChecked() || boxes[5].isChecked())) {
            checkboxColorModifier(Color.RED, boxes);
            return true;
        } else {
            checkboxColorModifier(Color.BLACK, boxes);
            return false;
        }

    }

    //=======================
    //  QUERY TEXT INPUT
    //=======================

    /*
    * @method displayErrorMessage
    * @param textInputLayout option which allow to change the editText behavior
    *
    * This method handle the search text widget, before and after text change
    * */
    public void displayErrorMessage(final TextInputLayout textInputLayout) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            //The text input set up an hint for the user
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textInputLayout.setHintEnabled(true);
            }

            //When user type something the error alert is disabled
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setErrorEnabled(false);
                textInputLayout.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /*
   * @queryInputIsEmpty
   * @param searchQuery
   * @textInputLayout display a red alert message
   *
   * This method check if the search query input is empty, and if yes an error message occur
   * */
    public boolean queryInputIsEmpty(EditText searchQuery, TextInputLayout textInputLayout, CharSequence msg) {
        if (searchQuery.getText().toString().isEmpty()) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(msg);
            return true;

        } else {
            textInputLayout.setErrorEnabled(false);
            return false;
        }
    }

}
