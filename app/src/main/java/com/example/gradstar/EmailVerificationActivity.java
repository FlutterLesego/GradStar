package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmailVerificationActivity extends AppCompatActivity {

    private Button Continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        Continue = (Button) findViewById(R.id.continue_btn);

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(EmailVerificationActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
