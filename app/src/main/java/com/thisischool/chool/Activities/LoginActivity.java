package com.thisischool.chool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private EditText otpCodeEdit, phoneEdit;
    private Button sendBtn, verifyBtn, resendBtn;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        //FirebaseAuth.getInstance().signOut();

        otpCodeEdit = findViewById(R.id.otp_frg_edit);
        phoneEdit = findViewById(R.id.phone_frg_edit);
        sendBtn = findViewById(R.id.next);
        verifyBtn = findViewById(R.id.verify);
        resendBtn = findViewById(R.id.resend);

        sendBtn.setOnClickListener(this);
        resendBtn.setOnClickListener(this);
        verifyBtn.setOnClickListener(this);

        myDialog = AppHelper.getLoadingDialog(this);

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.d(TAG, "onVerificationCompleted:" + credential);

                mVerificationInProgress = false;

                updateUI(STATE_VERIFY_SUCCESS, credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w(TAG, "onVerificationFailed", e);

                mVerificationInProgress = false;


                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    AppHelper.showToast(LoginActivity.this, e.getMessage());

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }

                updateUI(STATE_VERIFY_FAILED);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                Log.d(TAG, "onCodeSent:" + verificationId);

                mVerificationId = verificationId;
                mResendToken = token;

                updateUI(STATE_CODE_SENT);

            }
        };

    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(phoneEdit.getText().toString().trim());
        }

    }


    private void startPhoneNumberVerification(String phoneNumber) {

        if (isMobileNumberCorrect(phoneNumber)) {
            phoneNumber = countryCode(phoneNumber);
        } else {
            phoneEdit.setError("Invalid Phone Number");
            return;
        }
        myDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);


        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        myDialog.show();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithPhoneAuthCredential(credential);
    }


    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        if (isMobileNumberCorrect(phoneNumber)) {
            phoneNumber = countryCode(phoneNumber);
        } else {
            phoneEdit.setError("Invalid Phone Number");
            return;
        }
        myDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                token);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        Log.d(TAG, "signInWithCredential:success");

                        FirebaseUser user = task.getResult().getUser();

                        updateUI(STATE_SIGNIN_SUCCESS, user);

                    } else {

                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            otpCodeEdit.setError("Invalid code.");

                        }

                        updateUI(STATE_SIGNIN_FAILED);

                    }
                });
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                break;
            case STATE_CODE_SENT:
                myDialog.dismiss();
                //textInputLayout.setVisibility(View.GONE);
                otpCodeEdit.setVisibility(View.VISIBLE);
                verifyBtn.setVisibility(View.VISIBLE);
                phoneEdit.setVisibility(View.GONE);
                sendBtn.setVisibility(View.GONE);
                resendBtn.setVisibility(View.GONE);
                break;
            case STATE_VERIFY_FAILED:
                myDialog.dismiss();
                //textInputLayout.setVisibility(View.VISIBLE);
                phoneEdit.setVisibility(View.VISIBLE);
                sendBtn.setVisibility(View.GONE);
                resendBtn.setVisibility(View.VISIBLE);
                otpCodeEdit.setVisibility(View.GONE);
                verifyBtn.setVisibility(View.GONE);
                break;
            case STATE_VERIFY_SUCCESS:
                myDialog.dismiss();
                //textInputLayout.setVisibility(View.GONE);
                phoneEdit.setVisibility(View.GONE);
                sendBtn.setVisibility(View.GONE);
                otpCodeEdit.setVisibility(View.VISIBLE);
                resendBtn.setVisibility(View.GONE);
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        otpCodeEdit.setText(cred.getSmsCode());
                    } else {

                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                //  textInputLayout.setVisibility(View.VISIBLE);
                phoneEdit.setVisibility(View.VISIBLE);
                phoneEdit.setText("");
                otpCodeEdit.setText("");
                sendBtn.setVisibility(View.GONE);
                resendBtn.setVisibility(View.VISIBLE);
                otpCodeEdit.setVisibility(View.GONE);
                verifyBtn.setVisibility(View.GONE);
                myDialog.dismiss();
                AppHelper.showToast(this, "Sign in Failed");
                break;
            case STATE_SIGNIN_SUCCESS:
                myDialog.dismiss();
                check();
                break;
        }

    }

    private boolean validatePhoneNumber() {
        String phoneNumber = phoneEdit.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneEdit.setError("Invalid Phone Number.");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (!validatePhoneNumber()) {
                    return;
                }
                startPhoneNumberVerification(phoneEdit.getText().toString().trim());
                break;

            case R.id.verify:
                String code = otpCodeEdit.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    otpCodeEdit.setError("Cannot be empty.");
                    return;
                }
                verifyPhoneNumberWithCode(mVerificationId, code);
                break;

            case R.id.resend:
                resendVerificationCode(phoneEdit.getText().toString().trim(), mResendToken);
                break;
        }
    }

    private void check() {

        MyReferences.userInfoRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    AppHelper.setData(LoginActivity.this);
                    startActivity(new Intent(LoginActivity.this, ClassChatGroupActivity.class));
                    finish();
                    Log.d(TAG, "onDataChange: IF");
                } else {
                    Log.d(TAG, "onDataChange: ELSE");
                    startActivity(new Intent(LoginActivity.this, ChooseNicknameActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private boolean isMobileNumberCorrect(@NotNull String num) {
        return num.length() == 10;
    }

    @NotNull
    @Contract(pure = true)
    private String countryCode(String num) {
        return "+92" + num;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDialog.dismiss();
    }


}