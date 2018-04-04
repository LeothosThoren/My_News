package com.leothosthoren.mynews.controler.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.controler.fragments.SearchArticleFragment;
import com.leothosthoren.mynews.model.ModelTools;
import com.leothosthoren.mynews.model.SetSearchDate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchArticlesActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String SEARCH_ARTICLE_VALUES = "SEARCH_ARTICLE_VALUES";
    public String[] checkboxData = new String[6];
    public String[] BOX_VALUES = {"Arts", "Business", "Entrepreneurs", "Politics", "Sports", "Travels"};
    @BindView(R.id.query_text_input_layout)
    public TextInputLayout floatingHintLabel;
    public SearchArticleFragment mFragment;
    public ModelTools mFormater = new ModelTools();
    @BindView(R.id.search_query_term)
    EditText mSearchQuery;
    @BindView(R.id.end_date)
    EditText mEndDate;
    @BindView(R.id.begin_date)
    EditText mBeginDate;
    @BindView(R.id.search_activity_search_button)
    Button mSearchButton;
    @BindView(R.id.checkbox_1)
    CheckBox mCheckBox1;
    @BindView(R.id.checkbox_2)
    CheckBox mCheckBox2;
    @BindView(R.id.checkbox_3)
    CheckBox mCheckBox3;
    @BindView(R.id.checkbox_4)
    CheckBox mCheckBox4;
    @BindView(R.id.checkbox_5)
    CheckBox mCheckBox5;
    @BindView(R.id.checkbox_6)
    CheckBox mCheckBox6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.displayErrorMessage(floatingHintLabel);
        this.setSearchDate();
        this.mSearchButton.setOnClickListener(this);

    }

    /*
    * @method configureToolbar
    *
    * This method call the toolbar layout and fixe it on the action bar, a return home feature is displayed
    * */
    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configureAndShowMainFragment() {
        // A - Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mFragment = (SearchArticleFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_search_article_list);

        // A' - Get all the item for the Http request
        String[] value = {mSearchQuery.getText().toString(), mFormater.getNewDesk(checkboxData),
                mFormater.getBeginDate(mBeginDate.getText().toString()), mFormater.getEndDate(mEndDate.getText().toString())};

        // B - Initiate bundle
        Bundle bundle = new Bundle();
        bundle.putStringArray(SEARCH_ARTICLE_VALUES, value);

        if (mFragment == null) {
            // B' - Create new main fragment
            mFragment = new SearchArticleFragment();

            mFragment.setArguments(bundle);
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_search_article_list, mFragment)
                    .commit();
        }
    }


    //=======================
    //  TEXT QUERY INPUT
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
    public void queryInputIsEmpty(EditText searchQuery, TextInputLayout textInputLayout) {
        if (searchQuery.getText().toString().isEmpty()) {
            textInputLayout.setError(getResources().getString(R.string.query_error));
            textInputLayout.setErrorEnabled(true);
        } else {
            textInputLayout.setErrorEnabled(false);
            configureAndShowMainFragment();
        }

    }

    //=======================
    //  DATE INPUT
    //=======================

    /*
   * @setSearchDate
   *
   * This method allows to display the datePicker widget threw an edit text
   * */
    private void setSearchDate() {
        SetSearchDate beginDate = new SetSearchDate(mBeginDate, this);
        SetSearchDate endDate = new SetSearchDate(mEndDate, this);
    }

    //=======================
    //  CHECKBOX INPUT
    //=======================

    /*
    * @method onCheckboxClicked
    * @param view
    *
    * This method handle the checkboxes behavior on click.
    * If it's checked an action is executed
    * */
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        checkboxData[0] = BOX_VALUES[0];
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_1:
                if (checked) {
                    checkboxData[0] = BOX_VALUES[0];
                    toastMessage(checkboxData[0] + " is selected");
                    checkboxColorModifier(Color.BLACK);
                } else {
                    checkboxData[0] = "";
                    toastMessage(BOX_VALUES[0] + " is deselected");
                }
                break;
            case R.id.checkbox_2:
                if (checked) {
                    checkboxData[1] = BOX_VALUES[1];
                    toastMessage(checkboxData[1] + " is selected");
                    checkboxColorModifier(Color.BLACK);
                } else {
                    checkboxData[1] = "";
                    toastMessage(BOX_VALUES[1] + " is deselected");
                }
                break;
            case R.id.checkbox_3:
                if (checked) {
                    checkboxData[2] = BOX_VALUES[2];
                    toastMessage(checkboxData[2] + " is selected");
                    checkboxColorModifier(Color.BLACK);
                } else {
                    checkboxData[2] = "";
                    toastMessage(BOX_VALUES[2] + " is deselected");
                }
                break;
            case R.id.checkbox_4:
                if (checked) {
                    checkboxData[3] = BOX_VALUES[3];
                    toastMessage(checkboxData[3] + " is selected");
                    checkboxColorModifier(Color.BLACK);
                } else {
                    checkboxData[3] = "";
                    toastMessage(BOX_VALUES[3] + " is deselected");
                }
                break;
            case R.id.checkbox_5:
                if (checked) {
                    checkboxData[4] = BOX_VALUES[4];
                    toastMessage(checkboxData[4] + " is selected");
                    checkboxColorModifier(Color.BLACK);
                } else {
                    checkboxData[4] = "";
                    toastMessage(BOX_VALUES[4] + " is deselected");
                }
                break;
            case R.id.checkbox_6:
                if (checked) {
                    checkboxData[5] = BOX_VALUES[5];
                    toastMessage(checkboxData[5] + " is selected");
                    checkboxColorModifier(Color.BLACK);
                } else {
                    checkboxData[5] = "";
                    toastMessage(BOX_VALUES[5] + " is deselected");
                }
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
    public String boxSelection(boolean check, int id) {
        if (check) {
            checkboxData[id] = BOX_VALUES[id];
            toastMessage(checkboxData[id] + " is selected");
            checkboxColorModifier(Color.BLACK);
        } else {
            checkboxData[id] = "";
            toastMessage(BOX_VALUES[id] + " is deselected");
        }

        return checkboxData[id];
    }

    /*
    * @method checkboxColorModifier
    * @param color
    *
    * This method allows to change the color of every checkboxes at once
    * */
    public void checkboxColorModifier(int color) {
        mCheckBox1.setTextColor(color);
        mCheckBox2.setTextColor(color);
        mCheckBox3.setTextColor(color);
        mCheckBox4.setTextColor(color);
        mCheckBox5.setTextColor(color);
        mCheckBox6.setTextColor(color);
    }

    /*
    * @method onUncheckedBoxes
    *
    * This method warns when all the checkboxes are unchecked
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onUncheckedBoxes() {
        if (!(mCheckBox1.isChecked() || mCheckBox2.isChecked() || mCheckBox3.isChecked()
                || mCheckBox4.isChecked() || mCheckBox5.isChecked() || mCheckBox6.isChecked())) {
            checkboxColorModifier(Color.RED);
            //The sanckbar is display here to explain the user what to do
            snackBarMessage(R.string.box_unchecked);
        }
    }

    //=======================
    //  SEARCH BUTTON
    //=======================

    /*
    * @method onClick
    * @param v
    *
    * The search button performs all the action of control and check
    * It calls the api
    * Verify if all the inputs are corrects
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {

        // handle query not empty, otherwise toast text alert
        queryInputIsEmpty(mSearchQuery, floatingHintLabel);
        //When all the checkboxes are unchecked
        onUncheckedBoxes();

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
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /*
    * @method snackbarMessage
    * @param msg
    *
    * A simple reusable method which handle SnackBar message
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void snackBarMessage(int msg) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.activity_search_article), msg, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }

}