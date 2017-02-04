package com.example.prateekjoshi.agri_app;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by Prateek Joshi on 1/30/2017.
 */

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        registerToken(token);
    }

    private void registerToken(String token) {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);


    }
}
