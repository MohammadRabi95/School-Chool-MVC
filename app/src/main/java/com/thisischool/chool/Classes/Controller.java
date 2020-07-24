package com.thisischool.chool.Classes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Controller {

    public static class CurrentUser {

        private static String USER_PREFERENCES_KEY = "User_Data";
        private static String CLASS_ID = "CLASS_ID";
        private static String NICKNAME = "NICKNAME";

        public static void setUserClassId(@NotNull Context context, String classId) {
            SharedPreferences preferences = context.
                    getSharedPreferences(USER_PREFERENCES_KEY,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(CLASS_ID,classId).apply();
        }

        public static String getUserClassId(@NotNull Context context) {
            SharedPreferences preferences = context.
                    getSharedPreferences(USER_PREFERENCES_KEY,Context.MODE_PRIVATE);
            return preferences.getString(CLASS_ID,"");
        }


        public static String getUserPhone() {
            return FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }

        public static void setUserNickname(@NotNull Context context, String Nickname) {
            SharedPreferences preferences = context.
                    getSharedPreferences(USER_PREFERENCES_KEY,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(NICKNAME,Nickname).apply();
        }

        public static String getUserNickname(@NotNull Context context) {
            SharedPreferences preferences = context.
                    getSharedPreferences(USER_PREFERENCES_KEY,Context.MODE_PRIVATE);
            return preferences.getString(NICKNAME,"");
        }

        @NotNull
        @Contract(pure = true)
        public static String getUID() {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
    }
}
