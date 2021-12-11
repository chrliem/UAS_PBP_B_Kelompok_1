package com.pbp_b_kelompok_1.ticketplease.models;

import java.util.ArrayList;

public class DummyEvent {

    public ArrayList<Event> dataEvent;

    public DummyEvent(){
        dataEvent = new ArrayList();
        dataEvent.add(event1);
        dataEvent.add(event2);
        dataEvent.add(event3);
        dataEvent.add(event4);
    }

    public static final Event event1 = new Event(
            "5 Juni 2022",
            "Pentas Kebudayaan Solo",
            "https://kabarapik.com/wp-content/uploads/2020/12/Pentas-Sendratari-Ramayana-di-Surakarta.jpg",
            "Graha Saba Buana",
            "Jalan Letjen Suprapto No.80-B, Sumber, Kec. Banjarsari, Kota Surakarta, Jawa Tengah 57137",
            50000.00);
    public static final Event event2 = new Event(
            "25 Desember 2021",
            "Konser Musik Rock",
            "https://i1.wp.com/musikeras.com/wp-content/uploads/2018/05/AMBANG-CHRIST-edit.png?fit=900%2C486&quality=95&ssl=1",
            "GOR Sriwedari",
            "Jalan Bhayangkara No.5, Sriwedari, Kec. Laweyan, Kota Surakarta, Jawa Tengah 57141",
            75000.00);
    public static final Event event3 = new Event(
            "11 November 2021",
            "Konser Jazz Night Live",
            "https://www.cultura.id/wp-content/uploads/2020/04/Musik_Jazz_di_Indonesia-Cultura.jpg",
            "GOR Sritex Arena",
            "Jalan Abiyoso No. 21, Sriwedari, Kec. Laweyan, Kota Surakarta, Jawa Tengah 57141",
            100000.00);
    public static final Event event4 = new Event(
            "1 Januari 2022",
            "Pentas Seni Budaya",
            "https://surakarta.go.id/wp-content/uploads/2019/06/KOTA-KREATIF-1200x720.jpg",
            "Balai Kota Surakarta",
            "Jalan Jend. Sudirman No.2, Kp. Baru, Kec. Ps. Kliwon, Kota Surakarta, Jawa Tengah 57133",
            60000.00);
}
