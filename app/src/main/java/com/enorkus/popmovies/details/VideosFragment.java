package com.enorkus.popmovies.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.enorkus.popmovies.R;
import com.enorkus.popmovies.adapter.VideoAdapter;
import com.enorkus.popmovies.asynctask.MovieDBQueryTask;
import com.enorkus.popmovies.asynctask.VideosQueryTask;
import com.enorkus.popmovies.entity.Video;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideosFragment extends Fragment implements AsyncResponse {

    @BindView(R.id.LVvideos)
    protected ListView LVvideos;
    @BindView(R.id.TVnoMovieVideos)
    protected TextView TVnoMovieVideos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_videos, container, false);
        ButterKnife.bind(this, rootView);

        String movieID  = getArguments().getString("movieID");
        MovieDBQueryTask queryTask = new VideosQueryTask(this);
        queryTask.execute(ConnectionUtils.buildMovieVideosURL(movieID));

        return rootView;
    }

    @Override
    public void getAsyncResponseOnFinish(List<?> response) {
        if(response != null && !response.isEmpty()) {
            VideoAdapter adapter = new VideoAdapter(this.getContext(), (List<Video>) response);
            LVvideos.setAdapter(adapter);
        } else {
            TVnoMovieVideos.setVisibility(View.VISIBLE);
        }
    }
}
