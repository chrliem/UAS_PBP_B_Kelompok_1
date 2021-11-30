package com.pbp_b_kelompok_1.ticketplease.models;
public class TicketMovie {
    private int kodeTiket;
    private String namaMovie;
    private String namaPemesan;
    private String seatNumber;
    private String tanggalMovie;
    private String waktuMovie;
    private Double harga;

    public TicketMovie(int kodeTiket, String namaMovie, String namaPemesan, String seatNumber, String tanggalMovie, String waktuMovie, Double harga) {
        this.kodeTiket = kodeTiket;
        this.namaMovie = namaMovie;
        this.namaPemesan = namaPemesan;
        this.seatNumber = seatNumber;
        this.tanggalMovie = tanggalMovie;
        this.waktuMovie = waktuMovie;
        this.harga = harga;
    }

    public int getKodeTiket() {
        return kodeTiket;
    }

    public void setKodeTiket(int kodeTiket) {
        this.kodeTiket = kodeTiket;
    }

    public String getNamaMovie() {
        return namaMovie;
    }

    public void setNamaMovie(String namaMovie) {
        this.namaMovie = namaMovie;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getTanggalMovie() {
        return tanggalMovie;
    }

    public void setTanggalMovie(String tanggalMovie) {
        this.tanggalMovie = tanggalMovie;
    }

    public String getWaktuMovie() {
        return waktuMovie;
    }

    public void setWaktuMovie(String waktuMovie) {
        this.waktuMovie = waktuMovie;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }
}
