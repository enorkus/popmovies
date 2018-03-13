package com.enorkus.popmovies;

import android.os.AsyncTask;

import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MovieDBQueryTask extends AsyncTask<URL, Void, String> {
    private final AsyncResponse output;

    public MovieDBQueryTask(AsyncResponse output) {
        this.output = output;
    }

    @Override
    protected String doInBackground(URL... urls) {
        try {
            return ConnectionUtils.getResponse(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(String response) {
        try {
            JSONObject results = new JSONObject(response);
            JSONArray resultsArray = results.getJSONArray("results");
            Movie[] movieArray = new Gson().fromJson(resultsArray.toString(), Movie[].class);
            List<Movie> movies = Arrays.asList(movieArray);
            output.getAsyncResponseOnFinish(movies);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
