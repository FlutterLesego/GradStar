package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WaitForVerificationActivity extends AppCompatActivity
{
    private Button viewVideos, backHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_verification);

        viewVideos = (Button) findViewById(R.id.view_videos);
        backHome = (Button) findViewById(R.id.back_home);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(WaitForVerificationActivity.this, EmployerProfileCreateActivity.class);
                startActivity(backIntent);
            }
        });
    }
}
