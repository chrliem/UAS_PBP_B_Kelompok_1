package com.pbp_b_kelompok_1.ticketplease.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.mapbox.services.android.navigation.v5.navigation.NavigationUnitType;
import com.pbp_b_kelompok_1.ticketplease.models.User;

public class UserPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public static final String KEY_ID = "id";
    public static final String IS_LOGIN = "isLogin";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_IMAGE = "imgUrl";

    public UserPreferences(Context context){
        this.context = context;

        sharedPreferences = context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(Long id, String access_token, String name, String email,
                         String username, String password, String imgUrl){
        editor.putLong(KEY_ID, id);
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(ACCESS_TOKEN, access_token);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_IMAGE, imgUrl);

        editor.commit();
    }

    public User getUserLogin(){
        String access_token, username, password, name, email, imgUrl;
        Long id;

        id = sharedPreferences.getLong(KEY_ID, 0L);
        access_token = sharedPreferences.getString(ACCESS_TOKEN, null);
        username = sharedPreferences.getString(KEY_USERNAME, null);
        password = sharedPreferences.getString(KEY_PASSWORD, null);
        name = sharedPreferences.getString(KEY_NAME, null);
        email = sharedPreferences.getString(KEY_EMAIL, null);
        imgUrl = sharedPreferences.getString(KEY_IMAGE,null);

        return new User(id, access_token, name, email, username, password, imgUrl);
    }

    public boolean checkLogin(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
