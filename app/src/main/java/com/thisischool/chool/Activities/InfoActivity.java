package com.thisischool.chool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thisischool.chool.Adapters.ClassChatGroupAdapter;
import com.thisischool.chool.Adapters.InfoAdapter;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.Info;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.thisischool.chool.Classes.AppHelper.inviteFriend;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView inviteFriend,kickSomeone,schoolNum,studentCount;
    private List<Info> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        recyclerView = findViewById(R.id.recyclerview_info);

        inviteFriend = findViewById(R.id.invite_info);
        kickSomeone = findViewById(R.id.kick_vote_info);
        schoolNum = findViewById(R.id.school_num_info);
        studentCount = findViewById(R.id.strength_info);
        inviteFriend.setOnClickListener(this);
        kickSomeone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invite_info:
                inviteFriend(this);
                break;
            case R.id.kick_vote_info:

                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        strength();
    }

    private void strength() {
        list = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Class_Chat_Groups").child("strength");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    studentCount.setText(snapshot.getChildrenCount() + " Students");
                    schoolNum.setText("School ID: " + Controller.CurrentUser.getUserClassId(InfoActivity.this));
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Info info = dataSnapshot.getValue(Info.class);
                        if (info != null) {
                            list.add(info);
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

    private void setAdapter(@NotNull List<Info> mList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        InfoAdapter adapter = new InfoAdapter(mList, this);
        recyclerView.setAdapter(adapter);
    }
}