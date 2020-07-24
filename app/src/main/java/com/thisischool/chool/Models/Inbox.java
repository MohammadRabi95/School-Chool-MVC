package com.thisischool.chool.Models;

public class Inbox {

    private String senderId;
    private String receiverId;
    private String chatId;
    private String message;
    private String senderImageUrl;
    private String receiverImageUrl;
    private String senderNickname;
    private String receiverNickname;

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public String getReceiverImageUrl() {
        return receiverImageUrl;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public Inbox() {
    }

    public Inbox(String senderId, String receiverId, String chatId, String message,
                 String senderImageUrl, String receiverImageUrl, String senderNickname,
                 String receiverNickname) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.chatId = chatId;
        this.message = message;
        this.senderImageUrl = senderImageUrl;
        this.receiverImageUrl = receiverImageUrl;
        this.senderNickname = senderNickname;
        this.receiverNickname = receiverNickname;
    }
}
