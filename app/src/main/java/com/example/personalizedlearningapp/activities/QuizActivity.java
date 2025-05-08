package com.example.personalizedlearningapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedlearningapp.R;
import com.example.personalizedlearningapp.adapters.QuestionsAdapter;
import com.example.personalizedlearningapp.models.Answer;
import com.example.personalizedlearningapp.models.Question;
import com.example.personalizedlearningapp.models.Task;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private RecyclerView rvQuiz;
    private QuestionsAdapter questionsAdapter;
    private Button btnSubmit;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        rvQuiz = findViewById(R.id.rvQuiz);
        btnSubmit = findViewById(R.id.btnSubmit);

        task = getIntent().getParcelableExtra("task");

        if (task != null && task.getQuestions() != null) {
            rvQuiz.setLayoutManager(new LinearLayoutManager(this));
            questionsAdapter = new QuestionsAdapter(task.getQuestions());
            rvQuiz.setAdapter(questionsAdapter);
        }

        btnSubmit.setOnClickListener(v -> {
            List<Answer> selectedAnswers = questionsAdapter.getSelectedAnswers();
            if (selectedAnswers.size() < task.getQuestions().size()) {
                Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);

            intent.putExtra("answers", selectedAnswers.toArray(new Answer[0]));
            intent.putExtra("taskId", task.getTaskId());
            intent.putExtra("questions", task.getQuestions().toArray(new Question[0]));
            startActivity(intent);
            finish();
        });
    }
}