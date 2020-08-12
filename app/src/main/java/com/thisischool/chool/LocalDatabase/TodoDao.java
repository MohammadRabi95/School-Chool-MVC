package com.thisischool.chool.LocalDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thisischool.chool.Models.TODO;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo_table")
    LiveData<List<TODO>> getAllTodoList();

    @Query("SELECT * FROM todo_table WHERE favorite = :isFav")
    LiveData<List<TODO>> getFavTodoList(boolean isFav); // always pass true for it.

    @Insert
    void insertTodo(TODO todo);

    @Query("DELETE FROM todo_table WHERE id = :id")
    void deleteTodo(int id);

    @Update
    void updateTodo(TODO todo);
}
