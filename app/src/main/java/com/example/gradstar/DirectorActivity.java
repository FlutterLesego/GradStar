package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DirectorActivity extends AppCompatActivity
{
    private CardView employerCardView, studentCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director);

        employerCardView = (CardView) findViewById(R.id.employer_card);
        studentCardView = (CardView) findViewById(R.id.student_card);

        studentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentRegisterIntent = new Intent(DirectorActivity.this, LoginActivity.class);
                startActivity(studentRegisterIntent);
            }
        });

        employerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent employerRegisterIntent = new Intent(DirectorActivity.this, EmployerLoginActivity.class);
                startActivity(employerRegisterIntent);
            }
        });
    }
}
