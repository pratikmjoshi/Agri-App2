package com.example.prateekjoshi.agri_app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Prateek Joshi on 12/26/2016.
 */

public class FirebaseAlertService extends FirebaseMessagingService {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    final int DEFAULT_ID = 42;
    List<String> alerts;
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        sharedPreferencesEditor = sharedPreferences.edit();

        Map<String, String> data = remoteMessage.getData();

        if (data != null) {
            //In case data is wrongly formatted
            //we use try catch block

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            int notificationId = DEFAULT_ID;
            String type = data.get("type");
            String id = data.get("id");
            String smallTitle = data.get("smallTitle");
            String smallSubTitle = data.get("smallSubTitle");
            String contentInfo = data.get("contentInfo");
            String link = data.get("link");


            //General things to be added for any kind of notification
            if (smallTitle != null) builder.setContentTitle(smallTitle);
            if (smallSubTitle != null) builder.setContentText(smallSubTitle);
            if (id != null) notificationId = Integer.parseInt(id);
            builder.setContentIntent(addPendingIntent(notificationId));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setCategory(Notification.CATEGORY_SOCIAL);
            }

            builder.setSmallIcon(R.drawable.ic_nav_alert);
            builder.setAutoCancel(true);


            if (contentInfo != null) builder.setContentInfo(contentInfo);
            else builder.setContentInfo("Agri-App");

            NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
            mNotificationManager.notify(notificationId, builder.build());

            // Save the alert
            List<String> alerts = load();
            String alert = contentInfo + "-" + link;
            alerts.add(alert);
            store(alerts);
        }
    }

    private PendingIntent addPendingIntent(int id) {
        Intent intent = new Intent(this, MenuNavActivity.class);
        intent.putExtra("openAlerts", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);

        return PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public List<String> load() {
        String csvList = sharedPreferences.getString("myList", "");
        String[] items = csvList.split(",");
        List<String> list = new ArrayList<String>();
        int num = 0;
        if (csvList.isEmpty()) {
            num = 1;
        }
        list.addAll(Arrays.asList(items).subList(num, items.length));
        return list;
    }

    public void store(List<String> alerts) {
        StringBuilder csvList = new StringBuilder();
        for (String s : alerts) {
            csvList.append(s);
            csvList.append(",");
        }

        sharedPreferencesEditor.putString("myList", csvList.toString());
        sharedPreferencesEditor.commit();
    }
}
