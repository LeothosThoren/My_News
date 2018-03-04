package com.leothosthoren.mynews.controler.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
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
import com.leothosthoren.mynews.model.SetSearchDate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchArticlesActivity extends AppCompatActivity implements View.OnClickListener {
    public String[] checkboxData = new String[6];
    public String[] BOX_VALUES = {"Arts", "Business", "Entrepreneurs", "Politics", "Sports", "Travels"};
    @BindView(R.id.search_query_term)
    EditText mSearchQuery;
    @BindView(R.id.begin_date)
    EditText mBeginDate;
    @BindView(R.id.end_date)
    EditText mEndDate;
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
    @BindView(R.id.query_text_input_layout)
    TextInputLayout floatingHintLabel;

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {

        //Todo :
        // handle query not empty, otherwise toast text alert
        queryInputIsEmpty(mSearchQuery, floatingHintLabel);
        //When all the checkboxes are unchecked
        onUncheckedBoxes();

        // launch http request,
        // check if result not null otherwise toast text alert
        // open view list recycler view
        //Open webView from recycler view
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void displayErrorMessage(final TextInputLayout textInputLayout) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textInputLayout.setHintEnabled(true);
            }

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

    private void setSearchDate() {
        SetSearchDate beginDate = new SetSearchDate(mBeginDate, this);
        SetSearchDate endDate = new SetSearchDate(mEndDate, this);
    }

    public void queryInputIsEmpty(EditText searchQuery, TextInputLayout textInputLayout) {
        if (searchQuery.getText().toString().isEmpty()) {
            textInputLayout.setError("You have to edit a query term search!!");
            textInputLayout.setErrorEnabled(true);
        } else
            textInputLayout.setErrorEnabled(false);

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_1:
                boxSelection(checked, checkboxData[0], BOX_VALUES[0]);
                break;
            case R.id.checkbox_2:
                boxSelection(checked, checkboxData[1], BOX_VALUES[1]);
                break;
            case R.id.checkbox_3:
                boxSelection(checked, checkboxData[2], BOX_VALUES[2]);
                break;
            case R.id.checkbox_4:
                boxSelection(checked, checkboxData[3], BOX_VALUES[3]);
                break;
            case R.id.checkbox_5:
                boxSelection(checked, checkboxData[4], BOX_VALUES[4]);
                break;
            case R.id.checkbox_6:
                boxSelection(checked, checkboxData[5], BOX_VALUES[5]);
                break;
        }
    }

    public void boxSelection(boolean check, String key, String value) {
        if (check) {
            key = value;
            toastMessage(key + " is selected");
            checkboxColorModifier(Color.BLACK);
        } else {
            key = "";
            toastMessage(value + " is deselected");
        }
    }


    public void checkboxColorModifier(int color) {
        mCheckBox1.setTextColor(color);
        mCheckBox2.setTextColor(color);
        mCheckBox3.setTextColor(color);
        mCheckBox4.setTextColor(color);
        mCheckBox5.setTextColor(color);
        mCheckBox6.setTextColor(color);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onUncheckedBoxes() {
        if (!(mCheckBox1.isChecked() || mCheckBox2.isChecked() || mCheckBox3.isChecked()
                || mCheckBox4.isChecked() || mCheckBox5.isChecked() || mCheckBox6.isChecked())) {
            checkboxColorModifier(Color.RED);
            //The sanckbar is display here to explain the user what to do
            snackbarMessage(R.string.box_unchecked);
        }
    }

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void snackbarMessage(int msg) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.activity_search_article), msg, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }

}