package aashna.com.aashna.H_WaterReminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import aashna.com.aashna.R;

/**
 * Created by Dell on 17-Feb-18.
 */

public class WaterReminderActivity  extends AppCompatActivity {
    SharedPreferences.Editor editor;
    SharedPreferences prefsRetriving;
    Button start, stop, close;
    EditText timing;
    int setTime;
    private int pendingID = 142804;
    String[] timeNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24"};


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_reminder_activity);

        editor = getSharedPreferences("com.innovatum.thirsty", MODE_PRIVATE).edit();
        prefsRetriving = getSharedPreferences("com.innovatum.thirsty", MODE_PRIVATE);
        boolean vibrateBool = prefsRetriving.getBoolean("vibration", false);
        boolean soundBool = prefsRetriving.getBoolean("sound", false);
        setTime = prefsRetriving.getInt("timing", 0);

        final Spinner time1 = (Spinner) findViewById(R.id.spinner1);
        time1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner time2 = (Spinner) findViewById(R.id.spinner2);
        time2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, timeNumbers);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        time1.setAdapter(aa);
        time2.setAdapter(aa);

        int time11 = prefsRetriving.getInt("time1", 7);
        int time12 = prefsRetriving.getInt("time2", 20);

        time1.setSelection(time11);
        time2.setSelection(time12);

        Switch vibration = (Switch) findViewById(R.id.vibration);
        vibration.setChecked(vibrateBool);
        vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    editor.putBoolean("vibration", true);
                    Toast.makeText(WaterReminderActivity.this, "Vibration turned ON", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("vibration", false);
                    Toast.makeText(WaterReminderActivity.this, "Vibration turned OFF", Toast.LENGTH_SHORT).show();
                }
                editor.commit();

            }
        });

        Switch sound = (Switch) findViewById(R.id.sound);
        sound.setChecked(soundBool);
        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("sound", true);
                    Toast.makeText(WaterReminderActivity.this, "Sound turned ON", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("sound", false);
                    Toast.makeText(WaterReminderActivity.this, "Sound turned OFF", Toast.LENGTH_SHORT).show();
                }
                editor.commit();

            }
        });

        timing = (EditText) findViewById(R.id.timing);
        if (setTime == 0) {
            timing.setText("");
        } else {
            timing.setText("" + setTime);
        }


        stop = (Button) findViewById(R.id.stop);
        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (timing.getText().toString().isEmpty()) {
                    timing.setError("Please enter how frequently.");
                    return;
                }
                try {
                    Log.e("***************", "Thirsty");
                    cancelPendingIntent();
                    editor.putInt("timing", Integer.parseInt(timing.getText().toString()));
                    editor.putInt("time1", time1.getSelectedItemPosition());
                    editor.putInt("time2", time2.getSelectedItemPosition());
                    editor.commit();
                    Intent myIntent = new Intent(WaterReminderActivity.this, AlarmReciever.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(WaterReminderActivity.this, pendingID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmMgr = (AlarmManager) WaterReminderActivity.this.getSystemService(Context.ALARM_SERVICE);
                    Log.e("**** TIME  :", " " + System.currentTimeMillis() + (60 * 1000 * setTime));
                    alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (60 * 1000 * setTime), pendingIntent);

                    Toast.makeText(WaterReminderActivity.this, "Started", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelPendingIntent();
                Toast.makeText(WaterReminderActivity.this, "Stopped", Toast.LENGTH_SHORT).show();
            }
        });

        close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                WaterReminderActivity.this.finish();
            }
        });

    }

    public void cancelPendingIntent() {
        Intent myIntent = new Intent(WaterReminderActivity.this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(WaterReminderActivity.this, pendingID, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    @Override
    public void onBackPressed(){
        WaterReminderActivity.this.finish();
    }

    @Override
    protected void onUserLeaveHint()
    {
        super.onUserLeaveHint();
        WaterReminderActivity.this.finish();
    }
}
