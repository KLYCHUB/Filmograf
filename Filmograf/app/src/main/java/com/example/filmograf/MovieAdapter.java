package com.example.filmograf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(@NonNull Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }

        Movie movie = getItem(position);

        ImageView movieImage = convertView.findViewById(R.id.movie_image);
        TextView movieTitle = convertView.findViewById(R.id.movie_title);
        TextView movieDescription = convertView.findViewById(R.id.movie_description);
        RatingBar movieRating = convertView.findViewById(R.id.movie_rating);

        if (movie != null) {
            movieImage.setImageURI(movie.getImageUri());
            movieTitle.setText(movie.getTitle());
            movieDescription.setText(movie.getDescription());
            movieRating.setRating(movie.getRating());
        }

        return convertView;
    }
}