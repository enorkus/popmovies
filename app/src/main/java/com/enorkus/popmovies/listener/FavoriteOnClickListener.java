package com.enorkus.popmovies.listener;

import android.content.ContentValues;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import com.enorkus.popmovies.R;
import com.enorkus.popmovies.database.MovieEntry;
import com.enorkus.popmovies.database.MoviesDBHelper;
import com.enorkus.popmovies.entity.Movie;

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
            dbHelper.saveMovie(movie);
            fabFavorite.setImageResource(R.drawable.fav_star_on_yell);
            Toast.makeText(view.getContext(), "Successfully saved favorited movie", Toast.LENGTH_SHORT).show();
        }
    }
}
