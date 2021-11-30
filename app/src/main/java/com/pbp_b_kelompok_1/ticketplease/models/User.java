package com.pbp_b_kelompok_1.ticketplease.models;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class User {
    private int idUser;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String alamat;
    private byte[] profilePicture;

    public User(int idUser, String fullName, String email, String username, String password, String alamat, byte[] profilePicture) {
        this.idUser = idUser;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.alamat = alamat;
        this.profilePicture = profilePicture;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
