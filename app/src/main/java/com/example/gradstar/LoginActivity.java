package com.example.gradstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gradstar.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity
{
    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton, RegisterButton;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RegisterButton = (Button) findViewById(R.id.register_btn);
        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPhoneNumber = (EditText) findViewById(R.id.login_number_input) ;
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) 
            {
                LoginUser();
                
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

    }


    private void LoginUser()
    {
        String phoneNumber = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(phoneNumber))
        {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else
            {
                loadingBar.setTitle("Logging in...");
                loadingBar.setMessage("Please wait while we are checking your credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                AllowAccessToAccount(phoneNumber,password);
            }

        }

    private void AllowAccessToAccount(final String phoneNumber, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDbName).child(phoneNumber).exists())
                {
                    Users userData = dataSnapshot.child(parentDbName).child(phoneNumber).getValue(Users.class);

                    if(userData.getPhoneNumber().equals(phoneNumber))
                    {
                        if(userData.getPassword().equals(password))
                        {
                            Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Please register new account", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }
}
