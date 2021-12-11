package com.pbp_b_kelompok_1.ticketplease;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.adapters.MovieAdapter;
import com.pbp_b_kelompok_1.ticketplease.databinding.FragmentMovieBinding;
import com.pbp_b_kelompok_1.ticketplease.models.DummyMovie;
import com.pbp_b_kelompok_1.ticketplease.models.Movie;
import com.pbp_b_kelompok_1.ticketplease.models.User;

import java.util.ArrayList;

public class FragmentMovie extends Fragment {

    private TextView tvUsername;
    private User user;
    private UserPreferences userPreferences;

    ArrayList<Movie> listMovie;
    FragmentMovieBinding binding;

    public FragmentMovie() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container,
                false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUsername = view.findViewById(R.id.tvUsername);
        userPreferences = new UserPreferences(this.getContext());
        user = userPreferences.getUserLogin();

        tvUsername.setText(user.getUsername());
        listMovie = new DummyMovie().dataMovie;

        MovieAdapter adapter = new MovieAdapter(getContext(), listMovie);
        binding.rvMovie.setLayoutManager(new LinearLayoutManager((Activity) this.getContext()));
        binding.rvMovie.setAdapter(adapter);
    }
}