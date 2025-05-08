package com.example.personalizedlearningapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Question implements Parcelable {
    private String questionId;
    private String questionText;
    private List<String> options;
    private String correctAnswer;

    public Question() {
    }

    protected Question(Parcel in) {
        questionId = in.readString();
        questionText = in.readString();
        options = in.createStringArrayList();
        correctAnswer = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionId);
        dest.writeString(questionText);
        dest.writeStringList(options);
        dest.writeString(correctAnswer);
    }
}