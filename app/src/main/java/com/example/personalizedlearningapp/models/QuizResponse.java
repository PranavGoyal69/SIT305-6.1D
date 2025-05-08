package com.example.personalizedlearningapp.models;

import java.util.List;

public class QuizResponse {
    private List<Question> quiz;
    private String error;

    public List<Question> getQuiz() {
        return quiz;
    }

    public String getError() {
        return error;
    }
}