package com.thisischool.chool.Models;

public class PrivateChatMessage {

    private String message;
    private String id;

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public PrivateChatMessage(String message, String id) {
        this.message = message;
        this.id = id;
    }

    public PrivateChatMessage() {
    }
}
