package com.leothosthoren.mynews.model;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Sofiane M. alias Leothos Thoren on 28/02/2018
 */
public class SetSearchDate implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private EditText editText;
    private Calendar mCalendar;

    /*
    * @method SetSearchDate
    * @param editText
    * @param context
    *
    * Public constructor
    * */
    public SetSearchDate(EditText editText, Context context){
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        mCalendar = Calendar.getInstance();
    }

    /*
    * @method onDateSet
    * @param view
    * @param year
    * @param monthOfYear
    * @param dayOfMonth
    *
    * Format the date value from the DatePicker widget
    * */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)     {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        editText.setText(sdformat.format(mCalendar.getTime()));

    }

    /*
   * @method onFocusChange
   * @param view
   * @param hasFocus
   *
   * The widget allow you to switch a value of the calendar
   * */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            new DatePickerDialog(v.getContext(), this, mCalendar
                    .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

}