package com.enorkus.popmovies.listener;

import android.content.ContentValues;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import com.enorkus.popmovies.R;
import com.enorkus.popmovies.database.MovieContract;
import com.enorkus.popmovies.database.MoviesDBHelper;
import com.enorkus.popmovies.entity.Movie;

import static com.enorkus.popmovies.database.MovieContract.*;

public class FavoriteOnClickListener implements View.OnClickListener {

    private Movie movie;
    private FloatingActionButton fabFavorite;

    public FavoriteOnClickListener(Movie movie) {
        this.movie = movie;
    }

    public FavoriteOnClickListener(Movie movie, FloatingActionButton fabFavorite) {
        this.movie = movie;
        this.fabFavorite = fabFavorite;
    }

    @Override
    public void onClick(View view) {
        MoviesDBHelper dbHelper = new MoviesDBHelper(view.getContext());
        if(dbHelper.favoriteMovieExists(movie.getId())) {
            dbHelper.deleteMovie(movie.getId());
            fabFavorite.setImageResource(R.drawable.fav_star_off);
            Toast.makeText(view.getContext(), "Successfully removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues contentValues = createMovieContentValues(movie);
            Uri uri = view.getContext().getContentResolver().insert(MovieEntry.FAVORITE_URI, contentValues);
            fabFavorite.setImageResource(R.drawable.fav_star_on_yell);
            Toast.makeText(view.getContext(), "Successfully saved favorited movie", Toast.LENGTH_SHORT).show();
        }
    }

    private ContentValues createMovieContentValues(Movie movie) {
        ContentValues movieCV = new ContentValues();
        movieCV.put(MovieEntry.COLUMN_ID, movie.getId());
        movieCV.put(MovieEntry.COLUMN_POSTER, movie.getPoster());
        movieCV.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
        movieCV.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        movieCV.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        movieCV.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        return movieCV;
    }
}
