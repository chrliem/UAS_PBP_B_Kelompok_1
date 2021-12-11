package com.pbp_b_kelompok_1.ticketplease.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    private String message;

    @SerializedName("user")
    private User user;

    private String access_token;

    public UserResponse(String message, User user, String access_token) {
        this.message = message;
        this.user = user;
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
