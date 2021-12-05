package com.pbp_b_kelompok_1.ticketplease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        changeFragment(new FragmentEvent());
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.menuEvent) {
                changeFragment(new FragmentEvent());
            } else if(item.getItemId() == R.id.tiketEvent){
                changeFragment(new FragmentTiketEvent());
            } else if (item.getItemId() == R.id.menuMovie) {
                changeFragment(new FragmentMovie());
            } else if (item.getItemId() == R.id.tiketMovie) {
                changeFragment(new FragmentProfile());
            } else if (item.getItemId() == R.id.menuProfile) {
                changeFragment(new FragmentProfile());
            }
            return true;
        }
    };

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, fragment)
                .commit();
    }
}