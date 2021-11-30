package com.pbp_b_kelompok_1.ticketplease.models;

public class Movie {
    private String namaMovie;
    private String urlImage;
    private String sinopsis;
    private Double hargaMovie;

    public Movie(String namaMovie, String urlImage, String sinopsis, Double hargaMovie) {
        this.namaMovie = namaMovie;
        this.urlImage = urlImage;
        this.sinopsis = sinopsis;
        this.hargaMovie = hargaMovie;
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
