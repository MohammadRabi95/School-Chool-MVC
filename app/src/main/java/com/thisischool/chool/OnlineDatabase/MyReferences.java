package com.thisischool.chool.OnlineDatabase;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.thisischool.chool.Classes.AppHelper;
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
    private static final String ALL_INBOX = "all_Inbox";
    private static final String PROFILE_IMAGES_ST = "profile_images";
    private static final String NOTIFICATION_DB = "notifications";
    private static final String MESSAGES = "messages";
    private static final String NOTES = "notes";
    private static final String QUESTIONS = "questions";
    private static final String ANSWERS = "answers";
    private static final String REPLIES = "replies";



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
    public static DatabaseReference classGroupNotes(Context context) {
        return FirebaseDatabase.getInstance().getReference(CLASS_CHAT_GROUP_DB)
                .child(Controller.CurrentUser.getUserClassId(context)).child(NOTES);
    }

    @NotNull
    public static DatabaseReference classGroupAnswers(Context context) {
        return FirebaseDatabase.getInstance().getReference(CLASS_CHAT_GROUP_DB)
                .child(Controller.CurrentUser.getUserClassId(context)).child(ANSWERS);
    }

    @NotNull
    public static DatabaseReference classGroupReplies(Context context) {
        return FirebaseDatabase.getInstance().getReference(CLASS_CHAT_GROUP_DB)
                .child(Controller.CurrentUser.getUserClassId(context)).child(REPLIES);
    }

    @NotNull
    public static DatabaseReference classGroupQuestions(Context context) {
        return FirebaseDatabase.getInstance().getReference(CLASS_CHAT_GROUP_DB)
                .child(Controller.CurrentUser.getUserClassId(context)).child(QUESTIONS);
    }

    @NotNull
    public static DatabaseReference classGroupMessages(Context context) {
        return FirebaseDatabase.getInstance().getReference(CLASS_CHAT_GROUP_DB)
                .child(Controller.CurrentUser.getUserClassId(context)).child(MESSAGES);
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
    public static DatabaseReference privateChatRef() {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(Controller.CurrentUser.getUID())
                .child(INBOX_DB);
    }

    @NotNull
    public static DatabaseReference allPrivateChat() {
        return FirebaseDatabase.getInstance().getReference(ALL_INBOX);
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
    public static DatabaseReference sendFriendRef(String userId) {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(userId).child(FRIEND_REQ_DB);
    }

    @NotNull
    public static DatabaseReference likedMessageRef(Context context, String messageId) {
        return FirebaseDatabase.getInstance().getReference()
                .child(Likes).child(Controller.CurrentUser.getUserClassId(context)).child(messageId);
    }

    @NotNull
    public static DatabaseReference notificationsRef() {
        return FirebaseDatabase.getInstance().getReference(USER_DB)
                .child(Controller.CurrentUser.getUID()).child(NOTIFICATION_DB);
    }

    @NotNull
    public static StorageReference profileImageStorage(Context context, Uri path){
        return FirebaseStorage.getInstance().getReference(PROFILE_IMAGES_ST)
                .child(System.currentTimeMillis() + "" + "." + AppHelper.getFileExtension(context,path));

    }


}
