package com.example.filmograf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfilFragment extends Fragment {

    private Button logoutButton;
    private SharedPreferences sharedPreferences;

    DatabaseHelper myDb;
    EditText editEmail;
    Button btnSearch;
    Button btnShowAll;
    RecyclerView recyclerView;
    EmailAdapter emailAdapter;
    List<String> emailList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        // DatabaseHelper instance
        myDb = new DatabaseHelper(getContext());

        // Initialize views
        editEmail = view.findViewById(R.id.editEmail);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnShowAll = view.findViewById(R.id.btnShowAll);
        recyclerView = view.findViewById(R.id.recyclerView);
        logoutButton = view.findViewById(R.id.btnLogout);

        sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);

        // Initialize email list and adapter
        emailList = new ArrayList<>();
        emailAdapter = new EmailAdapter(emailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(emailAdapter);

        // Set OnClickListener for the search button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                searchEmail(email);
            }
        });

        // Set OnClickListener for the show all button
        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllEmails();
            }
        });

        // Set OnClickListener for the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void searchEmail(String email) {
        Cursor cursor = myDb.getEmailData(email);
        emailList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            int emailColumnIndex = cursor.getColumnIndex("email");
            int firstNameColumnIndex = cursor.getColumnIndex("first_name");
            int lastNameColumnIndex = cursor.getColumnIndex("last_name");

            if (emailColumnIndex != -1 && firstNameColumnIndex != -1 && lastNameColumnIndex != -1) {
                do {
                    String emailResult = cursor.getString(emailColumnIndex);
                    String firstNameResult = cursor.getString(firstNameColumnIndex);
                    String lastNameResult = cursor.getString(lastNameColumnIndex);
                    emailList.add("Email: " + emailResult + ", Name: " + firstNameResult + " " + lastNameResult);
                } while (cursor.moveToNext());
            }
        }
        emailAdapter.notifyDataSetChanged();

        if (emailList.isEmpty()) {
            Toast.makeText(getContext(), "Email not found!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Email found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAllEmails() {
        Cursor cursor = myDb.getAllUsers();
        emailList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            int emailColumnIndex = cursor.getColumnIndex("email");
            int firstNameColumnIndex = cursor.getColumnIndex("first_name");
            int lastNameColumnIndex = cursor.getColumnIndex("last_name");

            if (emailColumnIndex != -1 && firstNameColumnIndex != -1 && lastNameColumnIndex != -1) {
                do {
                    String emailResult = cursor.getString(emailColumnIndex);
                    String firstNameResult = cursor.getString(firstNameColumnIndex);
                    String lastNameResult = cursor.getString(lastNameColumnIndex);
                    emailList.add("Email: " + emailResult + ", Name: " + firstNameResult + " " + lastNameResult);
                } while (cursor.moveToNext());
            }
        }
        emailAdapter.notifyDataSetChanged();

        if (emailList.isEmpty()) {
            Toast.makeText(getContext(), "No emails found!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "All emails displayed!", Toast.LENGTH_SHORT).show();
        }
    }
}
