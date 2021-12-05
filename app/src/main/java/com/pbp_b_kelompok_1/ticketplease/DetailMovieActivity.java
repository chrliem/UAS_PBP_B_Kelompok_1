package com.pbp_b_kelompok_1.ticketplease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.databinding.ActivityDetailMovieBinding;
import com.pbp_b_kelompok_1.ticketplease.models.Movie;

public class DetailMovieActivity extends AppCompatActivity {

    Movie movie;
    ActivityDetailMovieBinding binding;
    private Button btnPesanTiketMovie;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie);
        
//        Ambil Data Intent
        String strMovie = getIntent().getStringExtra("detailMovie");
        Gson gson = new Gson();
        movie = gson.fromJson(strMovie, Movie.class);

//        Inisialisasi objek dan variable ke data binding
        binding.setMovie(movie);

        ImageButton btnBackMovie = findViewById(R.id.btnBackMovieDetail);
        btnBackMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnPesanTiketMovie = findViewById(R.id.btnPesanTiketMovie);
        btnPesanTiketMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DetailMovie = new Intent(DetailMovieActivity.this, BookMovieActivity.class);
                Gson gson = new Gson();
                String strMovie = gson.toJson(movie);

//                Menyisipkan data json string ke intent
                DetailMovie.putExtra("detailMovie", strMovie);
                startActivity(DetailMovie);
            }
        });
    }
    
}