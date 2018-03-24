package com.enorkus.popmovies;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.enorkus.popmovies.data.MoviesContentProviderHelper;
import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.listener.BottomNavigationItemSelectListener;
import com.enorkus.popmovies.ui.BottomNavigationViewBehavior;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.enorkus.popmovies.util.MovieAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

//    @BindView(R.id.bottomNavigation)
//    protected BottomNavigationView bottomNavigation;
    @BindView(R.id.GVmoviePosters)
    protected GridView GVmoviePosters;

    private MovieDBQueryTask queryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        queryTask = new MovieDBQueryTask(this);
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
    public void getAsyncResponseOnFinish(List<Movie> response) {
        MovieAdapter adapter = new MovieAdapter(this, response);
        GVmoviePosters.setAdapter(adapter);
    }
}
