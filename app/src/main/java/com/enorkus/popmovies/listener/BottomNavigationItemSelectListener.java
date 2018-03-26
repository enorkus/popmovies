package com.enorkus.popmovies.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.GridView;

import com.enorkus.popmovies.asynctask.MovieDBQueryTask;
import com.enorkus.popmovies.R;
import com.enorkus.popmovies.asynctask.MoviesQueryTask;
import com.enorkus.popmovies.data.MoviesContentProviderHelper;
import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.enorkus.popmovies.adapter.MovieAdapter;

import java.util.List;

public class BottomNavigationItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener, AsyncResponse {

    private MovieDBQueryTask queryTask;
    private GridView GVmoviePosters;
    private Context ctx;

    //booleans to avoid querying API when sorting selection is same as current.
    private boolean isSortedByPopular;
    private boolean isSortedByRating;
    private boolean isFavoriteMovies;

    public BottomNavigationItemSelectListener(Context ctx, GridView GVmoviePosters) {
        this.ctx = ctx;
        this.GVmoviePosters = GVmoviePosters;
        isSortedByPopular = true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuSortPopular && !isSortedByPopular) {
            queryTask = new MoviesQueryTask(this);
            queryTask.execute(ConnectionUtils.buildPopularMoviesURL());
            isSortedByRating = false;
            isSortedByPopular = true;
            isFavoriteMovies = false;
            return true;
        } else if(id == R.id.menuSortRating && !isSortedByRating) {
            queryTask = new MoviesQueryTask(this);
            queryTask.execute(ConnectionUtils.buildTopRatedMoviesURL());
            isSortedByRating = true;
            isSortedByPopular = false;
            isFavoriteMovies = false;
            return true;
        } else if(id == R.id.menuFavorites && !isFavoriteMovies) {
            MoviesContentProviderHelper contentHelper = new MoviesContentProviderHelper(ctx);
            MovieAdapter adapter = new MovieAdapter(ctx, contentHelper.fetchAllFavoriteMovies());
            GVmoviePosters.setAdapter(adapter);
            isSortedByRating = false;
            isSortedByPopular = false;
            isFavoriteMovies = true;
            return true;
        }
        return true;
    }

    @Override
    public void getAsyncResponseOnFinish(List<?> response) {
        MovieAdapter adapter = new MovieAdapter(ctx, (List<Movie>) response);
        GVmoviePosters.setAdapter(adapter);
    }
}
