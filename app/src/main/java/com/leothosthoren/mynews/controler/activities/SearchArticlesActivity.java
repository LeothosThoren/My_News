package com.leothosthoren.mynews.controler.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.SetDate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchArticlesActivity extends AppCompatActivity implements View.OnClickListener {
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
    String case1 = "", case2 = "", case3 = "", case4 = "", case5 = "", case6 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);
        ButterKnife.bind(this);
        this.configureToolbar();

        setDate();
        mSearchButton.setOnClickListener(this);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_1:
                boxSelection(checked, case1, "Arts");
                break;
            case R.id.checkbox_2:
                boxSelection(checked, case2, "Business");
                break;
            case R.id.checkbox_3:
                boxSelection(checked, case3, "Entrepreneurs");
                break;
            case R.id.checkbox_4:
                boxSelection(checked, case4, "Politics");
                break;
            case R.id.checkbox_5:
                boxSelection(checked, case5, "Sports");
                break;
            case R.id.checkbox_6:
                boxSelection(checked, case6, "Travel");
                break;
        }
    }

    public void boxSelection(boolean check, String key, String value) {
        if (check) {
            key = value;
            Toast.makeText(this, key + " is selected", Toast.LENGTH_SHORT).show();
        } else {
            key = "";
            Toast.makeText(this, value + " is deselected", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDate() {
        SetDate beginDate = new SetDate(mBeginDate, this);
        SetDate endDate = new SetDate(mEndDate, this);
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Test if the button ok", Toast.LENGTH_SHORT).show();
        //Todo :
        // handle query not empty, otherwise toast text alert
        // launch http request,
        // check if result not null otherwise toast text alert
        // open view list recycler view
    }
}
