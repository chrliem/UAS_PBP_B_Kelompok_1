package com.pbp_b_kelompok_1.ticketplease;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentDetailTicketEvent extends Fragment {

    private static final String bund_namaEvent = "namaEvent";
    private static final String bund_namaPemilik = "namaPemilik";
    private static final String bund_kodeBooking = "kodeBooking";
    private static final String bund_section = "section";
    private static final String bund_seatNumber = "seatNumber";
    private static final String bund_tanggal = "tanggal";
    private static final String bund_venue = "venue";
    private TextView tvNamaEvent;
    private TextView tvNamaPemilik;
    private TextView tvKodeBooking;
    private TextView tvSection;
    private TextView tvSeatNumber;
    private TextView tvTanggal;
    private TextView tvVenue;

    public FragmentDetailTicketEvent() {
        // Required empty public constructor
    }

    public static FragmentDetailTicketEvent newInstance(int kodeBooking, String namaEvent,
                                                   String namaPemilik, String section, String seatNumber,
                                                   String tanggal, String venue) {
        Bundle bundle = new Bundle();
        // Save data here
        bundle.putInt(bund_kodeBooking, kodeBooking);
        bundle.putString(bund_namaEvent, namaEvent);
        bundle.putString(bund_namaPemilik, namaPemilik);
        bundle.putString(bund_section, section);
        bundle.putString(bund_seatNumber, seatNumber);
        bundle.putString(bund_tanggal, tanggal);
        bundle.putString(bund_venue, venue);
        FragmentDetailTicketEvent fragment = new FragmentDetailTicketEvent();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_detail_ticket_event, container, false);

        int kodeBooking = getArguments().getInt(bund_kodeBooking);
        String namaEvent = getArguments().getString(bund_namaEvent);
        String namaPemilik = getArguments().getString(bund_namaPemilik);
        String section = getArguments().getString(bund_section);
        String seatNumber = getArguments().getString(bund_seatNumber);
        String tanggal = getArguments().getString(bund_tanggal);
        String venue = getArguments().getString(bund_venue);

        tvNamaEvent = root.findViewById(R.id.tvNamaTiket);
        tvNamaPemilik = root.findViewById(R.id.tvNamaPemilikTiket);
        tvKodeBooking = root.findViewById(R.id.tvKodeBookingTiket);
        tvSection = root.findViewById(R.id.tvSectionTiket);
        tvSeatNumber = root.findViewById(R.id.tvSeatNumberTiket);
        tvTanggal = root.findViewById(R.id.tvTanggalTiket);
        tvVenue = root.findViewById(R.id.tvVenueTiket);

        tvKodeBooking.setText(Integer.toString(kodeBooking));
        tvNamaEvent.setText(namaEvent);
        tvNamaPemilik.setText(namaPemilik);
        tvSection.setText(section);
        tvSeatNumber.setText(seatNumber);
        tvTanggal.setText(tanggal);
        tvVenue.setText(venue);

        return root;
    }
}