package com.thisischool.chool.Models;

public class Question {

    private String question;
    private String name;
    private int answersCount;
    private String id;

    public String getName() {
        return name;
    }

    public int getAnswersCount() {
        return answersCount;
    }

    public String getQuestion() {
        return question;
    }

    public String getId() {
        return id;
    }

    public Question() {
    }

    public Question(String question, String id, int answersCount, String name) {
        this.question = question;
        this.id = id;
        this.answersCount = answersCount;
        this.name = name;
    }
}
