package com.example.personalizedlearningapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ResultResponse implements Parcelable {
    private int score;
    private List<QuestionResult> questionResults;

    public ResultResponse(int score, List<QuestionResult> questionResults) {
        this.score = score;
        this.questionResults = questionResults;
    }

    protected ResultResponse(Parcel in) {
        score = in.readInt();
        questionResults = in.createTypedArrayList(QuestionResult.CREATOR);
    }

    public static final Creator<ResultResponse> CREATOR = new Creator<ResultResponse>() {
        @Override
        public ResultResponse createFromParcel(Parcel in) {
            return new ResultResponse(in);
        }

        @Override
        public ResultResponse[] newArray(int size) {
            return new ResultResponse[size];
        }
    };

    public int getScore() {
        return score;
    }

    public List<QuestionResult> getQuestionResults() {
        return questionResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(score);
        dest.writeTypedList(questionResults);
    }
}