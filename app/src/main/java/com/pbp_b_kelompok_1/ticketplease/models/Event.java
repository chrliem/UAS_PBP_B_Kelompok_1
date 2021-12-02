package com.pbp_b_kelompok_1.ticketplease.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class Event {
    private String tanggalEvent;
    private String namaEvent;
    private String urlImage;
    private String venueEvent;
    private String alamatEvent;
    private double hargaEvent;

    public Event(String tanggalEvent, String namaEvent, String urlImage, String venueEvent, String alamatEvent, double hargaEvent) {
        this.tanggalEvent = tanggalEvent;
        this.namaEvent = namaEvent;
        this.urlImage = urlImage;
        this.venueEvent = venueEvent;
        this.alamatEvent = alamatEvent;
        this.hargaEvent = hargaEvent;
    }

    public String getTanggalEvent() {
        return tanggalEvent;
    }

    public void setTanggalEvent(String tanggalEvent) {
        this.tanggalEvent = tanggalEvent;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getVenueEvent() {
        return venueEvent;
    }

    public void setVenueEvent(String venueEvent) {
        this.venueEvent = venueEvent;
    }

    public String getAlamatEvent() {
        return alamatEvent;
    }

    public void setAlamatEvent(String alamatEvent) {
        this.alamatEvent = alamatEvent;
    }

    public double getHargaEvent() {
        return hargaEvent;
    }

    public void setHargaEvent(double hargaEvent) {
        this.hargaEvent = hargaEvent;
    }

    @BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String imgURL){
        Glide.with(imageView)
                .load(imgURL)
                .into(imageView);
    }
}
