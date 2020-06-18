package com.example.gradstar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gradstar.Model.Admins;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity
{

    private EditText InputPhone, InputPassword;
    private Button Administrator;
    private ProgressDialog loader;

    private String parentDbName = "Admins";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        InputPhone = (EditText) findViewById(R.id.admin_phone_login);
        InputPassword = (EditText) findViewById(R.id.login_password_admin);
        Administrator = (Button) findViewById(R.id.admin_login_btn);
        loader = new ProgressDialog(this);

        Administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoginAdmin();

            }
        });
    }

    private void LoginAdmin()
    {
        String phone = InputPhone.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Phone number required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loader.setTitle("Accessing admin panel..." );
            loader.setMessage("Please wait while we verify your credentials");
            loader.setCanceledOnTouchOutside(false);
            loader.show();

            AdminAccess(phone, password);
        }
    }

    private void AdminAccess(final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Admins usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Admins.class);
                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(AdminLoginActivity.this, "Access Granted", Toast.LENGTH_SHORT).show();
                                loader.dismiss();

                                Intent intent = new Intent(AdminLoginActivity.this,AdminPanelActivity.class);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            loader.dismiss();
                            Toast.makeText(AdminLoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(AdminLoginActivity.this, "Access Denied!", Toast.LENGTH_SHORT).show();
                    loader.dismiss();
                    Toast.makeText(AdminLoginActivity.this, "Please create a new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
