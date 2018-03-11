package com.enorkus.popmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.enorkus.popmovies.util.MovieAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private MovieDBQueryTask queryTask;
    //booleans to avoid querying API when sorting selection is same as current.
    private boolean isSortedByPopular;
    private boolean isSortedByRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queryTask = new MovieDBQueryTask(this);
        queryTask.execute(ConnectionUtils.buildPopularMoviesURL());
        isSortedByPopular = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort_by) {
            return true;
        } else if(id == R.id.menuSortPopular && !isSortedByPopular) {
            queryTask = new MovieDBQueryTask(this);
            queryTask.execute(ConnectionUtils.buildPopularMoviesURL());
            isSortedByRating = false;
            isSortedByPopular = true;
            return true;
        } else if(id == R.id.menuSortRating && !isSortedByRating) {
            queryTask = new MovieDBQueryTask(this);
            queryTask.execute(ConnectionUtils.buildTopRatedMoviesURL());
            isSortedByPopular = false;
            isSortedByRating = true;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getAsyncResponseOnFinish(List<Movie> response) {
        MovieAdapter adapter = new MovieAdapter(this, response);
        GridView gridView = findViewById(R.id.moviesGV);
        gridView.setAdapter(adapter);
    }
}
