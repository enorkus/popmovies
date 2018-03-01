package com.enorkus.popmovies;

import android.os.AsyncTask;
import android.widget.TextView;

import com.enorkus.popmovies.util.ConnectionUtils;

import java.io.IOException;
import java.net.URL;

public class MovieDBQueryTask extends AsyncTask<URL, Void, String>{
    private TextView rootView;

    public MovieDBQueryTask(TextView rootView) {
        this.rootView = rootView;
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

    @Override
    protected void onPostExecute(String s) {
        rootView.setText(s);
        super.onPostExecute(s);
    }
}
