package com.thisischool.chool.OnlineDatabase;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.Classes.Constants;
import com.thisischool.chool.Classes.Controller;

import org.jetbrains.annotations.NotNull;

public class MyReferences {

    private static final String NICKNAME_DB = "Nicknames";
    private static final String USER_INFO_DB = "UserInfo";
    private static final String USER_DB = "Users";
    private static final String CLASS_CHAT_GROUP_DB = "Class_Chat_Groups";
    private static final String CLASS_IDS_DB = "Class_Ids";
    private static final String MY_FRIENDS_DB = "Friends";
    private static final String FRIEND_REQ_DB = "Friend_Requests";
    private static final String INBOX_DB = "Inbox";
    private static final String Likes = "Likes";

    @NotNull
    public static DatabaseReference nickNameRef() {
        return FirebaseDatabase.getInstance().getReference(NICKNAME_DB);
    }
    @NotNull
    public static DatabaseReference userInfoRef() {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(Controller.CurrentUser.getUID()).child(USER_INFO_DB);
    }
    @NotNull
    public static DatabaseReference classChatGroup(Context context) {
        return FirebaseDatabase.getInstance().getReference(CLASS_CHAT_GROUP_DB)
                .child(Controller.CurrentUser.getUserClassId(context));
    }

    @NotNull
    public static DatabaseReference otherUserInfoRef(String uid) {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(uid).child(USER_INFO_DB);
    }

    @NotNull
    public static DatabaseReference classIdsRef() {
        return FirebaseDatabase.getInstance().getReference(CLASS_IDS_DB);
    }

    @NotNull
    public static DatabaseReference privateChatRef(String chatId) {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(Controller.CurrentUser.getUID())
                .child(INBOX_DB).child(chatId);
    }

    @NotNull
    public static DatabaseReference friendsRef() {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(Controller.CurrentUser.getUID())
                .child(MY_FRIENDS_DB);
    }

    @NotNull
    public static DatabaseReference friendsRequestRef() {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(Controller.CurrentUser.getUID())
                .child(FRIEND_REQ_DB);
    }

    @NotNull
    public static DatabaseReference inboxRef() {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(Controller.CurrentUser.getUID())
                .child(INBOX_DB);
    }

    @NotNull
    public static DatabaseReference sendFriendRef(String userId) {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(userId).child(FRIEND_REQ_DB);
    }

    @NotNull
    public static DatabaseReference likedMessageRef(Context context, String messageId) {
        return FirebaseDatabase.getInstance().getReference()
                .child(Likes).child(Controller.CurrentUser.getUserClassId(context)).child(messageId);
    }


}
