package com.enorkus.popmovies.asynctask;

import com.enorkus.popmovies.entity.Review;
import com.enorkus.popmovies.entity.Video;
import com.enorkus.popmovies.util.AsyncResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class VideosQueryTask extends MovieDBQueryTask {

    public VideosQueryTask(AsyncResponse output) {
        super(output);
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            JSONObject results = new JSONObject(response);
            JSONArray resultsArray = results.getJSONArray("results");
            Video[] videosArray = new Gson().fromJson(resultsArray.toString(), Video[].class);
            List<Video> videos = Arrays.asList(videosArray);
            getOutput().getAsyncResponseOnFinish(videos);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }
}
