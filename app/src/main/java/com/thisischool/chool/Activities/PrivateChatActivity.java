package com.thisischool.chool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thisischool.chool.Adapters.PrivateChatAdapter;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.Inbox;
import com.thisischool.chool.Models.PrivateMessages;
import com.thisischool.chool.Models.User;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrivateChatActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ClassChatGroup";
    private RecyclerView recyclerView;
    private List<ClassChatGroupMessage> messageList;
    private boolean isMenuOpened = false;
    private EditText newMessageEdit;
    private LinearLayout menu;
    private String id = "";
    private DatabaseReference inboxRef;
    private String Receiver;
    private User receiverUser,senderUser;
    List<PrivateMessages> messagesList;
    CircleImageView profileImage;
    TextView username;
   private int check=0;
   private int check1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        profileImage=findViewById(R.id.ProfileImageMessageActivityId);
        username=findViewById(R.id.UsernameMessageActivityId);
        Bundle bundle=getIntent().getExtras();
        messagesList=new ArrayList<>();
        if(bundle!=null){
            Receiver=bundle.getString("Receiver");
        }
        else {
            Receiver="";
        }
        UsersInformation();
        readMessage(Controller.CurrentUser.getUID(),Receiver);
        recyclerView = findViewById(R.id.pc_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        RecyclerView.LayoutManager layoutManager=linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        newMessageEdit = findViewById(R.id.msgEdit_pc);
        menu = findViewById(R.id.opened_menu_pc);

        ImageView selectImageBtn, sendBtn, lessons, menuBtn, questionsBtn,
                classInfoBtn, notesBtn, mProfileBtn, schoolChatBtn;

        selectImageBtn = findViewById(R.id.select_img_pc);
        sendBtn = findViewById(R.id.send_msg_pc);
        lessons = findViewById(R.id.lesson_pc);
        menuBtn = findViewById(R.id.menu_pc);
        questionsBtn = findViewById(R.id.questions_menu_pc);
        classInfoBtn = findViewById(R.id.classInfo_menu_pc);
        notesBtn = findViewById(R.id.notes_menu_pc);
        mProfileBtn = findViewById(R.id.profile_menu_pc);
        schoolChatBtn = findViewById(R.id.schoolChat_menu_pc);

        selectImageBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        lessons.setOnClickListener(this);
        menuBtn.setOnClickListener(this);
        questionsBtn.setOnClickListener(this);
        classInfoBtn.setOnClickListener(this);
        notesBtn.setOnClickListener(this);
        mProfileBtn.setOnClickListener(this);
        schoolChatBtn.setOnClickListener(this);



    }



    @Override
    public void onClick(@NotNull View view) {

        switch (view.getId()) {

            case R.id.select_img_pc:
                startActivity(new Intent(this, ImageMessageActivity.class));
                break;
            case R.id.send_msg_pc:
                if (!newMessageEdit.getText().toString().isEmpty()) {
                    //SendMessage.sendMessageToGroupChat(this, newMessageEdit.getText().toString(), "");
                    sendMessage( newMessageEdit.getText().toString());
                } else {
                    newMessageEdit.setError("Empty Message Cannot be send");
                    newMessageEdit.requestFocus();
                }
                break;
            case R.id.lesson_pc:
                startActivity(new Intent(this, LessonsActivity.class));
                break;
            case R.id.menu_pc:
                if (isMenuOpened) {
                    menu.setVisibility(View.GONE);
                    isMenuOpened = false;
                } else {
                    menu.setVisibility(View.VISIBLE);
                    isMenuOpened = true;
                }
                break;
            case R.id.notes_menu_pc:
                startActivity(new Intent(this, NotesActivity.class));
                break;
            case R.id.classInfo_menu_pc:
                startActivity(new Intent(this, ClassInfoActivity.class));
                break;
            case R.id.profile_menu_pc:
                startActivity(new Intent(this, MyProfileActivity.class));
                break;
            case R.id.schoolChat_menu_pc:
                startActivity(new Intent(this, ClassChatGroupActivity.class));
                finish();
                break;
            case R.id.questions_menu_pc:

                break;
        }
    }
    void sendMessage(String message){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Inboxs");


        PrivateMessages messages=new PrivateMessages();
        messages.setMessage(message);
        messages.setName(senderUser.getNickname());
        messages.setReceiver(Receiver);
        messages.setSender(Controller.CurrentUser.getUID());



        databaseReference.push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PrivateChatActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PrivateChatActivity.this, "Message send failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    void UsersInformation(){
        DatabaseReference receiverReference = MyReferences.otherUserInfoRef(Receiver);
        receiverReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user=snapshot.getValue(User.class);
                    receiverUser=user;
                    username.setText(user.getNickname());
                    //Picasso.get().load(receiverUser.getProfileImage()).centerCrop().fit().into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference senderReference = MyReferences.otherUserInfoRef(Controller.CurrentUser.getUID());
        senderReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user=snapshot.getValue(User.class);
                    senderUser=user;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void readMessage(final String MyId, final String userId){

        DatabaseReference  messageReference=FirebaseDatabase.getInstance().getReference("Inboxs");
        messageReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                messagesList.clear();
                for(DataSnapshot post: dataSnapshot.getChildren()){
                    PrivateMessages chat=new PrivateMessages();
                    if(post!=null){
                        chat.setSender(post.child("sender").getValue().toString());
                        chat.setReceiver(post.child("receiver").getValue().toString());
                        chat.setMessage(post.child("message").getValue().toString());
                        chat.setName(post.child("name").getValue().toString());
                    }
                    if(chat.getReceiver().equals(MyId)&&chat.getSender().equals(userId)||
                            chat.getSender().equals(MyId)&&chat.getReceiver().equals(userId)){
                        messagesList.add(chat);

                    }
                    PrivateChatAdapter messageAdapter=new PrivateChatAdapter(messagesList,PrivateChatActivity.this);

                    recyclerView.setAdapter(messageAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


}