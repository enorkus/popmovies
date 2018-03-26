package com.enorkus.popmovies;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.enorkus.popmovies.data.MoviesContentProviderHelper;
import com.enorkus.popmovies.details.SectionPagerAdapter;
import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.listener.FavoriteOnClickListener;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";

    @BindView(R.id.IVmoviePoster)
    protected ImageView IVmoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        setSupportActionBar((Toolbar) findViewById(R.id.movieDetailsToolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        getSupportActionBar().setTitle(movie.getTitle());

        Picasso.with(this)
                .load(ConnectionUtils.buildMoviePosterUrl(movie.getPoster()))
                .placeholder(R.drawable.movie_poster_placeholder)
                .error(R.drawable.movie_poster_error)
                .into(IVmoviePoster);

        //Tabs
        SectionPagerAdapter mSectionsPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), movie);

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
    }
}
