package com.pbp_b_kelompok_1.ticketplease.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketMovieResponse {

    private String message;

    @SerializedName("ticketmovie")
    private List<TicketMovie> ticketMovieList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TicketMovie> getTicketMovieList() {
        return ticketMovieList;
    }

    public void setTicketMovieList(List<TicketMovie> ticketMovieList) {
        this.ticketMovieList = ticketMovieList;
    }
}
