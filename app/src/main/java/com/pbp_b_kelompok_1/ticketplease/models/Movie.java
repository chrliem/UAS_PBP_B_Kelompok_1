package com.pbp_b_kelompok_1.ticketplease.models;

public class Movie {
    private String namaMovie;
    private String tanggalMovie;
    private String urlImage;
    private String sinopsis;
    private Double hargaMovie;

    public Movie(String namaMovie, String tanggalMovie, String urlImage, String sinopsis, Double hargaMovie) {
        this.namaMovie = namaMovie;
        this.tanggalMovie = tanggalMovie;
        this.urlImage = urlImage;
        this.sinopsis = sinopsis;
        this.hargaMovie = hargaMovie;
    }

    public String getTanggalMovie() {
        return tanggalMovie;
    }

    public void setTanggalMovie(String tanggalMovie) {
        this.tanggalMovie = tanggalMovie;
    }

    public String getNamaMovie() {
        return namaMovie;
    }

    public void setNamaMovie(String namaMovie) {
        this.namaMovie = namaMovie;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Double getHargaMovie() {
        return hargaMovie;
    }

    public void setHargaMovie(Double hargaMovie) {
        this.hargaMovie = hargaMovie;
    }
}
