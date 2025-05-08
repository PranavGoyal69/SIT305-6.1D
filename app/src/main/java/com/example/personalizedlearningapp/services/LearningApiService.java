package com.example.personalizedlearningapp.services;

import com.example.personalizedlearningapp.models.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface LearningApiService {
    // Existing endpoints
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @GET("tasks")
    Call<TaskResponse> getTasks(@Header("Authorization") String token);

    @GET("tasks/{taskId}")
    Call<Task> getTaskDetails(@Header("Authorization") String token, @Path("taskId") String taskId);

    @POST("tasks/submit")
    Call<ResultResponse> submitAnswers(@Header("Authorization") String token, @Body AnswerSubmission submission);

    // Add new endpoint for quiz generation
    @GET("getQuiz")
    Call<QuizResponse> generateQuiz(
            @Query("topic") String topic,
            @Header("Authorization") String token
    );
}