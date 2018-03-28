package com.enorkus.popmovies.details;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.enorkus.popmovies.MovieDetailsActivity;
import com.enorkus.popmovies.R;
import com.enorkus.popmovies.entity.Movie;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private Movie movie;
    private Context ctx;

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public SectionPagerAdapter(FragmentManager fm, Movie movie, Context ctx) {
        super(fm);
        this.movie = movie;
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putParcelable(MovieDetailsActivity.EXTRA_MOVIE, movie);
                OverviewFragment overviewFragment = new OverviewFragment();
                overviewFragment.setArguments(bundle);
                return overviewFragment;
            case 1:
                bundle.putString(MovieDetailsActivity.EXTRA_MOVIE_ID, Integer.toString(movie.getId()));
                ReviewsFragment reviewsFragment = new ReviewsFragment();
                reviewsFragment.setArguments(bundle);
                return reviewsFragment;
            case 2:
                bundle.putString(MovieDetailsActivity.EXTRA_MOVIE_ID, Integer.toString(movie.getId()));
                VideosFragment videosFragment = new VideosFragment();
                videosFragment.setArguments(bundle);
                return videosFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return ctx.getResources().getString(R.string.header_overview);
            case 1:
                return ctx.getResources().getString(R.string.header_reviews);
            case 2:
                return ctx.getResources().getString(R.string.header_videos);
        }
        return null;
    }
}
