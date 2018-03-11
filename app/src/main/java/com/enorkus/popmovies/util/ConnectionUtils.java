package com.enorkus.popmovies.util;

import android.net.Uri;

import com.enorkus.popmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ConnectionUtils {
    private static final String MOVIE_DB_API_KEY = "SECRET";
    private static final String MOVIE_DB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String MOVIE_DB_BASE_ULR = "http://api.themoviedb.org/3/movie/";
    private static final String PARAM_API_KEY = "api_key";
    private static final String ENDPOINT_POPULAR = "popular";
    private static final String ENDPOINT_TOP_RATED = "top_rated";

    public static URL buildTopRatedMoviesURL() {
        return buildMoviesDBURL(ENDPOINT_TOP_RATED);
    }

    public static URL buildPopularMoviesURL() {
        return buildMoviesDBURL(ENDPOINT_POPULAR);
    }

    private static URL buildMoviesDBURL(String endpoint) {
        Uri uri = Uri.parse(MOVIE_DB_BASE_ULR).buildUpon()
                .appendPath(endpoint)
                .appendQueryParameter(PARAM_API_KEY, MOVIE_DB_API_KEY).build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String buildMoviePosterUrl(String imageName) {
        return MOVIE_DB_IMAGE_BASE_URL + imageName;
    }

    public static String getResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream stream = connection.getInputStream();
            Scanner scan = new Scanner(stream);
            scan.useDelimiter("\\A");
            if (scan.hasNext()) {
                return scan.next();
            }
            return null;
        } finally {
            connection.disconnect();
        }
    }
}
