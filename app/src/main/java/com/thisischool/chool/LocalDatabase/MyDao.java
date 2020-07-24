package com.thisischool.chool.LocalDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thisischool.chool.Models.WorkBook;

import java.util.List;

@Dao
public interface MyDao {

    @Query("SELECT * FROM work_book")
    LiveData<List<WorkBook>> getAllWorkBook();

    @Insert
    void insertWorkBook(WorkBook workBook);

    @Query("DELETE FROM work_book WHERE id = :id")
    void deleteWorkBook(int id);

    @Update
    void updateWorkBook(WorkBook workBook);
}
