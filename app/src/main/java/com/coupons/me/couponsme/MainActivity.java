package com.coupons.me.couponsme;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebSettings;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    SampleAlarmReceiver alarm = new SampleAlarmReceiver();
    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7830155450711523~5366062943");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

            myWebView = (WebView) findViewById(R.id.webview);
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            int isAlarmSet = sharedPref.getInt("isAlarmSet", 0);

            if (isAlarmSet == 0) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("isAlarmSet", 1);
                editor.commit();

                alarm.setAlarm(this);
            }
            NotificationManager mNotificationManager = (NotificationManager)
                    this.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(1);

            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            myWebView.setVerticalScrollBarEnabled(true);
            myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
            myWebView.loadUrl("file:///android_asset/CouponsMe.html");




    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
