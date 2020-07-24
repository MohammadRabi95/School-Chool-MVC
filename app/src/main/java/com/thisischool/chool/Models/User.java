package com.thisischool.chool.Models;

public class User {

    private String mobileNum;
    private String nickname;
    private String classId;
    private String profileImage;
    private String status;
    private int totalLikes;
    private String deviceToken;
    private String id;

    public User() {
    }

    public User(String mobileNum, String nickname, String classId, String deviceToken,
                String profileImage, String status, int totalLikes, String id) {
        this.mobileNum = mobileNum;
        this.nickname = nickname;
        this.classId = classId;
        this.profileImage = profileImage;
        this.status = status;
        this.totalLikes = totalLikes;
        this.deviceToken = deviceToken;
        this.id = id;
    }


    public String getDeviceToken() {
        return deviceToken;
    }

    public String getId() {
        return id;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public String getNickname() {
        return nickname;
    }

    public String getClassId() {
        return classId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getStatus() {
        return status;
    }
}
