package com.thisischool.chool.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.thisischool.chool.Classes.Constants.TODO_TABLE;

@Entity(tableName = TODO_TABLE)
public class TODO implements Serializable {

    @PrimaryKey (autoGenerate = true)
    private int id;

    @ColumnInfo (name = "content")
    private String content;

    @ColumnInfo (name = "time_stamp")
    private String stamp;

    @ColumnInfo (name = "favorite")
    private boolean favorite;

    @ColumnInfo (name = "title")
    private String title;

    @ColumnInfo (name = "image")
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
