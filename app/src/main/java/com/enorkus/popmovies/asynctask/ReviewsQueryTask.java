package com.enorkus.popmovies.asynctask;

import com.enorkus.popmovies.entity.Movie;
import com.enorkus.popmovies.entity.Review;
import com.enorkus.popmovies.util.AsyncResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewsQueryTask extends MovieDBQueryTask {
    public ReviewsQueryTask(AsyncResponse output) {
        super(output);
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            JSONObject results = new JSONObject(response);
            JSONArray resultsArray = results.getJSONArray(RESULTS);
            Review[] reviewsArray = new Gson().fromJson(resultsArray.toString(), Review[].class);
            List<Review> reviews = new ArrayList<>(Arrays.asList(reviewsArray));
            getOutput().getAsyncResponseOnFinish(reviews);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }
}
