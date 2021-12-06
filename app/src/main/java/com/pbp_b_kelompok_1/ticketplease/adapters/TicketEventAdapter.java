package com.pbp_b_kelompok_1.ticketplease.adapters;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.GET;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.pbp_b_kelompok_1.ticketplease.BookEventActivity;
import com.pbp_b_kelompok_1.ticketplease.FragmentDetailTicketEvent;
import com.pbp_b_kelompok_1.ticketplease.FragmentTiketEvent;
import com.pbp_b_kelompok_1.ticketplease.MainActivity;
import com.pbp_b_kelompok_1.ticketplease.Preferences.UserPreferences;
import com.pbp_b_kelompok_1.ticketplease.R;
import com.pbp_b_kelompok_1.ticketplease.VolleySingleton;
import com.pbp_b_kelompok_1.ticketplease.api.TicketEventApi;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEvent;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEventResponse;
import com.pbp_b_kelompok_1.ticketplease.models.User;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketEventAdapter extends RecyclerView.Adapter<TicketEventAdapter.viewHolder>{
    List<TicketEvent> ticketEventList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private UserPreferences userPreferences;
    private User user;
    private TicketEventAdapter ticketEventAdapter;

    public TicketEventAdapter(Context context, List<TicketEvent> ticketEventList, UserPreferences userPreferences){
        layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.ticketEventList = ticketEventList;
        this.userPreferences = userPreferences;
    }

    @NonNull
    @Override
    public TicketEventAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rv_tiketevent, parent,false);
//        userPreferences = new UserPreferences(view.getContext());
//        user = userPreferences.getUserLogin();
        final viewHolder holder = new viewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketEventAdapter.viewHolder holder, int position) {
        TicketEvent ticketEvent = ticketEventList.get(position);
        holder.kodeBooking.setText(String.valueOf(ticketEvent.getKodeTiket()));
        holder.tanggal.setText(ticketEvent.getTanggalEvent());
        holder.namaEvent.setText(ticketEvent.getNamaEvent());
        holder.venueEvent.setText(ticketEvent.getVenueEvent());
        holder.namaPemesan.setText(ticketEvent.getNamaPemesan());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View newLayout = LayoutInflater.from(builder.getContext()).inflate(R.layout.fragment_detail_ticket_event, null);

//                Deklarasi Atribut dari Fragment
                TextView tvNamaEvent, tvPemilikTiket, tvKodeBooking, tvSection, tvTanggalWaktu, tvVenue, tvSeat;
                MaterialButton btnBack;

//                Mendapatkan Id pada activity
                tvNamaEvent = newLayout.findViewById(R.id.tvNamaTiket);
                tvPemilikTiket = newLayout.findViewById(R.id.tvNamaPemilikTiket);
                tvKodeBooking = newLayout.findViewById(R.id.tvKodeBookingTiket);
                tvSection = newLayout.findViewById(R.id.tvSectionTiket);
                tvTanggalWaktu = newLayout.findViewById(R.id.tvTanggalTiket);
                tvVenue = newLayout.findViewById(R.id.tvVenueTiket);
                tvSeat = newLayout.findViewById(R.id.tvSeatNumberTiket);
                btnBack = newLayout.findViewById(R.id.btnBack);

//                Mengeset Tampilan TextView
                tvNamaEvent.setText(ticketEvent.getNamaEvent());
                tvPemilikTiket.setText(ticketEvent.getNamaPemesan());
                tvKodeBooking.setText(String.valueOf(ticketEvent.getKodeTiket()));
                tvSection.setText(ticketEvent.getSection());
                tvTanggalWaktu.setText(ticketEvent.getTanggalEvent() + "\n" +ticketEvent.getWaktuEvent());
                tvVenue.setText(ticketEvent.getVenueEvent());
                tvSeat.setText(ticketEvent.getSeatNumber());

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
                Intent intent = new Intent(mContext, BookEventActivity.class);
                intent.putExtra("kodeTiket", ticketEvent.getKodeTiket());
                mContext.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder =
                        new MaterialAlertDialogBuilder(mContext);
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin membatalkan tiket event ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteTicketEvent(ticketEvent.getKodeTiket());
                            }
                        })
                        .show();
            }
        });
    }
    private void deleteTicketEvent(long id){
        StringRequest stringRequest = new StringRequest(DELETE, TicketEventApi.DELETE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TicketEventResponse ticketEventResponse = gson.fromJson(response, TicketEventResponse.class);
                Toast.makeText(layoutInflater.getContext(), ticketEventResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                ticketEventAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                try{
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(layoutInflater.getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(layoutInflater.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer "+ userPreferences.getUserLogin().getAccessToken());  //nanti ini token ambil dari userPreference
                return headers;
            }
        };
        VolleySingleton.getInstance(layoutInflater.getContext()).addToRequestQueue(stringRequest);

    }

//    public void getAllTicketEvent(){
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, TicketEventApi.GET_ALL_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Gson gson = new Gson();
//                TicketEventResponse ticketEventResponse = gson.fromJson(response, TicketEventResponse.class);
//                ticketEventAdapter.setTicketEventList(ticketEventResponse.getTicketEventList());
//                Toast.makeText(mContext.getApplicationContext(), ticketEventResponse.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
//                    JSONObject errors = new JSONObject(responseBody);
//
//                    Toast.makeText(mContext.getApplicationContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    Toast.makeText(mContext.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Accept", "application/json");
//                headers.put("Authorization", "Bearer "+ userPreferences.getUserLogin().getAccessToken());  //nanti ini token ambil dari userPreference
//                return headers;
//            }
//        };
//        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
//    }
//
    @Override
    public int getItemCount() {
        return ticketEventList.size();
    }

    public void setTicketEventList(List<TicketEvent> ticketEventList){
        this.ticketEventList = ticketEventList;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        protected TextView kodeBooking, tanggal, namaEvent, venueEvent, namaPemesan;
        protected ImageButton btnEdit, btnDelete;
        protected CardView cardView;
        public viewHolder(View itemView){
            super(itemView);
            this.kodeBooking = itemView.findViewById(R.id.tvKodeBooking);
            this.tanggal = itemView.findViewById(R.id.tvTanggal);
            this.namaEvent = itemView.findViewById(R.id.tvNamaEventRiwayat);
            this.venueEvent = itemView.findViewById(R.id.tvVenueRiwayat);
            this.namaPemesan = itemView.findViewById(R.id.tvNamaPemilik);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            this.cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
