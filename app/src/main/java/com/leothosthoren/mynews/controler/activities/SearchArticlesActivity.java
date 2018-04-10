package com.leothosthoren.mynews.controler.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.controler.fragments.SearchArticleFragment;
import com.leothosthoren.mynews.model.ModelTools;
import com.leothosthoren.mynews.model.SetSearchDate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchArticlesActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SEARCH_ARTICLE_VALUES = "SEARCH_ARTICLE_VALUES";
    public String[] checkboxData = new String[6];
    public String[] BOX_VALUES = {"Culture", "Environement", "Foreign", "Politics", "Sports", "Technology"};
    @BindView(R.id.query_text_input_layout)
    public TextInputLayout floatingHintLabel;
    public SearchArticleFragment mFragment;
    public ModelTools mTools = new ModelTools();
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
    private CheckBox[] mCheckBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);
        ButterKnife.bind(this);

        this.mCheckBoxes = new CheckBox[]{mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6};

        this.configureToolbar();
        this.mTools.displayErrorMessage(floatingHintLabel);
        this.setSearchDate();
        this.mSearchButton.setOnClickListener(this);

    }

    //=======================
    //  CONFIGURATIONS
    //=======================

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
        String[] value = {mSearchQuery.getText().toString(), mTools.getNewDesk(checkboxData),
                mTools.getBeginDate(mBeginDate.getText().toString()), mTools.getEndDate(mEndDate.getText().toString())};

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


//    //=======================
//    //  QUERY TEXT INPUT
//    //=======================
//
//    /*
//    * @method displayErrorMessage
//    * @param textInputLayout option which allow to change the editText behavior
//    *
//    * This method handle the search text widget, before and after text change
//    * */
//    public void displayErrorMessage(final TextInputLayout textInputLayout) {
//        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
//            //The text input set up an hint for the user
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                textInputLayout.setHintEnabled(true);
//            }
//
//            //When user type something the error alert is disabled
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                textInputLayout.setErrorEnabled(false);
//                textInputLayout.setEnabled(true);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }

    /*
    * @queryInputIsEmpty
    * @param searchQuery
    * @textInputLayout display a red alert message
    *
    * This method check if the search query input is empty, and if yes an error message occur
    * */
//    public void queryInputIsEmpty(EditText searchQuery, TextInputLayout textInputLayout) {
//        if (searchQuery.getText().toString().isEmpty()) {
//            textInputLayout.setErrorEnabled(true);
//            textInputLayout.setError(getResources().getString(R.string.query_error));
//
//        } else {
//            textInputLayout.setErrorEnabled(false);
//
//        }
//
//    }

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
                } else {
                    checkboxData[0] = "";
                }
                break;
            case R.id.checkbox_2:
                if (checked) {
                    checkboxData[1] = BOX_VALUES[1];
                } else {
                    checkboxData[1] = "";
                }
                break;
            case R.id.checkbox_3:
                if (checked) {
                    checkboxData[2] = BOX_VALUES[2];
                } else {
                    checkboxData[2] = "";
                }
                break;
            case R.id.checkbox_4:
                if (checked) {
                    checkboxData[3] = BOX_VALUES[3];
                } else {
                    checkboxData[3] = "";
                }
                break;
            case R.id.checkbox_5:
                if (checked) {
                    checkboxData[4] = BOX_VALUES[4];
                } else {
                    checkboxData[4] = "";
                }
                break;
            case R.id.checkbox_6:
                if (checked) {
                    checkboxData[5] = BOX_VALUES[5];
                } else {
                    checkboxData[5] = "";
                }
                break;
        }
    }



    /*
    * @method onUncheckedBoxes
    *
    * This method warns when all the checkboxes are unchecked
    * */
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    public void onUncheckedBoxes() {
//        if (!(mCheckBox1.isChecked() || mCheckBox2.isChecked() || mCheckBox3.isChecked()
//                || mCheckBox4.isChecked() || mCheckBox5.isChecked() || mCheckBox6.isChecked())) {
//            mTools.checkboxColorModifier(Color.RED, mCheckBoxes);
//            //The sanckbar is display here to explain the user what to do
//            mTools.snackBarMessage(R.string.box_unchecked, findViewById(R.id.activity_search_article));
//        }
//    }

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

        //if text input is empty OR all checkboxes are empty
        if (mSearchQuery.getText().toString().isEmpty()) {
            //display error message
            floatingHintLabel.setErrorEnabled(true);
            floatingHintLabel.setError(getResources().getString(R.string.query_error));
        } else
            floatingHintLabel.setErrorEnabled(false);
//        if (mTools.queryInputIsEmpty(mSearchQuery, floatingHintLabel,getResources().getString(R.string.query_error)))

        if (mTools.onUncheckedBoxes(mCheckBoxes))
            mTools.snackBarMessage(findViewById(R.id.activity_search_article), R.string.box_unchecked);

        if (!(mSearchQuery.getText().toString().isEmpty()) && !(mTools.onUncheckedBoxes(mCheckBoxes))) {
            configureAndShowMainFragment();
        }


    }

}