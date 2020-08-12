package com.thisischool.chool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.thisischool.chool.Classes.Constants;
import com.thisischool.chool.Models.TODO;
import com.thisischool.chool.R;

import static com.thisischool.chool.Classes.Constants.ADD_WORKBOOK;
import static com.thisischool.chool.Classes.Constants.TODO_DATA;
import static com.thisischool.chool.Classes.Constants.TODO_KEY;
import static com.thisischool.chool.Classes.Constants.UPDATE_WORKBOOK;

public class AddTodoActivity extends AppCompatActivity {

    private String type;
    private TODO todo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        type = getIntent().getStringExtra(TODO_KEY);
        todo = (TODO) getIntent().getSerializableExtra(TODO_DATA);

        switch (type) {
            case UPDATE_WORKBOOK:
                update();
                break;

            case ADD_WORKBOOK:
                add();
                break;
        }

    }

    private void update() {

    }

    private void add() {

    }
}