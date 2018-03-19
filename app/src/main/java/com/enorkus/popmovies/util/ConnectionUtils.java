package com.enorkus.popmovies.util;

import android.net.Uri;

import com.enorkus.popmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ConnectionUtils {
    private static final String MOVIE_DB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342";
    private static final String MOVIE_DB_BASE_ULR = "http://api.themoviedb.org/3/movie/";
    private static final String PARAM_API_KEY = "api_key";
    private static final String ENDPOINT_POPULAR = "popular";
    private static final String ENDPOINT_TOP_RATED = "top_rated";
    private static final String ENDPOINT_VIDEOS = "videos";
    private static final String ENDPOINT_REVIEWS = "reviews";
    private static final String JSON_STREAM_DELIMITER = "\\A";

    public static URL buildTopRatedMoviesURL() {
        return buildMoviesDBURL(ENDPOINT_TOP_RATED);
    }

    public static URL buildPopularMoviesURL() {
        return buildMoviesDBURL(ENDPOINT_POPULAR);
    }

    public static String buildMoviePosterUrl(String imageName) {
        return MOVIE_DB_IMAGE_BASE_URL + imageName;
    }

    public static URL buildMovieVideosURL(String id) {
        return buildMoviesDBURL(id, ENDPOINT_VIDEOS);
    }

    public static URL buildMovieReviewsURL(String id) {
        return buildMoviesDBURL(id, ENDPOINT_REVIEWS);
    }

    private static URL buildMoviesDBURL(String id, String endpoint) {
        Uri uri = Uri.parse(MOVIE_DB_BASE_ULR).buildUpon()
                .appendPath(id)
                .appendPath(endpoint)
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.TheMovieDBAPIKey).build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static URL buildMoviesDBURL(String endpoint) {
        Uri uri = Uri.parse(MOVIE_DB_BASE_ULR).buildUpon()
                .appendPath(endpoint)
                .appendQueryParameter(PARAM_API_KEY, BuildConfig.TheMovieDBAPIKey).build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream stream = connection.getInputStream();
            Scanner scan = new Scanner(stream);
            scan.useDelimiter(JSON_STREAM_DELIMITER);
            if (scan.hasNext()) {
                return scan.next();
            }
            return null;
        } finally {
            connection.disconnect();
        }
    }
}
