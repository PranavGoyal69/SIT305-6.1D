package com.example.personalizedlearningapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.os.Parcelable;
import java.util.Arrays;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedlearningapp.R;
import com.example.personalizedlearningapp.adapters.ResultsAdapter;
import com.example.personalizedlearningapp.models.Answer;
import com.example.personalizedlearningapp.models.Question;
import com.example.personalizedlearningapp.models.QuestionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        RecyclerView rvResults = findViewById(R.id.rvResults);
        rvResults.setLayoutManager(new LinearLayoutManager(this));

        Parcelable[] parcelables = getIntent().getParcelableArrayExtra("answers");
        Answer[] answers = Arrays.copyOf(parcelables, parcelables.length, Answer[].class);


        Question[] questions = (Question[]) getIntent().getParcelableArrayExtra("questions");

        if (answers != null && questions != null) {
            List<QuestionResult> resultList = new ArrayList<>();
            int score = 0;

            for (Question q : questions) {
                String correct = q.getCorrectAnswer();
                String userAnswer = null;

                for (Answer a : answers) {
                    if (Objects.equals(a.getQuestionId(), q.getQuestionId())) {
                        userAnswer = a.getAnswer();
                        break;
                    }
                }

                boolean isCorrect = correct != null && userAnswer != null &&
                        correct.trim().equalsIgnoreCase(userAnswer.trim());

                if (isCorrect) score++;

                resultList.add(new QuestionResult(
                        q.getQuestionText(),
                        userAnswer != null ? userAnswer : "No answer",
                        correct != null ? correct : "N/A",
                        isCorrect
                ));
            }

            ResultsAdapter resultsAdapter = new ResultsAdapter(resultList);
            rvResults.setAdapter(resultsAdapter);

            Log.d("ResultsActivity", "Score: " + score + "/" + questions.length);
        } else {
            Log.e("ResultsActivity", "Answers or Questions are missing from intent");
        }
    }
}