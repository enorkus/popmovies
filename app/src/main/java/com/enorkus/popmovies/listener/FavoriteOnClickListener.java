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

    public FavoriteOnClickListener(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void onClick(View view) {
        MoviesDBHelper dbHelper = new MoviesDBHelper(view.getContext());
        dbHelper.saveMovie(movie);
        Toast.makeText(view.getContext(), "Successfully saved favorited movie", Toast.LENGTH_SHORT).show();
        FloatingActionButton fabFavorite = view.findViewById(R.id.fabFavorite);
        fabFavorite.setImageResource(R.drawable.fav_star_on_yell);
    }
}
