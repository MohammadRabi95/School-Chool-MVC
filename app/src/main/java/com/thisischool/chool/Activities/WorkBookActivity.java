package com.thisischool.chool.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thisischool.chool.Adapters.ClassChatGroupAdapter;
import com.thisischool.chool.Adapters.WorkBookAdapter;
import com.thisischool.chool.LocalDatabase.DatabaseHelper;
import com.thisischool.chool.Models.ClassChatGroupMessage;
import com.thisischool.chool.Models.WorkBook;
import com.thisischool.chool.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.thisischool.chool.Classes.Constants.ADD_WORKBOOK;
import static com.thisischool.chool.Classes.Constants.UPDATE_WORKBOOK;
import static com.thisischool.chool.Classes.Constants.WORKBOOK_KEY;

public class WorkBookActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_book);
        recyclerView = findViewById(R.id.recyclerview_workbook);
        FloatingActionButton fab = findViewById(R.id.add_fab_workbook);

        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, AddWorkBookActivity.class)
                    .putExtra(WORKBOOK_KEY,ADD_WORKBOOK));
        });

        DatabaseHelper.getInstance(this).workBookDao()
                .getAllWorkBook().observe(this, this::setAdapter);

    }
    private void setAdapter(@NotNull List<WorkBook> mList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        WorkBookAdapter adapter = new WorkBookAdapter(mList, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}