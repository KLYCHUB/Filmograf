package com.example.filmograf;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MovieDialog extends DialogFragment {

    private Uri imageUri;
    private EditText titleInput;
    private EditText descriptionInput;
    private RatingBar ratingBar;

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_movie, null);

        titleInput = view.findViewById(R.id.movie_title_input);
        descriptionInput = view.findViewById(R.id.movie_description_input);
        ratingBar = view.findViewById(R.id.movie_rating_bar);

        builder.setView(view)
                .setTitle("Yeni Film Ekle")
                .setNegativeButton("İptal", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Ekle", (dialog, which) -> {
                    String title = titleInput.getText().toString();
                    String description = descriptionInput.getText().toString();
                    float rating = ratingBar.getRating();

                    ListeFragment fragment = (ListeFragment) getTargetFragment();
                    if (fragment != null) {
                        fragment.addMovie(title, description, rating, imageUri);
                    }
                });

        return builder.create();
    }
}