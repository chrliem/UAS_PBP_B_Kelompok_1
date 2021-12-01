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

import com.pbp_b_kelompok_1.ticketplease.adapters.EventAdapter;
import com.pbp_b_kelompok_1.ticketplease.databinding.FragmentEventBinding;
import com.pbp_b_kelompok_1.ticketplease.models.DummyEvent;
import com.pbp_b_kelompok_1.ticketplease.models.Event;
import com.pbp_b_kelompok_1.ticketplease.models.User;

import java.util.ArrayList;


public class FragmentEvent extends Fragment {

    private TextView tvUsername;
    private User user;
//    private UserPreferences userPreferences;

    ArrayList<Event> listEvent;
    FragmentEventBinding binding;

    public FragmentEvent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        binding = FragmentEventBinding.inflate(inflater,R.layout.fragment_event,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        tvUsername = view.findViewById(R.id.tvUsername);
//        userPreferences = new UserPreferences(this.getContext());
//        user = userPreferences.getUserLogin();

        tvUsername.setText(user.getUsername());
        listEvent = new DummyEvent().dataEvent;

        EventAdapter adapter = new EventAdapter(getContext(), listEvent);
//        binding.rvEvent.setLayoutManager(new LinearLayoutManager((Activity) this.getContext()));
//        binding.rvEvent.setAdapter(adapter);
    }
}