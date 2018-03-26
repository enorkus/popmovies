package com.enorkus.popmovies;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.GridView;

import com.enorkus.popmovies.asynctask.MovieDBQueryTask;
import com.enorkus.popmovies.asynctask.MoviesQueryTask;
import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.listener.BottomNavigationItemSelectListener;
import com.enorkus.popmovies.ui.BottomNavigationViewBehavior;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.enorkus.popmovies.adapter.MovieAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    @BindView(R.id.GVmoviePosters)
    protected GridView GVmoviePosters;

    private MovieDBQueryTask queryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        queryTask = new MoviesQueryTask(this);
        queryTask.execute(ConnectionUtils.buildPopularMoviesURL());

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationItemSelectListener(this, GVmoviePosters));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void getAsyncResponseOnFinish(List<?> response) {
        MovieAdapter adapter = new MovieAdapter(this, (List<Movie>) response);
        GVmoviePosters.setAdapter(adapter);
    }
}
