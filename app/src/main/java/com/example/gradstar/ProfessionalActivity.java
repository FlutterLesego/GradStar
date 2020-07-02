package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradstar.Adapter.EmployerAdapter;
import com.example.gradstar.Model.Employers;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ProfessionalActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private EmployerAdapter employerAdapter;
    private List<Employers> mEmployers;
    TextView search_here;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);


        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        //set Home
        bottomNavigationView.setSelectedItemId(R.id.nav_employer_bottom);

        //itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home_bottom:

                        startActivity(new Intent(getApplicationContext()
                                , StudentsHomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_social_bottom:
                        startActivity(new Intent(getApplicationContext()
                                , SocialActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_employer_bottom:
                        return true;

                    case R.id.nav_notifications_bottom:
                        startActivity(new Intent(getApplicationContext()
                                , NotificationsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.nav_universities_bottom:
                        startActivity(new Intent(getApplicationContext()
                                , UniversitiesActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                }
                return false;
            }
        });

        search_here = findViewById(R.id.search_here);
        search_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent searchIntent = new Intent(ProfessionalActivity.this, SearchingActivity.class);
                startActivity(searchIntent);

            }
        });

    }
}
