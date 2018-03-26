package com.enorkus.popmovies.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.enorkus.popmovies.entity.Movie;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private Movie movie;

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public SectionPagerAdapter(FragmentManager fm, Movie movie) {
        super(fm);
        this.movie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
            case 0:
                bundle.putSerializable("movie", movie);
                OverviewFragment overviewFragment = new OverviewFragment();
                overviewFragment.setArguments(bundle);
                return overviewFragment;
            case 1:
                bundle.putString("movieID", Integer.toString(movie.getId()));
                ReviewsFragment reviewsFragment = new ReviewsFragment();
                reviewsFragment.setArguments(bundle);
                return reviewsFragment;
            case 2:
                bundle.putString("movieID", Integer.toString(movie.getId()));
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
                return "Overview";
            case 1:
                return "Reviews";
            case 2:
                return "Videos";
        }
        return null;
    }
}
