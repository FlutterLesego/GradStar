package com.example.gradstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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



        Toast.makeText(this, "Welcome, Admin!", Toast.LENGTH_SHORT).show();


        AddNewPostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SendUserToPostActivity();
            }
        });
    }

    private void SendUserToPostActivity()
    {
        Intent addNewPostIntent = new Intent(AdminPanelActivity.this,PostActivity.class);
        startActivity(addNewPostIntent);
    }
}
