package com.pbp_b_kelompok_1.ticketplease;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.api.TicketMovieApi;
import com.pbp_b_kelompok_1.ticketplease.databinding.ActivityBookMovieBinding;
import com.pbp_b_kelompok_1.ticketplease.models.Movie;
import com.pbp_b_kelompok_1.ticketplease.models.TicketMovie;
import com.pbp_b_kelompok_1.ticketplease.models.TicketMovieResponse;
import com.pbp_b_kelompok_1.ticketplease.models.User;
import com.pbp_b_kelompok_1.ticketplease.models.UserResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BookMovieActivity extends AppCompatActivity {

    private Movie movie;
    private ActivityBookMovieBinding binding;
    private AutoCompleteTextView ddSeatNumberMovie, ddTimeMovie;
    private ImageButton btnBackMovie;
    private Button btnPesanMovie;
    private User user;
    private LinearLayout layoutLoading;
    private UserPreferences userPreferences;
    private UserResponse userResponse;
    private static final String[] seatArray = new String[]{
            "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"
    };
    private static final String[] timeArray = new String[]{
            "08.00 WIB","11.00 WIB","14:00 WIB","17:00 WIB","20:00 WIB"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_movie);

        String strMovie = getIntent().getStringExtra("detailMovie");
        Gson gson = new Gson();
        movie = gson.fromJson(strMovie, Movie.class);

        binding.setMovie(movie);

        ddTimeMovie = findViewById(R.id.ddTimeMovie);
        ddSeatNumberMovie = findViewById(R.id.ddSeatNumberMovie);

        ArrayAdapter<String> adapterTimeMovie = new ArrayAdapter<>(this, R.layout.list_item,timeArray);
        ddTimeMovie.setAdapter(adapterTimeMovie);

        ArrayAdapter<String> adapterSeatMovie = new ArrayAdapter<>(this, R.layout.list_item, seatArray);
        ddSeatNumberMovie.setAdapter(adapterSeatMovie);

        userPreferences = new UserPreferences(this);
        user = userPreferences.getUserLogin();

        btnPesanMovie = findViewById(R.id.btnPesan);

        long id = getIntent().getLongExtra("kodeTiket",-1);
        Toast.makeText(BookMovieActivity.this, id + "", Toast.LENGTH_SHORT).show();

        if(id==-1){
            btnPesanMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addTicketMovie();
                    onBackPressed();
                }
            });
        }else{
            getTicketMoviebyId(id);

            btnPesanMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateTicketMovie(id);
                    onBackPressed();
                }
            });
        }

        btnBackMovie = findViewById(R.id.btnBack);
        btnBackMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getTicketMoviebyId(long id){
        StringRequest stringRequest = new StringRequest(GET, TicketMovieApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TicketMovieResponse ticketMovieResponse = gson.fromJson(response, TicketMovieResponse.class);

                TicketMovie ticketMovie = ticketMovieResponse.getTicketMovieList().get(0);
                TextView tvMovieName = findViewById(R.id.tvMovieNameOrder);
                TextView tvHarga = findViewById(R.id.tvMoviePriceOrder);
                TextView tvDate = findViewById(R.id.tvDate);
                TextView tvPrice = findViewById(R.id.tvPrice);

                tvMovieName.setText(ticketMovie.getNamaMovie());
                tvHarga.setText(String.valueOf(ticketMovie.getHarga()));
                ddSeatNumberMovie.setText(ticketMovie.getSeatNumber(),false);
                ddTimeMovie.setText(ticketMovie.getWaktuMovie(),false);
                tvDate.setText(ticketMovie.getTanggalMovie());
                tvPrice.setText(String.valueOf(ticketMovie.getHarga()));
                Toast.makeText(BookMovieActivity.this, ticketMovieResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(BookMovieActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(BookMovieActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        };
        VolleySingleton.getInstance(BookMovieActivity.this).addToRequestQueue(stringRequest);
    }

    private void addTicketMovie(){
        final String namaMovie = movie.getNamaMovie();
        final String namaPemesan = user.getFullName();
        final String seatNumber = ddSeatNumberMovie.getText().toString();
        final String tanggalMovie = movie.getTanggalMovie();
        final String waktuMovie = ddTimeMovie.getText().toString();
        final String sinopsis = movie.getSinopsis();
        final Double harga = movie.getHargaMovie();

        TicketMovie ticketMovie = new TicketMovie(
                namaMovie,
                namaPemesan,
                seatNumber,
                tanggalMovie,
                waktuMovie,
                sinopsis,
                harga
        );

        StringRequest stringRequest = new StringRequest(POST, TicketMovieApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        TicketMovieResponse ticketMovieResponse = gson.fromJson(response, TicketMovieResponse.class);

                        Toast.makeText(BookMovieActivity.this, ticketMovieResponse.getMessage(), Toast.LENGTH_SHORT).show();

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

                    Toast.makeText(BookMovieActivity.this, errors.getString("message"),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(BookMovieActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(ticketMovie);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void updateTicketMovie(long id){
        TicketMovie ticketMovie = new TicketMovie(
                null,
                null,
                ddSeatNumberMovie.getText().toString(),
                null,
                ddTimeMovie.getText().toString(),
                null,
                null
        );

        StringRequest stringRequest = new StringRequest(PUT, TicketMovieApi.UPDATE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TicketMovieResponse ticketMovieResponse = gson.fromJson(response, TicketMovieResponse.class);

                Toast.makeText(BookMovieActivity.this, ticketMovieResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BookMovieActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(BookMovieActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
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
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(ticketMovie);
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