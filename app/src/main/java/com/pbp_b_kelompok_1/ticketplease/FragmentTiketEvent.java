package com.pbp_b_kelompok_1.ticketplease;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.pbp_b_kelompok_1.ticketplease.adapters.TicketEventAdapter;
import com.pbp_b_kelompok_1.ticketplease.api.TicketEventApi;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEvent;

import java.util.ArrayList;
import java.util.List;


public class FragmentTiketEvent extends Fragment {

   private RecyclerView rvTiketEvent;
   private TicketEventAdapter ticketEventAdapter;
   private ArrayList<TicketEvent> ticketEventList;
   private RequestQueue requestQueue;

    public FragmentTiketEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ticketEventList = new ArrayList<>();
        ticketEventAdapter = new TicketEventAdapter(getContext(),ticketEventList);
        getAllTicketEvent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tiket_event,container,false);
        rvTiketEvent = view.findViewById(R.id.rvTiketEvent);
        rvTiketEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTiketEvent.setAdapter(ticketEventAdapter);
        return view;
//        return inflater.inflate(R.layout.fragment_tiket_event, container, false);
    }

    public void getAllTicketEvent(){
//        StringRequest request = new StringRequest(Request.Method.GET, TicketEventApi.ADD_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        })
    }

}