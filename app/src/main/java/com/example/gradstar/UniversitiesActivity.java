package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UniversitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        //set Universities
        bottomNavigationView.setSelectedItemId(R.id.nav_universities_bottom);

        //itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home_bottom:

                        startActivity(new Intent(getApplicationContext()
                                ,StudentsHomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_social_bottom:
                        startActivity(new Intent(getApplicationContext()
                                ,SocialActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_employer_bottom:
                        startActivity(new Intent(getApplicationContext()
                                ,ProfessionalActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_notifications_bottom:
                        startActivity(new Intent(getApplicationContext()
                                ,NotificationsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_universities_bottom:
                        return true;


                }
                return false;
            }
        });

        FloatingActionButton fab_signout = findViewById(R.id.fab_signout_btn);

        fab_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent signoutIntent = new Intent(UniversitiesActivity.this,LoginActivity.class);
                startActivity(signoutIntent);

            }
        });

        FloatingActionButton fab_settings = findViewById(R.id.fab_settings_btn);

        fab_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent settingsIntent = new Intent(UniversitiesActivity.this,SettingsActivity.class);
                startActivity(settingsIntent);

            }
        });

        FloatingActionButton fab_about = findViewById(R.id.fab_about_btn);

        fab_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent aboutIntent = new Intent(UniversitiesActivity.this,AboutActivity.class);
                startActivity(aboutIntent);

            }
        });

        FloatingActionButton fab_sponsors = findViewById(R.id.fab_sponsors_btn);

        fab_sponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent sponsorsIntent = new Intent(UniversitiesActivity.this,SponsorsActivity.class);
                startActivity(sponsorsIntent);

            }
        });

        FloatingActionButton fab_events = findViewById(R.id.fab_events_btn);

        fab_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent eventsIntent = new Intent(UniversitiesActivity.this,EventsActivity.class);
                startActivity(eventsIntent);

            }
        });

        FloatingActionButton fab_profile = findViewById(R.id.fab_profile_btn);

        fab_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent profileIntent = new Intent(UniversitiesActivity.this,UserProfileActivity.class);
                startActivity(profileIntent);

            }
        });
    }
}
