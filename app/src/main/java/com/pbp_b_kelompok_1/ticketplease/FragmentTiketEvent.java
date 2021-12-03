package com.pbp_b_kelompok_1.ticketplease;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.adapters.TicketEventAdapter;
import com.pbp_b_kelompok_1.ticketplease.api.TicketEventApi;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEvent;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEventResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, TicketEventApi.ADD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TicketEventResponse ticketEventResponse = gson.fromJson(response, TicketEventResponse.class);

                ticketEventAdapter.setTicketEventList(ticketEventResponse.getTicketEventList());

                Toast.makeText(getContext(), ticketEventResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}