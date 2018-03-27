package com.enorkus.popmovies.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.GridView;

import com.enorkus.popmovies.R;
import com.enorkus.popmovies.adapter.MovieAdapter;
import com.enorkus.popmovies.asynctask.MovieDBQueryTask;
import com.enorkus.popmovies.asynctask.MoviesQueryTask;
import com.enorkus.popmovies.data.MoviesContentProviderHelper;
import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.listener.state.MainActivityStateHolder;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;

import java.util.List;

public class BottomNavigationItemSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener, AsyncResponse {

    private MovieDBQueryTask queryTask;
    private GridView GVmoviePosters;
    private Context ctx;

    public BottomNavigationItemSelectListener(Context ctx, GridView GVmoviePosters) {
        this.ctx = ctx;
        this.GVmoviePosters = GVmoviePosters;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuSortPopular && !MainActivityStateHolder.sortOrder.equals(MainActivityStateHolder.MovieSortOrder.POPULAR)) {
            queryTask = new MoviesQueryTask(this);
            queryTask.execute(ConnectionUtils.buildPopularMoviesURL());
            MainActivityStateHolder.sortOrder = MainActivityStateHolder.MovieSortOrder.POPULAR;
            return true;
        } else if(id == R.id.menuSortRating && !MainActivityStateHolder.sortOrder.equals(MainActivityStateHolder.MovieSortOrder.RATING)) {
            queryTask = new MoviesQueryTask(this);
            queryTask.execute(ConnectionUtils.buildTopRatedMoviesURL());
            MainActivityStateHolder.sortOrder = MainActivityStateHolder.MovieSortOrder.RATING;
            return true;
        } else if(id == R.id.menuFavorites && !MainActivityStateHolder.sortOrder.equals(MainActivityStateHolder.MovieSortOrder.FAVORITE)) {
            MoviesContentProviderHelper contentHelper = new MoviesContentProviderHelper(ctx);
            MovieAdapter adapter = new MovieAdapter(ctx, contentHelper.fetchAllFavoriteMovies());
            GVmoviePosters.setAdapter(adapter);
            MainActivityStateHolder.sortOrder = MainActivityStateHolder.MovieSortOrder.FAVORITE;
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
