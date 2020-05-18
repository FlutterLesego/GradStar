package com.example.gradstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(7000);
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent welcomeIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(welcomeIntent);
                }
            }
        };
        thread.start();
    }

    public void onPause()
    {
        super.onPause();
        finish();
    }
}
