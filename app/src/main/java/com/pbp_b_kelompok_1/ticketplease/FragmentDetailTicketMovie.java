package com.pbp_b_kelompok_1.ticketplease;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetailTicketMovie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetailTicketMovie extends Fragment {

    private static final String bund_namaMovie = "namaMovie";
    private static final String bund_namaPemilik = "namaPemilik";
    private static final String bund_kodeTiketMovie = "kodeTiketMovie";
    private static final String bund_seatNumber = "seatNumber";
    private static final String bund_tanggal = "tanggal";
    private static final String bund_waktu = "waktu";
    private static final String bund_sinopsis = "sinopsis";
    private TextView tvNamaMovie;
    private TextView tvNamaPemilik;
    private TextView tvKodeTiket;
    private TextView tvSeatNumber;
    private TextView tvTanggal;
    private TextView tvWaktu;
    private TextView tvSinopsis;

    public FragmentDetailTicketMovie() {
        // Required empty public constructor
    }

    public static FragmentDetailTicketMovie newInstance(Long kodeTiketMovie, String namaMovie,
                                                        String namaPemilik, String seatNumber,
                                                        String tanggal, String waktu, String sinopsis) {
        Bundle bundle = new Bundle();
        // Save data here
        bundle.putLong(bund_kodeTiketMovie, kodeTiketMovie);
        bundle.putString(bund_namaMovie, namaMovie);
        bundle.putString(bund_namaPemilik, namaPemilik);
        bundle.putString(bund_seatNumber, seatNumber);
        bundle.putString(bund_tanggal, tanggal);
        bundle.putString(bund_waktu, waktu);
        bundle.putString(bund_sinopsis, sinopsis);

        FragmentDetailTicketMovie fragment = new FragmentDetailTicketMovie();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_detail_ticket_movie, container, false);

        Long kodeTiketMovie = getArguments().getLong(bund_kodeTiketMovie);
        String namaMovie = getArguments().getString(bund_namaMovie);
        String namaPemilik = getArguments().getString(bund_namaPemilik);
        String seatNumber = getArguments().getString(bund_seatNumber);
        String tanggal = getArguments().getString(bund_tanggal);
        String waktu = getArguments().getString(bund_waktu);
        String sinopsis = getArguments().getString(bund_sinopsis);

        tvNamaMovie = root.findViewById(R.id.tvNamaTiketMovie);
        tvNamaPemilik = root.findViewById(R.id.tvNamaPemilikTiketMovie);
        tvKodeTiket = root.findViewById(R.id.tvKodeTiketMovie);
        tvSeatNumber = root.findViewById(R.id.tvSeatNumberTiketMovie);
        tvTanggal = root.findViewById(R.id.tvTanggalTiketMovie);
        tvWaktu = root.findViewById(R.id.tvWaktuTiketMovie);
        tvSinopsis = root.findViewById(R.id.tvSinopsisMovieDetail);

        tvKodeTiket.setText(Long.toString(kodeTiketMovie));
        tvNamaMovie.setText(namaMovie);
        tvNamaPemilik.setText(namaPemilik);
        tvSeatNumber.setText(seatNumber);
        tvTanggal.setText(tanggal);
        tvWaktu.setText(waktu);
        tvSinopsis.setText(sinopsis);

        return root;
    }
}