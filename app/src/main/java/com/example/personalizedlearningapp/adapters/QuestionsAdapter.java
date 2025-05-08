package com.example.personalizedlearningapp.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedlearningapp.R;
import com.example.personalizedlearningapp.models.Answer;
import com.example.personalizedlearningapp.models.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Question> questions;
    private List<Answer> selectedAnswers = new ArrayList<>();

    public QuestionsAdapter(List<Question> questions) {
        this.questions = questions != null ? questions : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.tvQuestion.setText(
                question.getQuestionText() != null ? question.getQuestionText() : "No question text"
        );

        holder.radioGroup.setOnCheckedChangeListener(null); // Prevent recycled listener conflicts
        holder.radioGroup.removeAllViews();

        List<String> options = question.getOptions() != null ? question.getOptions() : new ArrayList<>();

        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(holder.itemView.getContext());
            radioButton.setText(options.get(i));
            radioButton.setId(i);
            holder.radioGroup.addView(radioButton);
        }

        // Restore selected answer
        for (int i = 0; i < options.size(); i++) {
            Answer selected = getAnswerForQuestion(question.getQuestionId());
            if (selected != null && options.get(i).equals(selected.getAnswer())) {
                ((RadioButton) holder.radioGroup.getChildAt(i)).setChecked(true);
                break;
            }
        }

        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId >= 0 && checkedId < options.size()) {
                Answer answer = new Answer(question.getQuestionId(), options.get(checkedId));
                selectedAnswers.removeIf(a -> Objects.equals(a.getQuestionId(), question.getQuestionId()));
                selectedAnswers.add(answer);
            }
        });
    }

    private Answer getAnswerForQuestion(String questionId) {
        for (Answer a : selectedAnswers) {
            if (Objects.equals(a.getQuestionId(), questionId)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public List<Answer> getSelectedAnswers() {
        return selectedAnswers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        RadioGroup radioGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }
    }
}