package com.enorkus.popmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.enorkus.popmovies.R;
import com.enorkus.popmovies.entity.Review;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(@NonNull Context context, List<Review> reviews) {
        super(context, 0, reviews);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Review review = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_review, parent, false);
        }

        TextView reviewAuthor = convertView.findViewById(R.id.TVreviewAuthor);
        reviewAuthor.setText(review.getAuthor());
        TextView reviewContent = convertView.findViewById(R.id.TVreviewContent);
        reviewContent.setText(review.getContent());

        return convertView;
    }
}
