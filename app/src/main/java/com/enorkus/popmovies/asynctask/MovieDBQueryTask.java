package com.enorkus.popmovies.asynctask;

import android.os.AsyncTask;

import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.entity.Review;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MovieDBQueryTask extends AsyncTask<URL, Void, String> {
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

    protected AsyncResponse getOutput() {
        return output;
    }
}
