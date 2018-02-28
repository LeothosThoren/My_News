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
public class SetDate implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private EditText editText;
    private Calendar mCalendar;


    public SetDate(EditText editText, Context context){
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        mCalendar = Calendar.getInstance();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)     {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.US);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, monthOfYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        editText.setText(sdformat.format(mCalendar.getTime()));

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            new DatePickerDialog(v.getContext(), this, mCalendar
                    .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

}