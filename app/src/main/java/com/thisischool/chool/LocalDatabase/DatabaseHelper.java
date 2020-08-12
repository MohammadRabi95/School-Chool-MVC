package com.thisischool.chool.LocalDatabase;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.thisischool.chool.Models.WorkBook;
import static com.thisischool.chool.Classes.Constants.WORKBOOK_TABLE;

@Database(entities = {WorkBook.class}, version = 2, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract NotesDao notesDao();

    public abstract TodoDao todoDao();

    private static volatile DatabaseHelper INSTANCE;

    public static DatabaseHelper getInstance(final Context context, String table) {
        if (INSTANCE == null) {
            synchronized (DatabaseHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseHelper.class, table)
                            .fallbackToDestructiveMigration().allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
