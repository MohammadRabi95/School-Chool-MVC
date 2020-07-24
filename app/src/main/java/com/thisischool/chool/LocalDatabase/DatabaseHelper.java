package com.thisischool.chool.LocalDatabase;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.thisischool.chool.Models.WorkBook;
import static com.thisischool.chool.Classes.Constants.WORKBOOK_TABLE;

@Database(entities = {WorkBook.class}, version = 2, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract MyDao workBookDao();

    private static volatile DatabaseHelper INSTANCE;

    public static DatabaseHelper getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseHelper.class, WORKBOOK_TABLE)
                            .fallbackToDestructiveMigration().allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
