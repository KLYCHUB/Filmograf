package com.example.filmograf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ListeFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private ArrayList<Movie> movies;
    private ArrayAdapter<Movie> adapter;
    private DatabaseHelper databaseHelper;
    private String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste, container, false);

        databaseHelper = new DatabaseHelper(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", null);

        // Kullanıcı email null ise LoginActivity'e yönlendirme
        if (userEmail == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            return view;
        }

        movies = new ArrayList<>();
        adapter = new MovieAdapter(getActivity(), movies);

        ListView listView = view.findViewById(R.id.movie_list);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        Button btnAddMovie = view.findViewById(R.id.btn_add_movie);
        btnAddMovie.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        loadUserMovies();

        return view;
    }

    private void loadUserMovies() {
        if (userEmail == null) {
            return;
        }

        Cursor cursor = databaseHelper.getUserMovies(userEmail);
        if (cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int ratingIndex = cursor.getColumnIndex("rating");
            int imageUriIndex = cursor.getColumnIndex("image_uri");

            do {
                String title = cursor.getString(titleIndex);
                String description = cursor.getString(descriptionIndex);
                float rating = cursor.getFloat(ratingIndex);
                String imageUriString = cursor.getString(imageUriIndex);
                Uri imageUri = Uri.parse(imageUriString);

                movies.add(new Movie(title, description, rating, imageUri));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            MovieDialog dialog = new MovieDialog();
            dialog.setTargetFragment(ListeFragment.this, 0);
            dialog.show(getParentFragmentManager(), "MovieDialog");
            dialog.setImageUri(imageUri);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_delete) {
            // Get the position of the clicked item from menuInfo
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;

            // Remove movie from list and database
            Movie movie = movies.remove(position);
            adapter.notifyDataSetChanged();

            databaseHelper.deleteMovie(movie.getTitle()); // Implement deleteMovie method in DatabaseHelper

            return true;
        }
        return super.onContextItemSelected(item);
    }


    public void addMovie(String title, String description, float rating, Uri imageUri) {
        movies.add(new Movie(title, description, rating, imageUri));
        adapter.notifyDataSetChanged();
        databaseHelper.insertMovie(title, description, rating, imageUri.toString(), userEmail);
    }
}
