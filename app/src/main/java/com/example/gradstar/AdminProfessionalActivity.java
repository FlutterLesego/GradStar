package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminProfessionalActivity extends AppCompatActivity {

    private ImageView jobAdd;
    private RecyclerView jobsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_professional);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Professional");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        jobAdd = (ImageView) findViewById(R.id.job_add);
        jobAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoriesIntent = new Intent(AdminProfessionalActivity.this, AdminCategoryActivity.class);
                startActivity(categoriesIntent);
            }
        });

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.admin_bottom_nav_bar);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.admin_nav_employer_bottom);

        //perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.admin_nav_home_bottom:
                        Intent adminHomeIntent = new Intent(AdminProfessionalActivity.this, AdminPanelActivity.class);
                        startActivity(adminHomeIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_social_bottom:
                        Intent adminSocialIntent = new Intent(AdminProfessionalActivity.this, AdminSocialActivity.class);
                        startActivity(adminSocialIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_employer_bottom:
                        return true;
                    case R.id.admin_nav_notifications_bottom:
                        Intent adminNotificationsIntent = new Intent(AdminProfessionalActivity.this, AdminNotificationsActivity.class);
                        startActivity(adminNotificationsIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_universities_bottom:
                        Intent adminUniversitiesIntent = new Intent(AdminProfessionalActivity.this, AdminUniversitiesActivity.class);
                        startActivity(adminUniversitiesIntent);
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }

}
