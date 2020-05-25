package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SocialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        //set Socials
        bottomNavigationView.setSelectedItemId(R.id.nav_social_bottom);

        //itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home_bottom:

                        startActivity(new Intent(getApplicationContext()
                                , StudentsHomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_social_bottom:
                        return true;

                    case R.id.nav_employer_bottom:
                        startActivity(new Intent(getApplicationContext()
                                , ProfessionalActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_notifications_bottom:
                        startActivity(new Intent(getApplicationContext()
                                , NotificationsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_universities_bottom:
                        startActivity(new Intent(getApplicationContext()
                                , UniversitiesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                }
                return false;
            }
        });

    }
}
