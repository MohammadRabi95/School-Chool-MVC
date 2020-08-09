package com.thisischool.chool.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.thisischool.chool.Adapters.ClassChatGroupAdapter;
import com.thisischool.chool.Adapters.NotificationsAdapter;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.Notification;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.thisischool.chool.Classes.AppHelper.inviteFriend;
import static com.thisischool.chool.OnlineDatabase.MyReferences.notificationsRef;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyProfileActivity";
    private CircleImageView profileImg, walletBtn, exitBtn, msgBtn,
            inviteBtn, workBookBtn, groupChatBtn;
    private ImageView lessonBtn, menuBtn, updateStatusBtn;
    private EditText searchEdit, status;
    private TextView nickName, likes, friends;
    private boolean isMenuOpened = false;
    private LinearLayout menu;
    private User user;
    private RecyclerView recyclerView;
    private List<Notification> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        profileImg = findViewById(R.id.profle_img_myprofle);
        lessonBtn = findViewById(R.id.lesson_mp);
        walletBtn = findViewById(R.id.wallet_menu_mp);
        exitBtn = findViewById(R.id.exit_menu_mp);
        msgBtn = findViewById(R.id.msg_menu_mp);
        inviteBtn = findViewById(R.id.invite_menu_mp);
        workBookBtn = findViewById(R.id.notes_menu_mp);
        groupChatBtn = findViewById(R.id.schoolChat_menu_mp);
        menuBtn = findViewById(R.id.menu_mp);
        updateStatusBtn = findViewById(R.id.update_status_mp);
        recyclerView = findViewById(R.id.notification_recyclerview);

        menu = findViewById(R.id.opened_menu_mp);

        searchEdit = findViewById(R.id.searchEdit_mp);
        status = findViewById(R.id.status_myprofile);

        nickName = findViewById(R.id.nickname_myprofile);
        likes = findViewById(R.id.total_likes_myprofile);
        friends = findViewById(R.id.total_friends_myprofile);

        updateStatusBtn.setOnClickListener(this);
        menuBtn.setOnClickListener(this);
        groupChatBtn.setOnClickListener(this);
        workBookBtn.setOnClickListener(this);
        inviteBtn.setOnClickListener(this);
        msgBtn.setOnClickListener(this);
        exitBtn.setOnClickListener(this);
        walletBtn.setOnClickListener(this);
        lessonBtn.setOnClickListener(this);
        profileImg.setOnClickListener(this);

        friends.setOnClickListener(this);

        status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateStatusBtn.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
        getNotifications();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.profle_img_myprofle:
                selectProfileImage();
                break;

            case R.id.lesson_mp:
                startActivity(new Intent(this, LessonsActivity.class));
                break;

            case R.id.wallet_menu_mp:
                startActivity(new Intent(this, WalletActivity.class));
                break;

            case R.id.exit_menu_mp:
                // exit app
                break;

            case R.id.msg_menu_mp:
                startActivity(new Intent(this, InboxActivity.class));
                break;

            case R.id.invite_menu_mp:
                inviteFriend(this);
                break;

            case R.id.notes_menu_mp:
                startActivity(new Intent(this, WorkBookActivity.class));
                break;

            case R.id.schoolChat_menu_mp:
                startActivity(new Intent(this, ClassChatGroupActivity.class));
                finish();
                break;

            case R.id.menu_mp:
                if (isMenuOpened) {
                    menu.setVisibility(View.GONE);
                    isMenuOpened = false;
                } else {
                    menu.setVisibility(View.VISIBLE);
                    isMenuOpened = true;
                }
                break;

            case R.id.total_friends_myprofile:
                startActivity(new Intent(this, MyFriendsActivity.class));
                break;

            case R.id.update_status_mp:
                updateStatus();
                break;


        }
    }

    private void updateStatus() {
        String s = status.getText().toString();
        if (s.isEmpty()) {
            AppHelper.showToast(this, "Cannot update Empty Status");
        } else {
            User user_ = new User(user.getMobileNum(), user.getNickname(),
                    user.getClassId(), user.getDeviceToken(), user.getProfileImage(),
                    s, user.getTotalLikes(), user.getId());
            MyReferences.userInfoRef().setValue(user_).addOnCompleteListener(task -> {
                // updated
            });
        }
    }

    private void selectProfileImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && data != null && resultCode == Activity.RESULT_OK) {
            if (data.getData() != null) {
                uploadProfileImage(data.getData());
            }
        }
    }

    private void uploadProfileImage(Uri path) {
        if (path != null) {
            Dialog mDialog = AppHelper.getLoadingDialog(this);
            mDialog.show();

            StorageReference storageReference = MyReferences.profileImageStorage(this, path);
            storageReference.putFile(path)
                    .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                String mUrl = uri.toString();
                                User user_ = new User(user.getMobileNum(), user.getNickname(),
                                        user.getClassId(), user.getDeviceToken(), mUrl,
                                        user.getStatus(), user.getTotalLikes(), user.getId());
                                MyReferences.userInfoRef().setValue(user_)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Picasso.get().load(path).fit().noPlaceholder().centerCrop().into(profileImg);
                                                mDialog.dismiss();
                                            }
                                        }).addOnFailureListener(e -> {
                                    mDialog.dismiss();
                                });
                            })).addOnFailureListener(e -> {
                mDialog.dismiss();
            });
        }
    }

    private void setData() {

        MyReferences.userInfoRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    user = snapshot.getValue(User.class);
                    if (user != null) {
                        nickName.setText(user.getNickname());
                        status.setText(user.getStatus());
                        likes.setText(user.getTotalLikes() + "");
                        Picasso.get().load(user.getProfileImage())
                                .noPlaceholder().fit().centerCrop().into(profileImg);

                        MyReferences.friendsRef().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    int count = (int) snapshot.getChildrenCount();
                                    friends.setText(count + "");
                                } else {
                                    friends.setText("0");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e(TAG, "onCancelled: ", error.toException());
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled1: ", error.toException());
            }
        });
    }

    private void setAdapter(@NotNull List<Notification> mList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        NotificationsAdapter adapter = new NotificationsAdapter(mList, this);
        recyclerView.setAdapter(adapter);
    }

    private void getNotifications() {
        list = new ArrayList<>();
        notificationsRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Notification notification = dataSnapshot.getValue(Notification.class);
                        if (notification != null) {
                            list.add(notification);
                        }
                        setAdapter(list);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}