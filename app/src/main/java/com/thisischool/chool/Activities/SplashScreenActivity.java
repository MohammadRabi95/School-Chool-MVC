package com.thisischool.chool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.thisischool.chool.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_activty);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(SplashScreenActivity.this,ClassChatGroupActivity.class));
        } else {
            startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
        }
        finish();
    }
}