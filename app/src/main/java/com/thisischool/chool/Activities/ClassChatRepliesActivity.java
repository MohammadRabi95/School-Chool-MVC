package com.thisischool.chool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thisischool.chool.Adapters.ClassChatGroupAdapter;
import com.thisischool.chool.Adapters.RepliesAdapter;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.Classes.Constants;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.Replies;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ClassChatRepliesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String id;
    private EditText editText;
    private ImageView btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_chat_replies);
        recyclerView = findViewById(R.id.chat_rep_rec);
        id = getIntent().getStringExtra(Constants.REPLIES_KEY);
        editText = findViewById(R.id.reply_edit_replies);
        btn = findViewById(R.id.reply_replies);

        btn.setOnClickListener(view -> {
            if (editText.getText().toString().isEmpty()) {
                editText.setError("Empty Field");
                editText.requestFocus();
                return;
            }
            reply(editText.getText().toString());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
    }

    private void setData() {
        List<Replies> list = new ArrayList<>();
        Query query = MyReferences.classGroupReplies(this).orderByChild("messageId").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Replies replies = dataSnapshot.getValue(Replies.class);
                        if (replies != null) {
                            list.add(replies);
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

    private void setAdapter(@NotNull List<Replies> mList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        RepliesAdapter adapter = new RepliesAdapter(mList, this);
        recyclerView.setAdapter(adapter);
    }

    private void reply(String content) {
        DatabaseReference reference = MyReferences.classGroupReplies(this);
        String pId = reference.push().getKey();
        Replies replies = new Replies(pId, content, id,
                Controller.CurrentUser.getUID(), Controller.CurrentUser.getUserNickname(this));
        reference.child(pId).setValue(replies).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AppHelper.showToast(this,"Replied");
                editText.setText("");
            }
        });

    }
}