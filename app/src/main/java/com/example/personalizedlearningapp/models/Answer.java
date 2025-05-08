package com.example.personalizedlearningapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    private String questionId;
    private String answer;

    public Answer(String questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    protected Answer(Parcel in) {
        questionId = in.readString();
        answer = in.readString();
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public String getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionId);
        dest.writeString(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
