package com.thisischool.chool.OnlineDatabase;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.ClassChatGroupMessage;

import static com.thisischool.chool.Classes.Constants.NO_IMAGE;

public class SendMessage {

    private static final String TAG = "SendMessage";

    public static void sendMessageToGroupChat(Context context, String msg, String imageUrl, EditText editText) {

        if ("".equals(imageUrl)){
            imageUrl = NO_IMAGE;
        }
        DatabaseReference msgRef = MyReferences.classChatGroup(context);
        String messageId = msgRef.push().getKey();
        ClassChatGroupMessage message = new ClassChatGroupMessage(msg,
                Controller.CurrentUser.getUserNickname(context),
                Controller.CurrentUser.getUID(), AppHelper.getTimeStamp(),
                messageId, imageUrl, 0);
        assert messageId != null;
        msgRef.child(messageId).setValue(message).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                SendNotification.classMessageNotification(context,
                        Controller.CurrentUser.getUserNickname(context),
                        msg, Controller.CurrentUser.getUserClassId(context));
                editText.setText("");
                Log.d(TAG, "onComplete:");
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "onFailure: ", e.getCause());
            AppHelper.showToast(context, e.getMessage());
        });
    }
}
