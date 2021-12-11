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
import com.pbp_b_kelompok_1.ticketplease.DetailEventActivity;
import com.pbp_b_kelompok_1.ticketplease.R;
import com.pbp_b_kelompok_1.ticketplease.databinding.RvEventBinding;
import com.pbp_b_kelompok_1.ticketplease.models.Event;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.viewHolder>{
    private Context context;
    ArrayList<Event> listEvent;

    public EventAdapter(Context context, ArrayList<Event> listEvent) {
        this.context = context;
        this.listEvent = listEvent;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private RvEventBinding binding;
        ImageButton btnDetail;
        public viewHolder(@NonNull RvEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RvEventBinding binding = RvEventBinding.inflate(inflater, parent, false);
        return new viewHolder( binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Event event = listEvent.get(position);
        holder.binding.setEvent(event);
        holder.binding.executePendingBindings();
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DetailEvent = new Intent(context, DetailEventActivity.class);
                Gson gson = new Gson();
                String strEvent = gson.toJson(event);

                DetailEvent.putExtra("detailEvent", strEvent);

                context.startActivity(DetailEvent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEvent.size();
    }
}
