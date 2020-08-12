package com.thisischool.chool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.Classes.Constants;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.Info;
import com.thisischool.chool.Models.NickName;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import static com.thisischool.chool.Classes.Constants.DEFAULT_DP;
import static com.thisischool.chool.Classes.Constants.DEFAULT_STATUS;

public class ChooseNicknameActivity extends AppCompatActivity {

    private static final String TAG = "ChooseNicknameActivity";
    private EditText nicknameEdit, classIdEdit;
    private String classId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_nickname);

        checkClassStrengthAndGetClassId();

        nicknameEdit = findViewById(R.id.chooseNickname_frg_edit);
        classIdEdit = findViewById(R.id.classid_frg_edit);

        findViewById(R.id.ok).setOnClickListener(view1 -> {
            if (nicknameEdit.getText().toString().isEmpty()) {
                nicknameEdit.setError("Nickname Required to Proceed");
                nicknameEdit.requestFocus();
                return;
            }
            checkUserName(nicknameEdit.getText().toString());
        });

    }

    private void checkUserName(String nick) {

        MyReferences.nickNameRef().orderByChild("nickname").equalTo(nick)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            nicknameEdit.setError("Nickname Already Exist, Kindly Choose Unique One!");
                            nicknameEdit.requestFocus();
                        } else {
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();
                            String phone = Objects.requireNonNull(FirebaseAuth.getInstance()
                                    .getCurrentUser()).getPhoneNumber();
                            if (!"".equals(classIdEdit.getText().toString())) {
                                if (classIdEdit.getText().toString().length() > 7) {
                                    classId = classIdEdit.getText().toString().trim();
                                } else {
                                    classIdEdit.setError("Invalid Code");
                                    classIdEdit.requestFocus();
                                    return;
                                }
                            }
                            User user = new User(phone, nick, classId, deviceToken,
                                    DEFAULT_DP, DEFAULT_STATUS, 0,
                                    Controller.CurrentUser.getUID());
                            addToClassStrength(classId);
                            MyReferences.userInfoRef().setValue(user)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            DatabaseReference reference = MyReferences.nickNameRef();
                                            String id = reference.push().getKey();
                                            NickName nickName1 = new NickName(nick, "extra", id);
                                            reference.child(id).setValue(nickName1)
                                                    .addOnCompleteListener(task1 -> {
                                                        AppHelper.setData(ChooseNicknameActivity.this);
                                                        startActivity(new Intent(ChooseNicknameActivity.this,
                                                                ClassChatGroupActivity.class));
                                                        finish();
                                                    });
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: ", error.toException());
                    }
                });
    }

    private void checkClassStrengthAndGetClassId() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Class_Chat_Groups").child("strength");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String id = dataSnapshot.getKey();
                        if (!classId.isEmpty()) {
                            return;
                        }
                        assert id != null;
                        reference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    long count = snapshot.getChildrenCount();
                                    if (count < 101) {
                                        classId = snapshot.getKey();
                                    } else {
                                        classId = reference.push().getKey();
                                        reference.child(classId).child("test").setValue("inner if");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                } else {
                    classId = reference.push().getKey();
                    reference.child(classId).child("test").setValue("outer if");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                AppHelper.showToast(ChooseNicknameActivity.this,error.getMessage());
            }
        });
    }

    private void addToClassStrength(String classId) {
        Info info = new Info();
        info.setId(Controller.CurrentUser.getUID());
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Class_Chat_Groups")
                .child(classId).child("strength");
        String id = reference.push().getKey();
        reference.child(id)
                .setValue(info);
    }
}