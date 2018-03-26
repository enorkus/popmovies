package com.enorkus.popmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.enorkus.popmovies.R;
import com.enorkus.popmovies.entity.Video;
import com.enorkus.popmovies.listener.PosterOnClickListener;
import com.enorkus.popmovies.listener.VideoOnClickListener;
import com.enorkus.popmovies.util.ConnectionUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<Video> {
    public VideoAdapter(@NonNull Context context, @NonNull List<Video> videos) {
        super(context, 0, videos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Video video = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_video, parent, false);
        }

        ImageView IVmoviePosterItem = convertView.findViewById(R.id.IVtrailerThumbnail);
        IVmoviePosterItem.setOnClickListener(new VideoOnClickListener(video.getKey()));
        Picasso.with(getContext())
                .load(ConnectionUtils.buildYoutubeTrailerThumbnailURL(video.getKey()))
                .placeholder(R.drawable.movie_poster_placeholder)
                .error(R.drawable.movie_poster_error)
                .into(IVmoviePosterItem);

        return convertView;
    }
}
