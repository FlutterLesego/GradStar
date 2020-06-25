package com.example.gradstar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

public class EmployerLoginActivity extends AppCompatActivity
{
    EditText employerEmailAddress, employerPassword;
    Button employerLoginButton, employerRegisterButton;
    TextView forgotPAssword;
    CheckBox remeberMe;
    FirebaseAuth eAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_login);

        employerEmailAddress = findViewById(R.id.elogin_email_address);
        employerPassword = findViewById(R.id.elogin_password_input);
        forgotPAssword = findViewById(R.id.eforgot_password);
        remeberMe = findViewById(R.id.remember_me_chk);
        employerLoginButton = findViewById(R.id.elogin_btn);
        employerRegisterButton = findViewById(R.id.eregister_btn);

        employerRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new  Intent(EmployerLoginActivity.this, EmployerProfileCreateActivity.class);
                startActivity(registerIntent);
            }
        });
        employerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final ProgressDialog pd = new ProgressDialog(EmployerLoginActivity.this);
                pd.setMessage("Logging in...");
                pd.show();

                String str_employerEmailAddress = employerEmailAddress.getText().toString();
                String str_employerPassword = employerPassword.getText().toString();

                if (TextUtils.isEmpty(str_employerEmailAddress)|| TextUtils.isEmpty(str_employerPassword))
                {
                    Toast.makeText(EmployerLoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    eAuth.signInWithEmailAndPassword(str_employerEmailAddress, str_employerEmailAddress).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                DatabaseReference employerRef = FirebaseDatabase.getInstance().getReference().child("employers")
                                        .child(eAuth.getCurrentUser().getUid());
                                employerRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        pd.dismiss();
                                        Intent intent =new Intent(EmployerLoginActivity.this, StudentsHomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        pd.dismiss();
                                    }
                                });
                            }
                            else
                            {
                                pd.dismiss();
                                Toast.makeText(EmployerLoginActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });



    }
}
