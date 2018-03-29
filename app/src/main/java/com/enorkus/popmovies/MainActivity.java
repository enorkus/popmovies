package com.enorkus.popmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    @BindView(R.id.LLnoInternetConnection)
    protected LinearLayout LLnoInternetConnection;
    @BindView(R.id.reloadBtn)
    protected Button reloadButton;

    private MovieDBQueryTask queryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        createLayout(savedInstanceState);
    }

    private void createLayout(Bundle savedInstanceState) {
        if(isOnline(this)) {
            if(savedInstanceState != null && savedInstanceState.getParcelableArrayList(SELECTED_MOVIES) != null) {
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
        } else {
            GVmoviePosters.setVisibility(View.GONE);
            LLnoInternetConnection.setVisibility(View.VISIBLE);
            reloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void getAsyncResponseOnFinish(List<?> response) {
        MovieAdapter adapter = new MovieAdapter(this, (List<Movie>) response);
        GVmoviePosters.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(isOnline(this)) {
            outState.putInt(BOTTOM_NAVIGATION_SELECTED_ID, bottomNavigation.getSelectedItemId());
            outState.putParcelableArrayList(SELECTED_MOVIES, MainActivityStateHolder.movies);
            outState.putString(SELECTED_SORT_ORDER, MainActivityStateHolder.sortOrder != null ? MainActivityStateHolder.sortOrder.name() : null);
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
