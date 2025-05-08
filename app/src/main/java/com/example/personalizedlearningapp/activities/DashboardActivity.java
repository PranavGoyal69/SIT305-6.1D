package com.example.personalizedlearningapp.activities;
import com.example.personalizedlearningapp.models.Question;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalizedlearningapp.R;
import com.example.personalizedlearningapp.adapters.TasksAdapter;
import com.example.personalizedlearningapp.models.Task;
import com.example.personalizedlearningapp.models.TaskResponse;
import com.example.personalizedlearningapp.models.QuizResponse;
import com.example.personalizedlearningapp.services.ApiClient;
import com.example.personalizedlearningapp.services.LearningApiService;
import com.example.personalizedlearningapp.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private LearningApiService apiService;
    private List<Task> tasks = new ArrayList<>();
    private TasksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        apiService = ApiClient.getApiService();

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnGenerateQuiz = findViewById(R.id.btnGenerateQuiz);
        EditText etTopic = findViewById(R.id.etTopic);
        RecyclerView rvTasks = findViewById(R.id.rvTasks);

        String username = SharedPrefManager.getInstance(this).getUsername();
        tvWelcome.setText("Hello, " + username);

        // Setup tasks RecyclerView
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TasksAdapter(tasks, task -> {
            Intent intent = new Intent(DashboardActivity.this, QuizActivity.class);

            intent.putExtra("task_id", task.getTaskId());
            startActivity(intent);
        });
        rvTasks.setAdapter(adapter);

        btnLogout.setOnClickListener(v -> {
            SharedPrefManager.getInstance(this).logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        btnGenerateQuiz.setOnClickListener(v -> {
            String topic = etTopic.getText().toString().trim();
            if (!topic.isEmpty()) {
                generateQuiz(topic);
            } else {
                Toast.makeText(this, "Please enter a topic", Toast.LENGTH_SHORT).show();
            }
        });

        loadTasks();
    }

    private void loadTasks() {
        String token = "Bearer " + SharedPrefManager.getInstance(this).getToken();
        Call<TaskResponse> call = apiService.getTasks(token);

        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tasks.clear();
                    tasks.addAll(response.body().getTasks());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(DashboardActivity.this, "Failed to load tasks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateQuiz(String topic) {
        String token = "Bearer " + SharedPrefManager.getInstance(this).getToken();
        Call<QuizResponse> call = apiService.generateQuiz(topic, token);

        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Question> questions = response.body().getQuiz();
                    if (questions != null && !questions.isEmpty()) {
                        Task generatedTask = new Task(
                                "gen-" + System.currentTimeMillis(),
                                "Generated Quiz: " + topic,
                                "AI-generated quiz about " + topic,
                                questions
                        );

                        Intent intent = new Intent(DashboardActivity.this, QuizActivity.class);
                        intent.putExtra("task", generatedTask);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DashboardActivity.this,
                                "Failed to generate quiz", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DashboardActivity.this,
                            "API error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}