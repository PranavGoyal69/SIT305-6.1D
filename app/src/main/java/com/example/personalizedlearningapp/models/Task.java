package com.example.personalizedlearningapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Task implements Parcelable {
    private String taskId;
    private String title;
    private String description;
    private List<Question> questions;

    public Task() {
    }

    public Task(String taskId, String title, String description, List<Question> questions) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    protected Task(Parcel in) {
        taskId = in.readString();
        title = in.readString();
        description = in.readString();
        questions = in.createTypedArrayList(Question.CREATOR);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeTypedList(questions);
    }
}