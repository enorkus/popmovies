package com.enorkus.popmovies.details;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enorkus.popmovies.MovieDetailsActivity;
import com.enorkus.popmovies.R;
import com.enorkus.popmovies.data.MoviesContentProviderHelper;
import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.listener.FavoriteOnClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewFragment extends Fragment {

    @BindView(R.id.TVmovieTitle)
    protected TextView TVmovieTitle;
    @BindView(R.id.TVreleaseDate)
    protected TextView TVreleaseDate;
    @BindView(R.id.TVrating)
    protected TextView TVrating;
    @BindView(R.id.TVOverview)
    protected TextView TVoverview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        ButterKnife.bind(this, rootView);

        Movie movie = (Movie) getArguments().getParcelable(MovieDetailsActivity.EXTRA_MOVIE);
        TVmovieTitle.setText(movie.getTitle());
        TVreleaseDate.setText(movie.getReleaseDate());
        TVrating.setText(movie.getVoteAverage());
        TVoverview.setText(movie.getOverview());

        return rootView;
    }
}
