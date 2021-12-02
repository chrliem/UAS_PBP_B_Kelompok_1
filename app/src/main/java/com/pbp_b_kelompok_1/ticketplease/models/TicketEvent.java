package com.pbp_b_kelompok_1.ticketplease.models;

public class TicketEvent {
    private String namaEvent;
    private String namaPemesan;
    private String section;
    private String seatNumber;
    private String tanggalEvent;
    private String waktuEvent;
    private String venueEvent;
    private String alamatEvent;
    private Double harga;

    public TicketEvent(String namaEvent, String namaPemesan, String section, String seatNumber, String tanggalEvent, String waktuEvent, String venueEvent, String alamatEvent, Double harga) {
        this.namaEvent = namaEvent;
        this.namaPemesan = namaPemesan;
        this.section = section;
        this.seatNumber = seatNumber;
        this.tanggalEvent = tanggalEvent;
        this.waktuEvent = waktuEvent;
        this.venueEvent = venueEvent;
        this.alamatEvent = alamatEvent;
        this.harga = harga;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getTanggalEvent() {
        return tanggalEvent;
    }

    public void setTanggalEvent(String tanggalEvent) {
        this.tanggalEvent = tanggalEvent;
    }

    public String getWaktuEvent() {
        return waktuEvent;
    }

    public void setWaktuEvent(String waktuEvent) {
        this.waktuEvent = waktuEvent;
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

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }
}

