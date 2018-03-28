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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.MostPopular;
import com.leothosthoren.mynews.model.SetSearchDate;
import com.leothosthoren.mynews.model.TopStories;
import com.leothosthoren.mynews.model.Utils.NewYorkTimeStream;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchArticlesActivity extends AppCompatActivity implements View.OnClickListener {
    public String[] checkboxData = new String[6];
    public String[] BOX_VALUES = {"Arts", "Business", "Entrepreneurs", "Politics", "Sports", "Travels"};
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
    @BindView(R.id.query_text_input_layout)
    TextInputLayout floatingHintLabel;
    @BindView(R.id.textTest) TextView textTest;
    private Disposable mDisposable;
    private final List<MostPopular.Result> mResultList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.displayErrorMessage(floatingHintLabel);
        this.setSearchDate();
        this.mSearchButton.setOnClickListener(this);

        executeHttpRequestWithRetrofit();

    }

    public void executeHttpRequestWithRetrofit() {
        textTest.setText("Start");

        this.mDisposable = NewYorkTimeStream.streamFetchMostPopular()
                .subscribeWith(new DisposableObserver<MostPopular>() {

                    @Override
                    public void onNext(MostPopular topStoriesItems) {
                        Log.d("TAG", "On Next");

                        mResultList.add(topStoriesItems.getResults().get(0));
                        textTest.setText(mResultList.get(0).getUrl());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG", "On Error" + Log.getStackTraceString(e));
                        textTest.setText("Error");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "On Complete !");
                    }
                });
    }

    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }
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

        //Todo :
        // handle query not empty, otherwise toast text alert
        queryInputIsEmpty(mSearchQuery, floatingHintLabel);
        //When all the checkboxes are unchecked
        onUncheckedBoxes();


        // launch http request,
        // check if result not null otherwise toast text alert
        // open view list recycler view
        // Open webView from recycler view
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
    * @setSearchDate
    *
    * This method allows to display the datePicker widget threw an edit text
    * */
    private void setSearchDate() {
        SetSearchDate beginDate = new SetSearchDate(mBeginDate, this);
        SetSearchDate endDate = new SetSearchDate(mEndDate, this);
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
        } else
            textInputLayout.setErrorEnabled(false);

    }

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