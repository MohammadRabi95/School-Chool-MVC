package com.thisischool.chool.OnlineDatabase;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyNotificationService extends FirebaseMessagingService {
    private static final String TAG = "MyNotificationService";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
