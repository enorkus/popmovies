package com.enorkus.popmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public static final String AUTHORITY = "com.enorkus.popmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVORITE = "favorite";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri FAVORITE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String TABLE_NAME = "favorite_movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_OVERVIEW = "overview";
    }
}
