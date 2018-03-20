package com.enorkus.popmovies.database;

import android.provider.BaseColumns;

public class MovieEntry implements BaseColumns {

    public static final String TABLE_NAME = "favorite_movies";
    public static final String COLUMN_MOVIE_ID = "movieId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_RELEASE_DATE = "releaseDate";
    public static final String COLUMN_POSTER = "poster";
    public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
    public static final String COLUMN_OVERVIEW = "overview";
}
