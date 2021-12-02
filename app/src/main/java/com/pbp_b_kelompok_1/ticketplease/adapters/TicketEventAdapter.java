package com.pbp_b_kelompok_1.ticketplease.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.pbp_b_kelompok_1.ticketplease.R;
import com.pbp_b_kelompok_1.ticketplease.models.TicketEvent;

import org.w3c.dom.Text;

import java.util.List;

public class TicketEventAdapter extends RecyclerView.Adapter<TicketEventAdapter.viewHolder>{
    List<TicketEvent> ticketEventList;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public TicketEventAdapter(Context context, List<TicketEvent> ticketEventList){
        layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.ticketEventList = ticketEventList;
    }

    @NonNull
    @Override
    public TicketEventAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rv_tiketevent, parent,false);
        final viewHolder holder= new viewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketEventAdapter.viewHolder holder, int position) {
        TicketEvent ticketEvent = ticketEventList.get(position);
        holder.kodeBooking.setText(position);
        holder.tanggal.setText(ticketEvent.getTanggalEvent());
        holder.namaEvent.setText(ticketEvent.getNamaEvent());
        holder.venueEvent.setText(ticketEvent.getVenueEvent());
        holder.namaPemesan.setText(ticketEvent.getNamaPemesan());
    }

    @Override
    public int getItemCount() {
        return ticketEventList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
        protected TextView kodeBooking, tanggal, namaEvent, venueEvent, namaPemesan;
        public viewHolder(View itemView){
            super(itemView);
            this.kodeBooking = itemView.findViewById(R.id.tvKodeBooking);
            this.tanggal = itemView.findViewById(R.id.tvTanggal);
            this.namaEvent = itemView.findViewById(R.id.tvNamaEventRiwayat);
            this.venueEvent = itemView.findViewById(R.id.tvVenueRiwayat);
            this.namaPemesan = itemView.findViewById(R.id.tvNamaPemilik);
        }
    }
}
