package com.example.filmograf;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if (itemId == R.id.navHome) {
                    loadFragment(new HomeFragment(), false);
                } else if (itemId == R.id.navVizyon) {
                    loadFragment(new VizyonFragment(), false);
                } else if (itemId == R.id.navListem) {
                    loadFragment(new ListeFragment(), false);
                } else if (itemId == R.id.navProfil) {
                    loadFragment(new ProfilFragment(), false);
                }
                return true;
            }
        });


        loadFragment(new HomeFragment(), false);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (!isAppInitialized) {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        }

        fragmentTransaction.commit();
    }
}
