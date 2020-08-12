package com.thisischool.chool.Models;

public class Replies {

    private String id;
    private String content;
    private String messageId;
    private String uid;
    private String name;

    public Replies() {
    }

    public Replies(String id, String content, String messageId, String uid, String name) {
        this.id = id;
        this.content = content;
        this.messageId = messageId;
        this.uid = uid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getMessageId() {
        return messageId;
    }

}
