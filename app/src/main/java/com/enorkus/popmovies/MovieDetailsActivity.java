package com.enorkus.popmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";

    @BindView(R.id.IVmoviePoster)
    protected ImageView IVmoviePoster;
    @BindView(R.id.TVmovieTitle)
    protected TextView TVmovieTitle;
    @BindView(R.id.TVreleaseDate)
    protected TextView TVreleaseDate;
    @BindView(R.id.TVrating)
    protected TextView TVrating;
    @BindView(R.id.TVOverview)
    protected TextView TVoverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Picasso.with(this)
                .load(ConnectionUtils.buildMoviePosterUrl(movie.getPoster()))
                .placeholder(R.drawable.movie_poster_placeholder)
                .error(R.drawable.movie_poster_error)
                .into(IVmoviePoster);

        TVmovieTitle.setText(movie.getTitle());
        TVreleaseDate.setText(movie.getReleaseDate());
        TVrating.setText(movie.getVoteAverage());
        TVoverview.setText(movie.getOverview());
    }
}
