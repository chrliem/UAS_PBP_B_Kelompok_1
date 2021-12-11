package com.pbp_b_kelompok_1.ticketplease.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.DetailMovieActivity;
import com.pbp_b_kelompok_1.ticketplease.R;
import com.pbp_b_kelompok_1.ticketplease.databinding.RvMovieBinding;
import com.pbp_b_kelompok_1.ticketplease.models.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.viewHolder>
{
    private Context context;
    ArrayList<Movie> listMovie;

    public MovieAdapter(Context context, ArrayList<Movie> listMovie) {
        this.context = context;
        this.listMovie = listMovie;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private RvMovieBinding binding;

        ImageButton btnDetail;
        public viewHolder(@NonNull RvMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            btnDetail = itemView.findViewById(R.id.btnDetailMovie);
        }
    }

    @NonNull
    @Override
    public MovieAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        RvMovieBinding binding = RvMovieBinding.inflate(inflater, parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.viewHolder holder, int position) {
        Movie movie = listMovie.get(position);
        holder.binding.setMovie(movie);
        holder.binding.executePendingBindings();

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DetailMovie = new Intent(context, DetailMovieActivity.class);
                Gson gson = new Gson();
                String strMovie = gson.toJson(movie);

                DetailMovie.putExtra("detailMovie", strMovie);

                context.startActivity(DetailMovie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }
}
