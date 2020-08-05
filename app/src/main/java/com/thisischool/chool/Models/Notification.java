package com.thisischool.chool.Models;

public class Notification {

    private int mCase;
    private String title;
    private String body;
    private String id;

    public Notification() {
    }

    public String getId() {
        return id;
    }

    public int getmCase() {
        return mCase;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Notification(int mCase, String title, String body, String id) {
        this.mCase = mCase;
        this.title = title;
        this.body = body;
        this.id = id;
    }
}
