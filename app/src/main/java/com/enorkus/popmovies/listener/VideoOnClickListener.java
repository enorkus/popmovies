package com.enorkus.popmovies.listener;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.enorkus.popmovies.util.ConnectionUtils;

public class VideoOnClickListener implements View.OnClickListener {

    private String key;

    public VideoOnClickListener(String key) {
        this.key = key;
    }

    @Override
    public void onClick(View view) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(ConnectionUtils.buildYoutubeTrailerURL(key)));
        try {
            view.getContext().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            view.getContext().startActivity(webIntent);
        }
    }
}
