package com.pbp_b_kelompok_1.ticketplease.models;

import java.util.ArrayList;
import java.util.Locale;

public class DummyMovie {

    public ArrayList<Movie> dataMovie;

    public DummyMovie(){
        dataMovie = new ArrayList<>();
        dataMovie.add(movie1);
        dataMovie.add(movie2);
        dataMovie.add(movie3);
        dataMovie.add(movie4);
    }

    public static final Movie movie1 = new Movie(
            "Avengers: Endgame",
            "5 Juni 2022",
            "https://m.media-amazon.com/images/M/MV5BMTc5MDE2ODcwNV5BMl5BanBnXkFtZTgwMzI2NzQ2NzM@._V1_.jpg",
            "After Thanos, an intergalactic warlord, disintegrates half of the universe, the Avengers must reunite and assemble again to reinvigorate their trounced allies and restore balance.",
            30000.00);

    public static final Movie movie2 = new Movie(
            "Train to Busan",
            "25 Desember 2021",
            "https://www.wellgousa.com/sites/default/files/styles/key_art_poster/public/2018-05/406x600_38.jpg?itok=Iqb3mvDq",
            "Seok-woo and his daughter are on a train to Busan on the latter's birthday to see his wife. However, the journey turns into a nightmare when they are trapped amidst a zombie outbreak in South Korea.",
            30000.00);

    public static final Movie movie3 = new Movie(
            "Fast & Furious",
            "1 Januari 2022",
            "https://ae01.alicdn.com/kf/HTB1Z9wAejgy_uJjSZKzq6z_jXXaG.jpg",
            "Fast & Furious is a media franchise centered on a series of action films that are largely concerned with illegal street racing, heists, spies and family. The franchise also includes short films, a television series, live shows, video games and theme park attractions. It is distributed by Universal Pictures.",
            30000.00);

    public static final Movie movie4 = new Movie(
            "No Mercy",
            "11 Januari 2022",
            "https://m.media-amazon.com/images/M/MV5BYTNmNjNlYjItNzZhNi00OTk0LWJjNWUtY2I5M2RlZDRhZDI1XkEyXkFqcGdeQXVyNzEyMDQ1MDA@._V1_SY1000_CR0,0,701,1000_AL_.jpg",
            "When Eun-hye doesn't come home from school, all the alarm bells go off for her older sister, In-ae. She tries to find her own way to save her sister.",
            30000.00);
}
