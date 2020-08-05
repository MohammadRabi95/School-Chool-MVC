package com.thisischool.chool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thisischool.chool.Adapters.ClassChatGroupAdapter;
import com.thisischool.chool.Adapters.FriendsAdapter;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.Friends;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Friends> friendsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

        recyclerView = findViewById(R.id.recyclerview_myfriends);
    }

    private void setAdapter(@NotNull List<Friends> mList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        FriendsAdapter adapter = new FriendsAdapter(mList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(mList.size() - 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
    }

    private void setData() {
        friendsList = new ArrayList<>();
        MyReferences.friendsRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    friendsList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Friends friends = dataSnapshot.getValue(Friends.class);
                        if (friends != null) {
                            friendsList.add(friends);
                        }
                        setAdapter(friendsList);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}