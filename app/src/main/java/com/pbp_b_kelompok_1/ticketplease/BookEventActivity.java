package com.pbp_b_kelompok_1.ticketplease;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;
import static com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences.ACCESS_TOKEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
    private AutoCompleteTextView ddSection, ddSeatNumber, ddTime;
    private ImageButton btnBack;
    private Button btnPesan;
    private User user;
    private LinearLayout layoutLoading;
    private UserPreferences userPreferences;
    private UserResponse userResponse;
    private static final String[] sectionAray = new String[]{
            "VVIP","VIP","Reguler"
    };
    private static final String[] seatArray = new String[]{
            "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"
    };
    private static final String[] timeArray = new String[]{
            "08.00 WIB","11.00 WIB","14:00 WIB","17:00 WIB","20:00 WIB"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_event);

        String strEvent = getIntent().getStringExtra("detailEvent");
        Gson gson = new Gson();
        event = gson.fromJson(strEvent, Event.class);

        binding.setEvent(event);

        ddSection = findViewById(R.id.ddSection);
        ddTime = findViewById(R.id.ddTime);
        ddSeatNumber = findViewById(R.id.ddSeatNumber);

        ArrayAdapter<String> adapterSection = new ArrayAdapter<>(this, R.layout.list_item, sectionAray);
        ddSection.setAdapter(adapterSection);

        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(this, R.layout.list_item,timeArray);
        ddTime.setAdapter(adapterTime);

        ArrayAdapter<String> adapterSeat = new ArrayAdapter<>(this, R.layout.list_item, seatArray);
        ddSeatNumber.setAdapter(adapterSeat);

        userPreferences = new UserPreferences(this);
        user = userPreferences.getUserLogin();
        btnPesan = findViewById(R.id.btnPesan);
        long id = getIntent().getLongExtra("kodeTiket",-1);
//        long id = getIntent().getIntExtra("kodeTiket",-1);

        if(id==-1){
            btnPesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addTicketEvent();
                    onBackPressed();
                }
            });
        }else{
            getTicketEventbyId(id);

            btnPesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateTicketEvent(id);
                    onBackPressed();
                }
            });
        }

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void getTicketEventbyId(long id){
        StringRequest stringRequest = new StringRequest(GET, TicketEventApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    TicketEventResponse ticketEventResponse = gson.fromJson(response, TicketEventResponse.class);

                    TicketEvent ticketEvent = ticketEventResponse.getTicketEventList().get(0);
                    TextView tvEventName = findViewById(R.id.tvEventNameOrder);
                    TextView tvHarga = findViewById(R.id.tvEventPriceOrder);
                    TextView tvDate = findViewById(R.id.tvDate);
                    TextView tvPrice = findViewById(R.id.tvPrice);

                    tvEventName.setText(ticketEvent.getNamaEvent());
                    tvHarga.setText(String.valueOf(ticketEvent.getHarga()));
                    ddSection.setText(ticketEvent.getSection());
                    ddSeatNumber.setText(ticketEvent.getSeatNumber());
                    ddTime.setText(ticketEvent.getWaktuEvent());
                    tvDate.setText(ticketEvent.getTanggalEvent());
                    tvPrice.setText(String.valueOf(ticketEvent.getHarga()));
                    Toast.makeText(BookEventActivity.this, ticketEventResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try{
                        String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        JSONObject errors = new JSONObject(responseBody);
                        Toast.makeText(BookEventActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(BookEventActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer "+ userPreferences.getUserLogin().getAccessToken());
                    return headers;
                }
            };            VolleySingleton.getInstance(BookEventActivity.this).addToRequestQueue(stringRequest);


    }

    private void addTicketEvent(){
        final String namaEvent = event.getNamaEvent();
        final String namaPemesan = user.getFullName();
        final String section = ddSection.getText().toString();
        final String seatNumber = ddSeatNumber.getText().toString();
        final String tanggalEvent = event.getTanggalEvent();
        final String waktuEvent = ddTime.getText().toString();
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

    private void updateTicketEvent(long id){
        TicketEvent ticketEvent = new TicketEvent(
                null,
                null,
                ddSection.getText().toString(),
                ddSeatNumber.getText().toString(),
                null,
                ddTime.getText().toString(),
                null,
                null,
                null
        );

        StringRequest stringRequest = new StringRequest(PUT, TicketEventApi.UPDATE_URL + id, new Response.Listener<String>() {
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
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(BookEventActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(BookEventActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
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