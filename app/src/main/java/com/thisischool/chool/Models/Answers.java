package com.thisischool.chool.Models;

public class Answers {

    private String id, questionId, answer, name;

    public Answers() {
    }

    public String getId() {
        return id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public String getName() {
        return name;
    }

    public Answers(String id, String questionId, String answer, String name) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
        this.name = name;
    }
}
