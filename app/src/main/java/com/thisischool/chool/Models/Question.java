package com.thisischool.chool.Models;

public class Question {

    private String question;
    private String id;

    public String getQuestion() {
        return question;
    }

    public String getId() {
        return id;
    }

    public Question() {
    }

    public Question(String question, String id) {
        this.question = question;
        this.id = id;
    }
}
