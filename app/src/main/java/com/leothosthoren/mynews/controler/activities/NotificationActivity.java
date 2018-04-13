package com.leothosthoren.mynews.controler.activities;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.leothosthoren.mynews.R;
import com.leothosthoren.mynews.model.HelperTools;
import com.leothosthoren.mynews.model.utility.AlarmReceiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    public static final String SEARCH_ARTICLE_NOTIFICATION_VALUES = "SEARCH_ARTICLE_NOTIFICATION_VALUES";
    public final String[] BOX_VALUES = {"Culture", "Environement", "Foreign", "Politics", "Sports", "Technology"};
    private final boolean TEST_MODE = true;
    //=============================================//
    public String[] checkboxData = new String[6];
    @BindView(R.id.query_text_input_layout)
    public TextInputLayout floatingHintLabel;
    public HelperTools mTools = new HelperTools();
    @BindView(R.id.search_query_term)
    EditText mSearchQuery;
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
    @BindView(R.id.switch_button)
    Switch mSwitch;
    @BindView(R.id.button_test)
    Button mButton;
    private CheckBox[] mCheckBoxes;
    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        this.mCheckBoxes = new CheckBox[]{mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6};

        this.configureToolbar();
        this.mTools.displayErrorMessage(floatingHintLabel);
        this.switchButton();
        this.configureAlarmManager(this);


        if (TEST_MODE) {
            mButton.setVisibility(View.VISIBLE);
            //Test send notification
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendTestNotification();
                }
            });
        }
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

    /*
    * @method configureAlarmManager
    * @param context
    *
    * Configure the alarm manager to perform an asynch task automatically
    *
    * */
    private void configureAlarmManager(Context context) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(NotificationActivity.this,
                0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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

    /**/
    private void switchButton() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Every time user click on button input query is checked
                    if (mTools.queryInputIsEmpty(mSearchQuery, floatingHintLabel, getResources().getString(R.string.query_error)))
                        mSwitch.setChecked(!isChecked);
                    //At least one check box must be checked
                    if (mTools.onUncheckedBoxes(mCheckBoxes)) {
                        mSwitch.setChecked(!isChecked);
                        mTools.snackBarMessage(findViewById(R.id.activity_notification), R.string.box_unchecked);
                    }
                    //if text input is empty OR all checkboxes are empty no access to the activity
                    if (!(mSearchQuery.getText().toString().isEmpty()) && !(mTools.onUncheckedBoxes(mCheckBoxes))) {
                        mSwitch.setChecked(isChecked);
                        //Launch alarm manager
                        if (TEST_MODE) {
                            startTestAlarm();
                            passingData();
                        } else {
                            passingData();
                            startAlarm();
                        }
                    }

                } else {
                    //Alarm Manager is disable
                    stopAlarm();
                }
            }
        });
    }

    // ---------------------------------------------
    // SCHEDULE TASK (AlarmManager & Notifications)
    // ---------------------------------------------

    // Start Alarm for test
    private void startTestAlarm() {
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 90000;
        assert mAlarmManager != null;
        mAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, interval,
                interval, mPendingIntent);
        Toast.makeText(this, "Alarm test set !", Toast.LENGTH_SHORT).show();
    }

    //Start Alarm for real
    private void startAlarm() {
        // The schedule is set to be launch at midnight
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, 1);

        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, mPendingIntent);
        Toast.makeText(this, "Alarm set !", Toast.LENGTH_SHORT).show();
    }

    // Stop Alarm for test and prod
    private void stopAlarm() {
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert mAlarmManager != null;
        mAlarmManager.cancel(mPendingIntent);
        Toast.makeText(this, "Alarm Canceled !", Toast.LENGTH_SHORT).show();
    }

    /*
    * @method sendNotification
    *
    * This is a sample code to test the notification alert on click
    * */
    public void sendTestNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        //Get an instance of NotificationManager//

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_logo)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.test_text_notification))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        assert mNotificationManager != null;
        mNotificationManager.notify(001, mBuilder.build());
    }

    /*
    *
    *
    * */
    public void passingData() {
        String[] value = {mSearchQuery.getText().toString(), mTools.getNewDesk(checkboxData)};
        Log.d("Check varaible", value[0] + " " + value[1]);
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra(SEARCH_ARTICLE_NOTIFICATION_VALUES, value);

    }
}
