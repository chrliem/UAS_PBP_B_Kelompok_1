package com.pbp_b_kelompok_1.ticketplease.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserCRUDResponse {

    private String message;
    
    @SerializedName("data")
    private User user;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUserCRUD() {
        return user;
    }

    public void setUserCRUD(User user) {
        this.user = user;
    }
}
