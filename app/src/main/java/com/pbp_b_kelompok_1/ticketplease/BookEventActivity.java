package com.pbp_b_kelompok_1.ticketplease;

import static com.android.volley.Request.Method.POST;
import static com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences.ACCESS_TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.api.TicketEventApi;
import com.pbp_b_kelompok_1.ticketplease.databinding.ActivityBookEventBinding;
import com.pbp_b_kelompok_1.ticketplease.models.Event;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEvent;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEventResponse;
import com.pbp_b_kelompok_1.ticketplease.models.User;
import com.pbp_b_kelompok_1.ticketplease.models.UserResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BookEventActivity extends AppCompatActivity {

    private Event event;
    private ActivityBookEventBinding binding;
    private Spinner spinnerSeat;
    private Spinner spinnerSection;
    private Spinner spinnerTime;
    private ImageButton btnBack;
    private Button btnPesan;
    private User user;
    private LinearLayout layoutLoading;
    private UserPreferences userPreferences;
    private UserResponse userResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_event);

        String strEvent = getIntent().getStringExtra("detailEvent");
        Gson gson = new Gson();
        event = gson.fromJson(strEvent, Event.class);

        binding.setEvent(event);

        spinnerSection = findViewById(R.id.spinnerSection);
        spinnerSeat = findViewById(R.id.spinnerSeatNumber);
        spinnerTime = findViewById(R.id.spinnerTime);

        userPreferences = new UserPreferences(this);
        user = userPreferences.getUserLogin();
        btnPesan = findViewById(R.id.btnPesan);
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTicketEvent();
                onBackPressed();
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void addTicketEvent(){
        final String namaEvent = event.getNamaEvent();
//        final String namaPemesan = userResponse.getUser().getFullName();
//        final String namaPemesan = userPreferences.getUserLogin().getFullName();
//        final String namaPemesan = "Christian Woi";
        final String namaPemesan = user.getFullName();
        final String section = spinnerSection.getSelectedItem().toString();
        final String seatNumber = spinnerSeat.getSelectedItem().toString();
        final String tanggalEvent = event.getTanggalEvent();
        final String waktuEvent = spinnerTime.getSelectedItem().toString();
        final String venueEvent = event.getVenueEvent();
        final String alamatEvent = event.getAlamatEvent();
        final Double harga = event.getHargaEvent();

        TicketEvent ticketEvent = new TicketEvent(
                namaEvent,
                namaPemesan,
                section,
                seatNumber,
                tanggalEvent,
                waktuEvent,
                venueEvent,
                alamatEvent,
                harga
        );

        StringRequest stringRequest = new StringRequest(POST, TicketEventApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        TicketEventResponse ticketEventResponse = gson.fromJson(response, TicketEventResponse.class);

                        Toast.makeText(BookEventActivity.this, ticketEventResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(BookEventActivity.this, errors.getString("message"),Toast.LENGTH_SHORT).show();
//                    Toast.makeText(BookEventActivity.this, errors.getString(userPreferences.getUserLogin().getAccessToken()),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(BookEventActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
//                headers.get(userPreferences.getUserLogin().getAccessToken());
                headers.put("Authorization", "Bearer "+ userPreferences.getUserLogin().getAccessToken());  //nanti ini token ambil dari userPreference
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(ticketEvent);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}