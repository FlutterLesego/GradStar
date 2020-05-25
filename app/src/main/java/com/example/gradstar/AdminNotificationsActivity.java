package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminNotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notifications);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.admin_bottom_nav_bar);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.admin_nav_notifications_bottom);

        //perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.admin_nav_home_bottom:
                        Intent adminHomeIntent = new Intent(AdminNotificationsActivity.this, AdminPanelActivity.class);
                        startActivity(adminHomeIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_social_bottom:
                        Intent adminSocialIntent = new Intent(AdminNotificationsActivity.this, AdminSocialActivity.class);
                        startActivity(adminSocialIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_employer_bottom:
                        Intent adminEmployerIntent = new Intent(AdminNotificationsActivity.this, AdminProfessionalActivity.class);
                        startActivity(adminEmployerIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_notifications_bottom:
                        Intent adminNotificationsIntent = new Intent(AdminNotificationsActivity.this, AdminNotificationsActivity.class);
                        startActivity(adminNotificationsIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_universities_bottom:
                        Intent adminUniversitiesIntent = new Intent(AdminNotificationsActivity.this, AdminUniversitiesActivity.class);
                        startActivity(adminUniversitiesIntent);
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }

}
