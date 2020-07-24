package com.thisischool.chool.Models;

public class ClassChatGroupMessage {

    private String message;
    private String nickname;
    private String senderId;
    private String messageId;
    private String postImageUrl;
    private String timeStamp;
    private int messageLikes;

    public String getTimeStamp() {
        return timeStamp;
    }

    public int getMessageLikes() {
        return messageLikes;
    }

    public ClassChatGroupMessage() {
    }

    public String getMessage() {
        return message;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public ClassChatGroupMessage(String message, String nickname, String senderId, String timeStamp,
                                 String messageId, String postImageUrl, int messageLikes) {
        this.message = message;
        this.nickname = nickname;
        this.senderId = senderId;
        this.messageId = messageId;
        this.postImageUrl = postImageUrl;
        this.messageLikes = messageLikes;
        this.timeStamp = timeStamp;
    }
}
