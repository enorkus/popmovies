package com.enorkus.popmovies.listener;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Toast;

import com.enorkus.popmovies.R;
import com.enorkus.popmovies.data.MoviesContentProviderHelper;
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
        MoviesContentProviderHelper contentHelper = new MoviesContentProviderHelper(view.getContext());
        if(contentHelper.isAlreadyFavoriteMovie(movie.getId())) {
            contentHelper.removeFavoriteMovie(movie.getId());
            fabFavorite.setImageResource(R.drawable.fav_star_off);
            Toast.makeText(view.getContext(), "Successfully removed from favorites", Toast.LENGTH_SHORT).show();
        } else {
            contentHelper.addFavoriteMovie(movie);
            fabFavorite.setImageResource(R.drawable.fav_star_on_yell);
            Toast.makeText(view.getContext(), "Successfully saved favorited movie", Toast.LENGTH_SHORT).show();
        }
    }
}
