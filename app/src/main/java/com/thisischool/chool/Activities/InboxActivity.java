package com.thisischool.chool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thisischool.chool.Adapters.InboxUserAdapter;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.InboxMyUser;
import com.thisischool.chool.Models.PrivateMessages;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {
RecyclerView recyclerView;
List<InboxMyUser> list;
List<String> users;
DatabaseReference reference;
List<String> userList;
List<User> mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        recyclerView=findViewById(R.id.recyclerview_myuser);
        list=new ArrayList<>();
        userList=new ArrayList<>();
        mUser=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        reference=FirebaseDatabase.getInstance().getReference("Inboxs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot post: snapshot.getChildren()){
                    PrivateMessages messages=post.getValue(PrivateMessages.class);
                    if(Controller.CurrentUser.getUID().equals(messages.getSender())){
                        userList.add(messages.getReceiver());

                    }
                    if(Controller.CurrentUser.getUID().equals(messages.getReceiver())){
                        userList.add(messages.getSender());
                    }
                }

                getUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getUser() {
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser.clear();
                for(DataSnapshot post: snapshot.getChildren()){
                    System.out.println("****************************************************"+post.child("UserInfo").getValue(User.class).getNickname());
                    User user=post.child("UserInfo").getValue(User.class);
                    for(String id: userList){
                        if(user.getId().equals(id)){
                            if(mUser.size()!=0){
                                for(int i=0 ; i<mUser.size();i++){
                                    if(!user.getId().equals(mUser.get(i).getId())){
                                        if(!mUser.contains(user)){
                                            mUser.add(user);
                                        }


                                    }
                                }
                            }else {
                                mUser.add(user);
                            }
                        }
                    }
                }

                InboxUserAdapter adapter=new InboxUserAdapter(InboxActivity.this,mUser);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}