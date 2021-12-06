package com.pbp_b_kelompok_1.ticketplease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = getIntent().getLongExtra("id",0);
        //                Bundle bundle = new Bundle();
//                bundle.putLong("id",userResponse.getUser().getId());
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                FragmentProfile fragmentProfile = new FragmentProfile();
//
//                fragmentProfile.setArguments(bundle);
//                getSupportFragmentManager().beginTransaction().add(R.id.layout_fragment, fragmentProfile).commit();
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
                changeFragment(new FragmentTiketMovie());
            } else if (item.getItemId() == R.id.menuProfile) {
                Bundle bundle = new Bundle();
                bundle.putLong("id",id);
                FragmentProfile fragmentProfile = new FragmentProfile();
                fragmentProfile.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_fragment, fragmentProfile)
                        .commit();
//                changeFragment(new FragmentProfile());
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