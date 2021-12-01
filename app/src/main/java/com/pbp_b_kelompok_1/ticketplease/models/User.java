package com.pbp_b_kelompok_1.ticketplease.models;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

public class User {
    private int idUser;
    @SerializedName("name")
    private String fullName;
    private String email;
    private String username;
    private String password;


    public User(int idUser, String fullName, String email, String username, String password) {
        this.idUser = idUser;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

