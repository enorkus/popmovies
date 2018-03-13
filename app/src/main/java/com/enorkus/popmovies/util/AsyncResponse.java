package com.enorkus.popmovies.util;

import com.enorkus.popmovies.entity.Movie;

import java.util.List;

/**
 * Interface for moving AsyncTask output data to an Activity.
 */
public interface AsyncResponse {
    void getAsyncResponseOnFinish(List<Movie> response);
}