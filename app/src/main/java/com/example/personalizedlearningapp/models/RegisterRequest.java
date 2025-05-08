package com.example.personalizedlearningapp.models;

import java.util.List;

public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String phone;
    private List<String> interests;

    public RegisterRequest(String username, String email, String password, String phone, List<String> interests) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.interests = interests;
    }

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public List<String> getInterests() { return interests; }
}