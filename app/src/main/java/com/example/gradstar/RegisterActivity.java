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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{

    private Button CreateAccountButton;
    private EditText InputName,InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_name_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_number_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CreateAccount();

            }
        });
    }

    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "name required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Phone number required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creating account...");
            loadingBar.setMessage("Please wait while we are checking your credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name,phone, password);

        }
    }

    private void ValidatephoneNumber(final String name, final String phone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("Phone", phone);
                    userDataMap.put("Name", name);
                    userDataMap.put("Password",password);
                    
                    RootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) 
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this, "Please check your network connectivity.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    
                                }
                            });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "This" + phone+ "already exists!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
