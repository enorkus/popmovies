package com.enorkus.popmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.enorkus.popmovies.R;
import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.listener.PosterOnClickListener;
import com.enorkus.popmovies.listener.state.MainActivityStateHolder;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(@NonNull Context context, @NonNull List<Movie> movies) {
        super(context, 0, movies);
        MainActivityStateHolder.movies = (ArrayList<Movie>) movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
        }

        ImageView IVmoviePosterItem = convertView.findViewById(R.id.IVmoviePosterItem);
        IVmoviePosterItem.setOnClickListener(new PosterOnClickListener(movie));
        Picasso.with(getContext())
                .load(ConnectionUtils.buildMoviePosterUrl(movie.getPoster()))
                .placeholder(R.drawable.movie_poster_placeholder)
                .error(R.drawable.movie_poster_error)
                .into(IVmoviePosterItem);
        return convertView;
    }
}
