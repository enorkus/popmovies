package com.enorkus.popmovies;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.enorkus.popmovies.adapter.MovieAdapter;
import com.enorkus.popmovies.asynctask.MovieDBQueryTask;
import com.enorkus.popmovies.asynctask.MoviesQueryTask;
import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.listener.BottomNavigationItemSelectListener;
import com.enorkus.popmovies.listener.state.MainActivityStateHolder;
import com.enorkus.popmovies.ui.BottomNavigationViewBehavior;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private static final String BOTTOM_NAVIGATION_SELECTED_ID = "bottomNavSelectedID";
    private static final String SELECTED_MOVIES = "selectedMovies";
    private static final String SELECTED_SORT_ORDER = "selectedSortOrder";

    @BindView(R.id.GVmoviePosters)
    protected GridView GVmoviePosters;
    @BindView(R.id.bottomNavigation)
    protected BottomNavigationView bottomNavigation;

    private MovieDBQueryTask queryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            bottomNavigation.setSelectedItemId(savedInstanceState.getInt(BOTTOM_NAVIGATION_SELECTED_ID));
            getAsyncResponseOnFinish(savedInstanceState.getParcelableArrayList(SELECTED_MOVIES));
            MainActivityStateHolder.sortOrder = MainActivityStateHolder.MovieSortOrder.valueOf(savedInstanceState.getString(SELECTED_SORT_ORDER));
        } else {
            queryTask = new MoviesQueryTask(this);
            queryTask.execute(ConnectionUtils.buildPopularMoviesURL());
            MainActivityStateHolder.sortOrder = MainActivityStateHolder.MovieSortOrder.POPULAR;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationItemSelectListener(this, GVmoviePosters));
    }

    @Override
    public void getAsyncResponseOnFinish(List<?> response) {
        MovieAdapter adapter = new MovieAdapter(this, (List<Movie>) response);
        GVmoviePosters.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BOTTOM_NAVIGATION_SELECTED_ID, bottomNavigation.getSelectedItemId());
        outState.putParcelableArrayList(SELECTED_MOVIES, MainActivityStateHolder.movies);
        outState.putString(SELECTED_SORT_ORDER, MainActivityStateHolder.sortOrder.name());
    }
}
