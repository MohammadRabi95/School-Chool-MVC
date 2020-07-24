package com.thisischool.chool.Models;

public class FriendRequest {

    private String senderId;

    public FriendRequest() {
    }

    public FriendRequest(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderId() {
        return senderId;
    }

}
