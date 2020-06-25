package com.example.gradstar;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.dismiss();
    }
}
