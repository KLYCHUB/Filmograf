package com.example.filmograf;

import android.net.Uri;

public class Movie {
    private String title;
    private String description;
    private float rating;
    private Uri imageUri;

    public Movie(String title, String description, float rating, Uri imageUri) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}