package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminPanelActivity extends AppCompatActivity
{
    private Toolbar aToolBar;
    private ImageButton AddNewPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        aToolBar = (Toolbar) findViewById(R.id.admin_toolbar);
        setSupportActionBar(aToolBar);
        getSupportActionBar().setTitle("Admin Panel");


        AddNewPostButton = (ImageButton) findViewById(R.id.add_post_btn);




        AddNewPostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SendUserToCategoriesActivity();
            }
        });

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.admin_bottom_nav_bar);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.admin_nav_home_bottom);

        //perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.admin_nav_home_bottom:
                        return true;
                    case R.id.admin_nav_social_bottom:
                        Intent adminSocialIntent = new Intent(AdminPanelActivity.this, AdminSocialActivity.class);
                        startActivity(adminSocialIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_employer_bottom:
                        Intent adminEmployerIntent = new Intent(AdminPanelActivity.this, AdminProfessionalActivity.class);
                        startActivity(adminEmployerIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_notifications_bottom:
                        Intent adminNotificationsIntent = new Intent(AdminPanelActivity.this, AdminNotificationsActivity.class);
                        startActivity(adminNotificationsIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_universities_bottom:
                        Intent adminUniversitiesIntent = new Intent(AdminPanelActivity.this, AdminUniversitiesActivity.class);
                        startActivity(adminUniversitiesIntent);
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }


    private void SendUserToCategoriesActivity()
    {
        Intent addNewPostIntent = new Intent(AdminPanelActivity.this,AdminCategoryActivity.class);
        startActivity(addNewPostIntent);
    }
}
