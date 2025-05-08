package com.example.personalizedlearningapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionResult implements Parcelable {
    private String questionText;
    private String userAnswer;
    private String correctAnswer;
    private boolean isCorrect;

    public QuestionResult(String questionText, String userAnswer, String correctAnswer, boolean isCorrect) {
        this.questionText = questionText;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.isCorrect = isCorrect;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public QuestionResult(Parcel in) {
        questionText = in.readString();
        userAnswer = in.readString();
        correctAnswer = in.readString();
        isCorrect = in.readByte() != 0;
    }

    public static final Creator<QuestionResult> CREATOR = new Creator<QuestionResult>() {
        @Override
        public QuestionResult createFromParcel(Parcel in) {
            return new QuestionResult(in);
        }

        @Override
        public QuestionResult[] newArray(int size) {
            return new QuestionResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionText);
        dest.writeString(userAnswer);
        dest.writeString(correctAnswer);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
    }
}