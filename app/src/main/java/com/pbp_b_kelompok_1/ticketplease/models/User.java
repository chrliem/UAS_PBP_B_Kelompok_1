package com.pbp_b_kelompok_1.ticketplease.models;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("name")
    private String fullName;
    private String email;
    private String username;
    private String password;
    @SerializedName("imgURL")
    private String imgUrl;
    private Long id;

    public User(Long  id, String accessToken, String fullName, String email, String username, String password, String imgUrl) {
        this.id = id;
        this.accessToken = accessToken;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

