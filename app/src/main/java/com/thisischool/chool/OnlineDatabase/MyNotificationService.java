package com.thisischool.chool.OnlineDatabase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.thisischool.chool.Activities.AddWorkBookActivity;
import com.thisischool.chool.Activities.ClassChatGroupActivity;
import com.thisischool.chool.Activities.FriendRequestActivity;
import com.thisischool.chool.Activities.InboxActivity;
import com.thisischool.chool.Activities.WorkBookActivity;
import com.thisischool.chool.Classes.Constants;
import com.thisischool.chool.R;

import org.json.JSONObject;

import static com.thisischool.chool.Classes.Constants.CLICK_ACTION;
import static com.thisischool.chool.Classes.Constants.TO_CLASS_CHAT_GROUP;
import static com.thisischool.chool.Classes.Constants.TO_FRIEND_REQUEST;
import static com.thisischool.chool.Classes.Constants.TO_INBOX;

public class MyNotificationService extends FirebaseMessagingService {
    private static final String TAG = "MyNotificationService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "onMessageReceived: ");
        int click_action = 0;
        if (remoteMessage.getData().size() > 0 ){

            try {
                JSONObject object = new JSONObject(remoteMessage.getData());
                click_action = object.getInt(CLICK_ACTION);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            showNotification(title, body, click_action);
        }}

    private void showNotification(String title, String message, int click_action){

        Intent intent;

        switch (click_action) {
            case TO_CLASS_CHAT_GROUP:
                intent = new Intent(this, ClassChatGroupActivity.class);
                break;

            case TO_FRIEND_REQUEST:
                intent = new Intent(this, FriendRequestActivity.class);
                break;

            case TO_INBOX:
                intent = new Intent(this, InboxActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + click_action);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent
                , PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.add_photo)
                .setAutoCancel(true)
                .setContentText(message)
                .setContentIntent(pendingIntent);

        builder.setVibrate(new long[] {2000});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MyNotificati", title,
                    importance);
            channel.setDescription(message);
            channel.setVibrationPattern(new long[] {2000});
            channel.enableVibration(true);
            channel.enableLights(true);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(0, builder.build());


    }
}
