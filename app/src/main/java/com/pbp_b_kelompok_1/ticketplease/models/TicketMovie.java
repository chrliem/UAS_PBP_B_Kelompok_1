package com.pbp_b_kelompok_1.ticketplease.models;
public class TicketMovie {
    private int kodeTiketMovie;
    private String namaMovie;
    private String namaPemesan;
    private String seatNumber;
    private String tanggalMovie;
    private String waktuMovie;
    private String sinopsis;
    private Double harga;

    public TicketMovie(String namaMovie, String namaPemesan, String seatNumber, String tanggalMovie, String waktuMovie, String sinopsis, Double harga) {
        this.namaMovie = namaMovie;
        this.namaPemesan = namaPemesan;
        this.seatNumber = seatNumber;
        this.tanggalMovie = tanggalMovie;
        this.waktuMovie = waktuMovie;
        this.sinopsis = sinopsis;
        this.harga = harga;
    }

    public int getKodeTiket() {
        return kodeTiketMovie;
    }

    public void setKodeTiket(int kodeTiketMovie) {
        this.kodeTiketMovie = kodeTiketMovie;
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

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }
}
