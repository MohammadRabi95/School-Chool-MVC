package com.thisischool.chool.Models;

public class Notes {
    private String title, content, id, name;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Notes() {
    }

    public Notes(String title, String content, String id, String name) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.name = name;
    }
}
