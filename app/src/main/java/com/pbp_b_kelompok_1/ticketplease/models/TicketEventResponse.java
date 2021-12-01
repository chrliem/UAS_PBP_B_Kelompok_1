package com.pbp_b_kelompok_1.ticketplease.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TicketEventResponse {

    private String message;

    @SerializedName("ticketevent")
    private List<TicketEvent> ticketEventList;

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public List<TicketEvent> getTicketEventList() {
        return ticketEventList;
    }

    public void setTicketEventList(List<TicketEvent> ticketEventList) {
        this.ticketEventList = ticketEventList;
    }
}
