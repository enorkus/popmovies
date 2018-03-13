package com.enorkus.popmovies.listener;

import android.content.Intent;
import android.view.View;

import com.enorkus.popmovies.MovieDetailsActivity;
import com.enorkus.popmovies.entity.Movie;

public class PosterOnClickListener implements View.OnClickListener {

    private final Movie movie;

    public PosterOnClickListener(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie);
        view.getContext().startActivity(intent);
    }
}
