package com.pbp_b_kelompok_1.ticketplease;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.adapters.TicketMovieAdapter;
import com.pbp_b_kelompok_1.ticketplease.api.TicketMovieApi;
import com.pbp_b_kelompok_1.ticketplease.models.TicketMovie;
import com.pbp_b_kelompok_1.ticketplease.models.TicketMovieResponse;
import com.pbp_b_kelompok_1.ticketplease.models.User;
import com.pbp_b_kelompok_1.ticketplease.models.UserResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentTiketMovie extends Fragment {
    private RecyclerView rvTiketMovie;
    private TicketMovieAdapter ticketMovieAdapter;
    private ArrayList<TicketMovie> ticketMovieList;
    private UserPreferences userPreferences;
    private UserResponse userResponse;
    private User user;
    private TicketMovie ticketMovie;

    public FragmentTiketMovie() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tiket_event,container,false);
        rvTiketMovie = view.findViewById(R.id.rvTiketMovie);

        userPreferences = new UserPreferences(this.getContext());
        user = userPreferences.getUserLogin();

        rvTiketMovie.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ticketMovieList = new ArrayList<>();
        ticketMovieAdapter = new TicketMovieAdapter(getContext(),ticketMovieList,userPreferences);
        rvTiketMovie.setAdapter(ticketMovieAdapter);

        getAllTicketMovie();
        return view;
    }

    public void getAllTicketMovie(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, TicketMovieApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TicketMovieResponse ticketMovieResponse = gson.fromJson(response, TicketMovieResponse.class);
                ticketMovieAdapter.setTicketMovieList(ticketMovieResponse.getTicketMovieList());
                Toast.makeText(getContext(), ticketMovieResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
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
                headers.put("Authorization", "Bearer "+ userPreferences.getUserLogin().getAccessToken());  //nanti ini token ambil dari userPreference
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}