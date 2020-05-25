package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminUniversitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_universities);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Universities");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.admin_bottom_nav_bar);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.admin_nav_universities_bottom);

        //perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.admin_nav_home_bottom:
                        Intent adminHomeIntent = new Intent(AdminUniversitiesActivity.this, AdminPanelActivity.class);
                        startActivity(adminHomeIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_social_bottom:
                        Intent adminSocialIntent = new Intent(AdminUniversitiesActivity.this, AdminSocialActivity.class);
                        startActivity(adminSocialIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_employer_bottom:
                        Intent adminEmployerIntent = new Intent(AdminUniversitiesActivity.this, AdminProfessionalActivity.class);
                        startActivity(adminEmployerIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_notifications_bottom:
                        Intent adminNotificationsIntent = new Intent(AdminUniversitiesActivity.this, AdminNotificationsActivity.class);
                        startActivity(adminNotificationsIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_universities_bottom:
                        return true;

                }
                return false;
            }
        });
    }

}
