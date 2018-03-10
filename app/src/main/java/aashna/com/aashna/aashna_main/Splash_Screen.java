package aashna.com.aashna.aashna_main;

        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.graphics.Color;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Handler;
        import android.provider.Settings;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.content.ContextCompat;
        import android.text.Spannable;
        import android.text.SpannableString;
        import android.text.style.ForegroundColorSpan;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import aashna.com.aashna.R;

public class Splash_Screen extends Activity {

    public static String str_login_test;

    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    static final int LOCATION_REQUEST = 1;

    ImageView image ;
    TextView textView;
    LinearLayout linearLayout;

    private Handler handler;
    private long startTime, currentTime, finishedTime = 0L;
    private int duration = 3000 / 2;// 1 character is equal to 1 second. if want to
    // reduce. can use as divide
    // by 2,4,8
    private int endTime = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        image = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.txt);
        linearLayout = (LinearLayout) findViewById(R.id.ly);
        textView.setText("AASHNA");// length of string is 19

        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        image.startAnimation(myFadeInAnimation);
        linearLayout.startAnimation(myFadeInAnimation);

        sh = getSharedPreferences("myprefe", 0);
        editor = sh.edit();

        // check here if user is login or not
        str_login_test = sh.getString("loginTest", null);


        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }


        if (!runtime_permissions())
            location_pemission();

        //nextactivity(1);

    }


    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Alert boz to show user what permissions has to be granted
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("App Permissions");
            alertDialogBuilder.setMessage(R.string.ALLinone);
            alertDialogBuilder.setCancelable(false);
            //If user allow ask permission
            alertDialogBuilder.setPositiveButton("Allow",
                    new DialogInterface.OnClickListener() {

                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                    android.Manifest.permission.INTERNET,
                                    android.Manifest.permission.ACCESS_WIFI_STATE,
                                    android.Manifest.permission.ACCESS_NETWORK_STATE,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.SEND_SMS,
                                    android.Manifest.permission.READ_CONTACTS,
                                    android.Manifest.permission.CALL_PHONE,
                                    android.Manifest.permission.WRITE_SETTINGS,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ID_MULTIPLE_PERMISSIONS);
                        }
                    });

            alertDialogBuilder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //If user deny then close app
                    finish();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


            return true;
        }
        return false;
    }

    public void location_pemission() {

        final String locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (locationProviders == null || locationProviders.equals("")) {
            //Alert dialog box to request location from user
            new AlertDialog.Builder(Splash_Screen.this)
                    .setTitle("Use Location?")
                    .setMessage("This app wants to enable your GPS for location.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                            nextactivity(1);
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();

        } else {
            nextactivity(1);
        }
    }


    public void nextactivity(final int requestCode) {

        if (requestCode == LOCATION_REQUEST) {
            handler = new Handler();
            startTime = Long.valueOf(System.currentTimeMillis());
            currentTime = startTime;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    currentTime = Long.valueOf(System.currentTimeMillis());
                    finishedTime = Long.valueOf(currentTime)
                            - Long.valueOf(startTime);

                    if (finishedTime >= duration + 30) {
                        //
                    } else {
                        endTime = (int) (finishedTime / 250);// divide this by
                        // 1000,500,250,125
                        Spannable spannableString = new SpannableString(textView
                                .getText());
                        spannableString.setSpan(new ForegroundColorSpan(
                                        Color.parseColor("#b71c1c")), 0, endTime,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        textView.setText(spannableString);
                        handler.postDelayed(this, 10);
                    }
                }
            }, 10);


//            flipit(image);
            /****** Create Thread that will sleep for 5 seconds *************/
            Thread background = new Thread() {
                public void run() {

                    try {
                        // Thread will sleep for 5 seconds
                        sleep(4 * 1000);

                        if (str_login_test != null
                                && !str_login_test.toString().trim().equals("")) {
                            Intent send = new Intent(getApplicationContext(),
                                    MainActivity.class);
                            startActivity(send);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else
                        {
                            Intent send = new Intent(getApplicationContext(),
                                    Login_Activity.class);
                            startActivity(send);

                        }


                        //Remove activity
                        finish();

                    } catch (Exception e) {

                    }
                }
            };

            // start thread
            background.start();


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                location_pemission();


            } else

            {
                runtime_permissions();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

}








/*{

    public static String str_login_test;

    public static SharedPreferences sh;
    public static SharedPreferences.Editor editor;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    static final int LOCATION_REQUEST = 1;

    ImageView image ;
    TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sh = getSharedPreferences("myprefe", 0);
        editor = sh.edit();

        // check here if user is login or not
        str_login_test = sh.getString("loginTest", null);

        image = (ImageView) findViewById(R.id.image);
        textView = (TextView) findViewById(R.id.txt);


        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

        textView.setText("Aashna");// length of string is 6
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        image.startAnimation(myFadeInAnimation);


        if (!runtime_permissions())
            location_pemission();

        //nextactivity(1);

    }


    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Alert boz to show user what permissions has to be granted
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("App Permissions");
            alertDialogBuilder.setMessage(R.string.ALLinone);
            alertDialogBuilder.setCancelable(false);
            //If user allow ask permission
            alertDialogBuilder.setPositiveButton("Allow",
                    new DialogInterface.OnClickListener() {

                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                                    android.Manifest.permission.INTERNET,
                                    android.Manifest.permission.ACCESS_WIFI_STATE,
                                    android.Manifest.permission.ACCESS_NETWORK_STATE,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.SEND_SMS,
                                    android.Manifest.permission.READ_CONTACTS,
                                    android.Manifest.permission.CALL_PHONE,
                                    android.Manifest.permission.WRITE_SETTINGS,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ID_MULTIPLE_PERMISSIONS);
                        }
                    });

            alertDialogBuilder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //If user deny then close app
                    finish();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


            return true;
        }
        return false;
    }

    public void location_pemission() {

        final String locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (locationProviders == null || locationProviders.equals("")) {
            //Alert dialog box to request location from user
            new AlertDialog.Builder(Splash_Screen.this)
                    .setTitle("Use Location?")
                    .setMessage("This app wants to enable your GPS for location.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                            nextactivity(1);
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();

        } else {
            nextactivity(1);
        }
    }


    public void nextactivity(final int requestCode) {

//            flipit(image);
        *//****** Create Thread that will sleep for 5 seconds *************//*
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(5 * 1000);

                    if (str_login_test != null && !str_login_test.toString().trim().equals("")) {
                        Intent send = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(send);
                    } else
                    {
                        Intent send = new Intent(getApplicationContext(), Login_Activity.class);
                        startActivity(send);

                    }
                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                location_pemission();


            } else

            {
                runtime_permissions();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

}*/
