package com.thisischool.chool.Models;

public class NickName {

    private String nickname;
    private String extra;
    private String id;

    public NickName() {
    }

    public String getNickname() {
        return nickname;
    }

    public String getExtra() {
        return extra;
    }

    public String getId() {
        return id;
    }

    public NickName(String nickname, String extra, String id) {
        this.nickname = nickname;
        this.extra = extra;
        this.id = id;
    }
}
