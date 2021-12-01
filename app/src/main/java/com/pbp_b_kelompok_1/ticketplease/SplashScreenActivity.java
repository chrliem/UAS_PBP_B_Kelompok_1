package com.pbp_b_kelompok_1.ticketplease;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;
    Animation animation;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logo = findViewById(R.id.splashView);

        logo.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent movePage = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(movePage);
                finish();
            }
        },SPLASH_SCREEN);
    }
}