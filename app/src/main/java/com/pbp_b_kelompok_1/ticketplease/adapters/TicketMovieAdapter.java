package com.pbp_b_kelompok_1.ticketplease.adapters;

import static com.android.volley.Request.Method.DELETE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.BookMovieActivity;
import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.R;
import com.pbp_b_kelompok_1.ticketplease.VolleySingleton;
import com.pbp_b_kelompok_1.ticketplease.api.TicketMovieApi;
import com.pbp_b_kelompok_1.ticketplease.models.TicketMovie;
import com.pbp_b_kelompok_1.ticketplease.models.TicketMovieResponse;
import com.pbp_b_kelompok_1.ticketplease.models.User;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketMovieAdapter extends RecyclerView.Adapter<TicketMovieAdapter.viewHolder>
{
    List<TicketMovie> ticketMovieList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private UserPreferences userPreferences;

    public TicketMovieAdapter(Context context, List<TicketMovie> ticketMovieList,
                              UserPreferences userPreferences){
        layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.ticketMovieList = ticketMovieList;
        this.userPreferences = userPreferences;
    }

    @NonNull
    @Override
    public TicketMovieAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rv_tiketmovie, parent,false);
        
        final viewHolder holder = new viewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketMovieAdapter.viewHolder holder, int position) {
        TicketMovie ticketMovie = ticketMovieList.get(position);
        holder.kodeTiketMovie.setText(MessageFormat.format("Kode: {0}",
                ticketMovie.getKodeTiketMovie()));
        holder.tanggal.setText(ticketMovie.getTanggalMovie());
        holder.waktu.setText(ticketMovie.getWaktuMovie());
        holder.seat.setText(MessageFormat.format("Seat No: {0}",
                ticketMovie.getSeatNumber()));
        holder.namaMovie.setText(ticketMovie.getNamaMovie());
        holder.namaPemesan.setText(ticketMovie.getNamaPemesan());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View newLayout = LayoutInflater.from(builder.getContext())
                        .inflate(R.layout.fragment_detail_ticket_movie, null);

//                Deklarasi Atribut dari Fragment
                TextView tvNamaMovie, tvPemilikTiket, tvKodeTiket, tvTanggal, tvWaktu,
                        tvSeat, tvSinopsis;
                MaterialButton btnBack;

//                Mendapatkan Id pada activity
                tvNamaMovie = newLayout.findViewById(R.id.tvNamaTiketMovie);
                tvPemilikTiket = newLayout.findViewById(R.id.tvNamaPemilikTiketMovie);
                tvKodeTiket = newLayout.findViewById(R.id.tvKodeTiketMovie);
                tvTanggal = newLayout.findViewById(R.id.tvTanggalTiketMovie);
                tvWaktu = newLayout.findViewById(R.id.tvWaktuTiketMovie);
                tvSeat = newLayout.findViewById(R.id.tvSeatNumberTiketMovie);
                tvSinopsis = newLayout.findViewById(R.id.tvSinopsisMovieDetail);
                btnBack = newLayout.findViewById(R.id.btnBackMovieFrag);

//                Mengeset Tampilan TextView
                tvNamaMovie.setText(ticketMovie.getNamaMovie());
                tvPemilikTiket.setText(ticketMovie.getNamaPemesan());
                tvKodeTiket.setText(String.valueOf(ticketMovie.getKodeTiketMovie()));
                tvTanggal.setText(ticketMovie.getTanggalMovie());
                tvWaktu.setText(ticketMovie.getWaktuMovie());
                tvSinopsis.setText(ticketMovie.getSinopsis());
                tvSeat.setText(ticketMovie.getSeatNumber());

//                menampilkan View builder dengan layout diolog
                builder.setView(newLayout);

//                Show Dialog
                AlertDialog popup = builder.create();
                popup.show();

                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popup.dismiss(); }
                });
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BookMovieActivity.class);
                intent.putExtra("kodeTiketMovie", ticketMovie.getKodeTiketMovie());
                mContext.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder =
                        new MaterialAlertDialogBuilder(mContext);
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin membatalkan tiket movie ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTicketMovie(ticketMovie.getKodeTiketMovie());
                            }
                        }).show();
            }
        });
    }
    private void deleteTicketMovie(long id){
        StringRequest stringRequest = new StringRequest(DELETE,
                TicketMovieApi.DELETE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TicketMovieResponse ticketMovieResponse = gson.fromJson(response,
                        TicketMovieResponse.class);
                Toast.makeText(layoutInflater.getContext(), ticketMovieResponse.getMessage(),
                        Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                try{
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(layoutInflater.getContext(), errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(layoutInflater.getContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+
                        userPreferences.getUserLogin().getAccessToken());
                return headers;
            }
        };
        VolleySingleton.getInstance(layoutInflater.getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return ticketMovieList.size();
    }

    public void setTicketMovieList(List<TicketMovie> ticketMovieList){
        this.ticketMovieList = ticketMovieList;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        protected TextView kodeTiketMovie, tanggal, namaMovie, namaPemesan, waktu, seat;
        protected ImageButton btnEdit, btnDelete;
        protected CardView cardView;

        public viewHolder(View itemView){
            super(itemView);
            this.kodeTiketMovie = itemView.findViewById(R.id.tvKodeTiket);
            this.tanggal = itemView.findViewById(R.id.tvTanggalMovie);
            this.waktu = itemView.findViewById(R.id.tvWaktuMovie);
            this.seat = itemView.findViewById(R.id.tvSeatMovie);
            this.namaMovie = itemView.findViewById(R.id.tvNamaMovieRiwayat);
            this.namaPemesan = itemView.findViewById(R.id.tvNamaPemilikMovie);

            btnEdit = itemView.findViewById(R.id.btnEditMovie);
            btnDelete = itemView.findViewById(R.id.btnDeleteMovie);
            this.cardView = itemView.findViewById(R.id.card_viewMovie);
        }
    }
}
