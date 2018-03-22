package com.enorkus.popmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.enorkus.popmovies.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.enorkus.popmovies.data.MovieContract.MovieEntry;

/**
 * Helper for preparing input/output data for content provider.
 */
public class MoviesContentProviderHelper {

    private Context ctx;

    public MoviesContentProviderHelper(Context ctx) {
        this.ctx = ctx;
    }

    public boolean isAlreadyFavoriteMovie(int id) {
        Uri uri = MovieEntry.FAVORITE_URI.buildUpon().appendPath(Integer.toString(id)).build();
        Cursor cursor = ctx.getContentResolver().query(uri, null, null, null, null);
        try {
            if (cursor.getCount() != 0) {
                return true;
            }
        } finally {
            cursor.close();
        }
        return false;
    }

    public void removeFavoriteMovie(int id) {
        Uri uri = MovieEntry.FAVORITE_URI.buildUpon().appendPath(Integer.toString(id)).build();
        ctx.getContentResolver().delete(uri, null, null);
    }

    public void addFavoriteMovie(Movie movie) {
        ContentValues movieCV = new ContentValues();
        movieCV.put(MovieEntry.COLUMN_ID, movie.getId());
        movieCV.put(MovieEntry.COLUMN_POSTER, movie.getPoster());
        movieCV.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
        movieCV.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        movieCV.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        movieCV.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        ctx.getContentResolver().insert(MovieEntry.FAVORITE_URI, movieCV);
    }

    public List<Movie> fetchAllFavoriteMovies() {
        List<Movie> movies = new ArrayList<>();
        Cursor cursor = ctx.getContentResolver().query(MovieEntry.FAVORITE_URI, null, null, null, null);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_ID));
                String poster = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER));
                String title = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE));
                String overview = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW));
                String releaseDate = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE));
                String voteAverage = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE));
                movies.add(new Movie(id, title, releaseDate, poster, voteAverage, overview));
            }
        } finally {
            cursor.close();
        }
        return movies;
    }
}
