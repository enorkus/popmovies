package com.enorkus.popmovies.listener.state;

import com.enorkus.popmovies.entity.Movie;

import java.util.ArrayList;

public class MainActivityStateHolder {

    private MainActivityStateHolder() {}

    public static ArrayList<Movie> movies;
    public static MovieSortOrder sortOrder;

    public static enum MovieSortOrder {
        POPULAR,
        RATING,
        FAVORITE;
    }
}
