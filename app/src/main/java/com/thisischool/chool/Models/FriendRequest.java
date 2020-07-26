package com.thisischool.chool.Models;

public class FriendRequest {

    private String senderId;
    private String reqId;

    public String getReqId() {
        return reqId;
    }

    public FriendRequest() {
    }

    public FriendRequest(String senderId, String reqId) {
        this.reqId = reqId;
        this.senderId = senderId;
    }

    public String getSenderId() {
        return senderId;
    }

}
