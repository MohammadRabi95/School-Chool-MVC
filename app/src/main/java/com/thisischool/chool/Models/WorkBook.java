package com.thisischool.chool.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.thisischool.chool.Classes.Constants.WORKBOOK_TABLE;

@Entity (tableName = WORKBOOK_TABLE)
public class WorkBook implements Serializable {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo (name = "title")
    @NonNull
    private String title;

    @ColumnInfo (name = "content")
    @NonNull
    private String content;

    @ColumnInfo (name = "favorite")
    @NonNull
    private boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }
}
