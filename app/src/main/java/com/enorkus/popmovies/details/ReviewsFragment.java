package com.enorkus.popmovies.details;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.enorkus.popmovies.MovieDetailsActivity;
import com.enorkus.popmovies.adapter.ReviewAdapter;
import com.enorkus.popmovies.asynctask.MovieDBQueryTask;
import com.enorkus.popmovies.R;
import com.enorkus.popmovies.asynctask.MoviesQueryTask;
import com.enorkus.popmovies.asynctask.ReviewsQueryTask;
import com.enorkus.popmovies.entity.Review;
import com.enorkus.popmovies.util.AsyncResponse;
import com.enorkus.popmovies.util.ConnectionUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsFragment extends Fragment implements AsyncResponse {

    @BindView(R.id.LVreviews)
    protected ListView LVreviews;
    @BindView(R.id.TVnoMovieReviews)
    protected TextView TVnoMovieReviews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);
        ButterKnife.bind(this, rootView);

        String movieID  = getArguments().getString(MovieDetailsActivity.EXTRA_MOVIE_ID);
        MovieDBQueryTask queryTask = new ReviewsQueryTask(this);
        queryTask.execute(ConnectionUtils.buildMovieReviewsURL(movieID));

        return rootView;
    }

    @Override
    public void getAsyncResponseOnFinish(List<?> response) {
        if(response != null && !response.isEmpty()) {
            ReviewAdapter adapter = new ReviewAdapter(this.getContext(), (List<Review>)response);
            LVreviews.setAdapter(adapter);
            TVnoMovieReviews.setVisibility(View.GONE);
        } else {
            TVnoMovieReviews.setVisibility(View.VISIBLE);
        }

    }
}
