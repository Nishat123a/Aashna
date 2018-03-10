package aashna.com.aashna.H_WaterReminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import aashna.com.aashna.R;

import java.util.Calendar;

public class ShowHomeScreen extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        super.onCreate(savedInstanceState);

        SharedPreferences prefsRetriving = getSharedPreferences("com.innovatum.thirsty", MODE_PRIVATE);
        boolean vibrateBool = prefsRetriving.getBoolean("vibration", false);
        boolean soundBool = prefsRetriving.getBoolean("sound", false);

        int setTimeCal = prefsRetriving.getInt("timing", 0);
        Log.e("***********", " " + setTimeCal);


        Intent myIntent = new Intent(ShowHomeScreen.this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ShowHomeScreen.this, 142804, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmMgr = (AlarmManager) ShowHomeScreen.this.getSystemService(Context.ALARM_SERVICE);
//        Calendar timeOff9 = Calendar.getInstance();
//        timeOff9.add(Calendar.MINUTE, setTimeCal);
        Log.e("**** TIME  :", " " + System.currentTimeMillis() + (60 * 1000 * setTimeCal));
        alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (60 * 1000 * setTimeCal), pendingIntent);

        if (vibrateBool) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(800);
        }

        if (soundBool) {
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Calendar c = Calendar.getInstance();
        int Hr24 = c.get(Calendar.HOUR_OF_DAY);
        Log.e("simpleDate", "************* " + Hr24);
        int time11 = prefsRetriving.getInt("time1", 7);
        int time12 = prefsRetriving.getInt("time2", 20);
        if ((Hr24 < time11) && (Hr24 > time12)) {
            return;
        }

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.thirstimage);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle("Feeling thirsty?? It's already too late.");
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });
        dialog.show();

        ImageView imageView = (ImageView) dialog.findViewById(R.id.iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView settings = (ImageView) dialog.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowHomeScreen.this, WaterReminderActivity.class));
                finish();
            }
        });

        ImageView close = (ImageView) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(android.os.Build.VERSION.SDK_INT >= 21)
                {
                    finishAndRemoveTask();
                }
                else
                {
                    finish();
                }
            }
        });

    }
}
