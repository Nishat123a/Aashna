package aashna.com.aashna.S_SelfSafety;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import aashna.com.aashna.R;

public class Agreement extends AppCompatActivity {

    ProgressBar Progressbar;
    private WebView wView;
    AlertDialog alertDialog;
    NetworkChangeReceiver br;
    String webViewUrl = "http://www.innovatumsolution.com/waitc/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        br = new NetworkChangeReceiver();
        setContentView(R.layout.agreement_screen);
        wView = (WebView) this.findViewById(R.id.webView);
        Progressbar = (ProgressBar) findViewById(R.id.progressBar);

    }
// configuring the WebView
    public void startWebView() {
        wView.setWebChromeClient(new WebChromeClient() {
// Callback to notify progress changes
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Progressbar.setProgress(newProgress);
                if (newProgress == 100) {
                    Progressbar.setVisibility(View.GONE);
                } else {
                    Progressbar.setVisibility(View.VISIBLE);
                }
            }
        });

        wView.getSettings().setJavaScriptEnabled(true);
        wView.setWebViewClient(new MyWebViewClient());
        wView.loadUrl(webViewUrl);
    }
//key down event
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wView.canGoBack()) {
            wView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_close,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.close_activity:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
//receiving a broadcast while you are running
    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(br,intentFilter);
    }
//unregistering
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }
    //Dialog box to display internet connectivity
    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Agreement.this);
        alertDialogBuilder.setTitle("No Internet Connection.");
        alertDialogBuilder
                .setMessage(
                        "Go to Settings to enable Internet Connecivity.")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivityForResult(
                                        new Intent(android.provider.Settings.ACTION_SETTINGS),0);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                finish();

                            }
                        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
// detect whether a device has a wifi or mobile connection
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
// check for the internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
//Give the host application a chance to take over the control when a new url is about to be loaded in the current WebView.
    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            wView.loadUrl(url);
            return true;
        }
    }
    // broadcast receiver for checking internet connection.
    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {

            if (haveNetworkConnection()) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                startWebView();
            } else {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                dialogBox();
            }
        }
    }
}