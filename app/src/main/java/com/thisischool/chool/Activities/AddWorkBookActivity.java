package com.thisischool.chool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.thisischool.chool.Classes.Constants;
import com.thisischool.chool.LocalDatabase.DatabaseHelper;
import com.thisischool.chool.Models.WorkBook;
import com.thisischool.chool.R;

import static com.thisischool.chool.Classes.Constants.ADD_WORKBOOK;
import static com.thisischool.chool.Classes.Constants.UPDATE_WORKBOOK;
import static com.thisischool.chool.Classes.Constants.WORKBOOK_DATA;
import static com.thisischool.chool.Classes.Constants.WORKBOOK_KEY;

public class AddWorkBookActivity extends AppCompatActivity {

    private EditText titleEdit, contentEdit;
    private Button addBtn;
    private WorkBook workBook;
    private String mCase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_book);

        titleEdit = findViewById(R.id.title_workbook);
        contentEdit = findViewById(R.id.content_workbook);
        addBtn = findViewById(R.id.add_workbook);

        setUpdateData();

        addBtn.setOnClickListener(view -> {

            if (contentEdit.getText().toString().isEmpty()) {
               contentEdit.setError("Cannot Add Empty Workbook");
            } else {
                String title = titleEdit.getText().toString();
                if (title.isEmpty()) {
                    title = contentEdit.getText().toString().substring(0,5);
                }
                addWorkbook(title,contentEdit.getText().toString());
            }
        });


    }

    private void addWorkbook(String title, String content) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        WorkBook workBook_ = new WorkBook();
        switch (mCase) {
            case ADD_WORKBOOK:
                workBook_.setTitle(title);
                workBook_.setContent(content);
                databaseHelper.workBookDao().insertWorkBook(workBook_);
                finish();
                break;

            case UPDATE_WORKBOOK:
                workBook_.setContent(content);
                workBook_.setTitle(title);
                workBook_.setId(workBook.getId());
                databaseHelper.workBookDao().updateWorkBook(workBook_);
                finish();
                break;
        }


    }

    private void setUpdateData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCase = extras.getString(WORKBOOK_KEY);
            assert mCase != null;
            if (mCase.equals(UPDATE_WORKBOOK)) {
                addBtn.setText("Update");
                workBook = (WorkBook) getIntent().getSerializableExtra(WORKBOOK_DATA);
                if (workBook != null) {
                    titleEdit.setText(workBook.getTitle());
                    contentEdit.setText(workBook.getContent());
                }
            }
        }
    }
}