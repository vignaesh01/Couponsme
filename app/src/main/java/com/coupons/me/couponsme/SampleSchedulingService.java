package com.coupons.me.couponsme;

/**
 * Created by user on 7/17/2016.
 */
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This {@code IntentService} does the app's actual work.
 * {@code SampleAlarmReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class SampleSchedulingService extends IntentService {
    public SampleSchedulingService() {
        super("SchedulingService");
    }

    public static final String TAG = "Scheduling Demo";
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;
    // The string the app searches for in the Google home page content. If the app finds
    // the string, it indicates the presence of a doodle.
    public static final String SEARCH_STRING = "doodle";
    // The Google home page URL from which the app fetches content.
    // You can find a list of other Google domains with possible doodles here:
    // http://en.wikipedia.org/wiki/List_of_Google_domains
    public static final String URL = "http://www.google.com";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Override
    protected void onHandleIntent(Intent intent) {
        // BEGIN_INCLUDE(service_onhandle)
        // The URL from which to fetch content.
        String urlString = URL;
        WebAppInterface obj=new WebAppInterface(this);
        String result=obj.expiresIn7DaysCoupons();
        try {
            JSONObject record = new JSONObject(result);
            JSONArray records=record.getJSONArray("records");
           // Log.i(TAG, "before notification");
            if(records!=null && records.length()>0){

                String msg="";
                for(int i=0;i<records.length();i++){
                    JSONObject rec=  records.getJSONObject(i);
                    if(msg==""){
                        msg+= rec.getString("storeName");
                    }else{
                        msg+= ", "+rec.getString("storeName");
                    }
                }
        msg+= " coupon(s) expiring in 7 days";
                sendNotification(msg);
               // Log.i(TAG, "inside notification");
            }

        }catch(JSONException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }



        // Try to connect to the Google homepage and download content.
       // try {
       //     result = loadFromNetwork(urlString);
       // } catch (IOException e) {
       //     Log.i(TAG, getString(R.string.connection_error));
       // }

        // If the app finds the string "doodle" in the Google home page content, it
        // indicates the presence of a doodle. Post a "Doodle Alert" notification.
      //  if (result.indexOf(SEARCH_STRING) != -1) {
         //   sendNotification(getString(R.string.doodle_found));
       //     Log.i(TAG, "Found doodle!!");
       // } else {
           // sendNotification(getString(R.string.no_doodle));
        //    Log.i(TAG, "No doodle found. :-(");
       // }
        // Release the wake lock provided by the BroadcastReceiver.
        SampleAlarmReceiver.completeWakefulIntent(intent);
        // END_INCLUDE(service_onhandle)
    }

    // Post a notification indicating whether a doodle was found.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

      NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Coupons expiring soon!!")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


}

