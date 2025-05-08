package com.example.personalizedlearningapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personalizedlearningapp.models.ResultResponse;
import com.example.personalizedlearningapp.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaskActivity extends AppCompatActivity {

    // Add other necessary members like buttons, lists, etc.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Trigger this after collecting user answers
        submitAnswersToServer();
    }

    private void submitAnswersToServer() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/tasks/submit")  // Use correct endpoint
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(TaskActivity.this, "Submission failed", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        ResultResponse result = new Gson().fromJson(json, ResultResponse.class);
                        Intent intent = new Intent(TaskActivity.this, ResultsActivity.class);
                        intent.putExtra("results", result); // Parcelable
                        startActivity(intent);
                    } else {
                        runOnUiThread(() ->
                                Toast.makeText(TaskActivity.this, "Error in submission", Toast.LENGTH_SHORT).show()
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(TaskActivity.this, "Unexpected error", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}