package com.leothosthoren.mynews.model;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.leothosthoren.mynews.R;

/**
 * Created by Leothos_Thoren on 18/03/2018.
 */

public class SearchArticlesItems {

    private static String[] checkboxData = new String[6];
    private static String[] BOX_VALUES = {"Arts", "Business", "Entrepreneurs", "Politics", "Sports", "Travels"};

    public SearchArticlesItems() {
    }

    /*
     * @method onCheckboxClicked
     * @param view
     *
     * This method handle the checkboxes behavior on click.
     * If it's checked an action is executed
     * */
    public static void onCheckboxClicked(View view, Context context, CheckBox[] checkBoxes) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_1:
                boxSelection(checked, checkboxData[0], BOX_VALUES[0], context, checkBoxes);
                break;
            case R.id.checkbox_2:
                boxSelection(checked, checkboxData[1], BOX_VALUES[1], context, checkBoxes);
                break;
            case R.id.checkbox_3:
                boxSelection(checked, checkboxData[2], BOX_VALUES[2], context, checkBoxes);
                break;
            case R.id.checkbox_4:
                boxSelection(checked, checkboxData[3], BOX_VALUES[3], context, checkBoxes);
                break;
            case R.id.checkbox_5:
                boxSelection(checked, checkboxData[4], BOX_VALUES[4], context, checkBoxes);
                break;
            case R.id.checkbox_6:
                boxSelection(checked, checkboxData[5], BOX_VALUES[5], context, checkBoxes);
                break;
        }
    }

    /*
  * @method boxSelection
  * @param check boolean holder
  * @param key is variable where data is stored
  * @param value the data to store
  *
  * This method works with the method onCheckboxClicked, two actions are performed:
  * -a checkbox is selected a value is hold
  * -otherwise it's blanked
  * */
    public static void boxSelection(boolean check, String key, String value, Context ctxt, CheckBox[] checkBoxes) {
        if (check) {
            key = value;
            toastMessage(ctxt, key + " is selected");
            checkboxColorModifier(Color.BLACK, checkBoxes);
        } else {
            key = "";
            toastMessage(ctxt, value + " is deselected");
        }
    }

    /*
    * @method checkboxColorModifier
    * @param color
    *
    * This method allows to change the color of every checkboxes at once
    * */
    public static void checkboxColorModifier(int color, CheckBox[] checkBoxes) {
        checkBoxes[0].setTextColor(color);
        checkBoxes[1].setTextColor(color);
        checkBoxes[2].setTextColor(color);
        checkBoxes[3].setTextColor(color);
        checkBoxes[4].setTextColor(color);
        checkBoxes[5].setTextColor(color);
    }

    /*
    * @method onUncheckedBoxes
    *
    * This method warns when all the checkboxes are unchecked
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void onUncheckedBoxes(View view, CheckBox[] checkBoxes) {
        if (!(checkBoxes[0].isChecked() || checkBoxes[1].isChecked() || checkBoxes[2].isChecked()
                || checkBoxes[3].isChecked() || checkBoxes[4].isChecked() || checkBoxes[5].isChecked())) {
            checkboxColorModifier(Color.RED, checkBoxes);
            //The snackbar is display here to explain the user what to do
            snackBarMessage(R.string.box_unchecked, view);
        }
    }

    /*
   * @toastMessage
   * @param msg the message to display
   *
   * A simple reusable method which handle Toast message
   * */
    public static void toastMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /*
    * @method snackbarMessage
    * @param msg
    *
    * A simple reusable method which handle SnackBar message
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void snackBarMessage(int msg, View v) {
        Snackbar snackbar = Snackbar
                .make(v, msg, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }
}
