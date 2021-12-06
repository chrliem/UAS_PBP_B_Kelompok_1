package com.pbp_b_kelompok_1.ticketplease.UnitTesting;

import android.content.Context;
import android.content.Intent;

import com.pbp_b_kelompok_1.ticketplease.LoginActivity;

public class LoginUtil {
    private Context context;

    public LoginUtil(Context context) {
        this.context = context;
    }

    public void startMainLogin() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
