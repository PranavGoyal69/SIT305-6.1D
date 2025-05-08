package com.example.personalizedlearningapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedlearningapp.R;
import com.example.personalizedlearningapp.models.QuestionResult;

import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private final List<QuestionResult> questionResults;

    public ResultsAdapter(List<QuestionResult> questionResults) {
        this.questionResults = questionResults != null ? questionResults : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionResult result = questionResults.get(position);
        holder.tvQuestion.setText(result.getQuestionText());
        holder.tvUserAnswer.setText("Your answer: " + result.getUserAnswer());
        holder.tvCorrectAnswer.setText("Correct answer: " + result.getCorrectAnswer());

        if (result.isCorrect()) {
            holder.ivStatus.setImageResource(R.drawable.ic_correct); // ✅
        } else {
            holder.ivStatus.setImageResource(R.drawable.ic_wrong);   // ❌
        }
    }

    @Override
    public int getItemCount() {
        return questionResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvUserAnswer, tvCorrectAnswer;
        ImageView ivStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvUserAnswer = itemView.findViewById(R.id.tvUserAnswer);
            tvCorrectAnswer = itemView.findViewById(R.id.tvCorrectAnswer);
            ivStatus = itemView.findViewById(R.id.ivStatus);
        }
    }
}