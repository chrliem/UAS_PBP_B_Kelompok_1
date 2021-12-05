package com.pbp_b_kelompok_1.ticketplease;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.models.User;


public class FragmentProfile extends Fragment {

    private ImageButton btnSetting;
    private Button btnLogout;
    private TextView textNama, textUsername, textEmail, textAlamat;
    private User user;
    private UserPreferences userPreferences;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textNama = view.findViewById(R.id.tvNama);
        textUsername = view.findViewById(R.id.tvUsername);
        textEmail = view.findViewById(R.id.tvEmail);
        textAlamat = view.findViewById(R.id.tvAlamat);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnSetting = view.findViewById(R.id.btnSetting);

        userPreferences = new UserPreferences(this.getContext());
        user = userPreferences.getUserLogin();

        textNama.setText(user.getFullName());
        textUsername.setText(user.getUsername());
        textEmail.setText(user.getEmail());

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment = new FragmentProfileEdit();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.layout_fragment, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Logout")
                        .setMessage("Are You Sure?")
                        .setNeutralButton("CLOSE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                userPreferences.logout();
                                Toast.makeText(getContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show();
                                checkLogin();
                            }
                        })
                        .show();
            }
        });
    }

    private void checkLogin() {
//        Fungsi ini akan mengecek jika user login,
//        akan memunculkan toast jika tidak redirect ke login activity
        if (!userPreferences.checkLogin()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), "Heyy Kamu Sudah Login !!", Toast.LENGTH_SHORT).show();
        }
    }

//    public void changeFragment(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.layout_fragment, fragment)
//                .commit();
//    }
}