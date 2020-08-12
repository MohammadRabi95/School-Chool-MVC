package com.thisischool.chool.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thisischool.chool.Adapters.TodoAdapter;
import com.thisischool.chool.Adapters.WorkBookAdapter;
import com.thisischool.chool.Classes.Constants;
import com.thisischool.chool.LocalDatabase.DatabaseHelper;
import com.thisischool.chool.Models.TODO;
import com.thisischool.chool.Models.WorkBook;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.thisischool.chool.Classes.Constants.ADD_WORKBOOK;
import static com.thisischool.chool.Classes.Constants.TODO_KEY;
import static com.thisischool.chool.Classes.Constants.WORKBOOK_KEY;

public class WorkBookActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_book);
        recyclerView = findViewById(R.id.recyclerview_workbook);
        recyclerView1 = findViewById(R.id.recyclerview_todolist);
        FloatingActionButton fab = findViewById(R.id.add_fab_workbook);
        FloatingActionButton floatingActionButton = findViewById(R.id.add_fab_todo);

        floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(this,AddTodoActivity.class)
            .putExtra(TODO_KEY,ADD_WORKBOOK));
        });

        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, AddWorkBookActivity.class)
                    .putExtra(WORKBOOK_KEY,ADD_WORKBOOK));
        });

        DatabaseHelper.getInstance(this, Constants.WORKBOOK_TABLE).notesDao()
                .getAllWorkBook().observe(this, this::setAdapter1);

        DatabaseHelper.getInstance(this, Constants.TODO_TABLE).todoDao()
                .getAllTodoList().observe(this, this::setAdapter2);

    }
    private void setAdapter1(@NotNull List<WorkBook> mList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        WorkBookAdapter adapter = new WorkBookAdapter(mList, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setAdapter2(@NotNull List<TODO> mList) {
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        TodoAdapter adapter = new TodoAdapter(mList, this);
        recyclerView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}