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
import com.google.firebase.database.ValueEventListener;
import com.thisischool.chool.Adapters.ClassChatGroupAdapter;
import com.thisischool.chool.Adapters.QuestionsAdapter;
import com.thisischool.chool.Classes.AppHelper;
import com.thisischool.chool.Classes.Controller;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.Question;
import com.thisischool.chool.OnlineDatabase.MyReferences;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Question> list;
    private EditText questionEdit;
    private ImageView postBtn;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        recyclerView = findViewById(R.id.recyclerView_questions);
        questionEdit = findViewById(R.id.question_edit_qa);
        postBtn = findViewById(R.id.send_qa);

        postBtn.setOnClickListener(view -> {
            if (questionEdit.getText() != null) {
                postQuestion(questionEdit.getText().toString());
            }
            else {
                questionEdit.setError("Empty Field");
                questionEdit.requestFocus();
            }
        });
        reference = MyReferences.classGroupQuestions(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
    }

    private void postQuestion(String question) {
        String id = reference.push().getKey();
        Question ques = new Question(question,id,0,
                Controller.CurrentUser.getUserNickname(this));
        reference.child(id).setValue(ques).addOnCompleteListener(task -> {
            AppHelper.showCenterToast(this,"Posted");
            questionEdit.setText("");
        });
    }

    private void setData() {
        list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            list.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Question question = dataSnapshot.getValue(Question.class);
                                if (question != null) {
                                    list.add(question);
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
    private void setAdapter(@NotNull List<Question> mList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        QuestionsAdapter adapter = new QuestionsAdapter(mList, this);
        recyclerView.setAdapter(adapter);
    }
}