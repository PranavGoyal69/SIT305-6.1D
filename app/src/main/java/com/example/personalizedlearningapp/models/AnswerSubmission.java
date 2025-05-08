package com.example.personalizedlearningapp.models;

import java.util.List;

public class AnswerSubmission {
    private String taskId;
    private List<Answer> answers;

    public AnswerSubmission(String taskId, List<Answer> answers) {
        this.taskId = taskId;
        this.answers = answers;
    }

    // Getters
    public String getTaskId() { return taskId; }
    public List<Answer> getAnswers() { return answers; }
}