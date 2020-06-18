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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyCompanyActivity extends AppCompatActivity
{
    private EditText companyName, companyEmail, confirmEmail;
    private Button send;
    private ProgressDialog loadingBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_company);

        companyName = (EditText) findViewById(R.id.company_name);
        companyEmail = (EditText) findViewById(R.id.company_email);
        confirmEmail = (EditText) findViewById(R.id.confirm_email);
        send = (Button) findViewById(R.id.send_verification_btn);

        loadingBar= new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                VerifyCompany();

            }
        });
    }

    private void VerifyCompany()
    {
        String name = companyName.getText().toString();
        final String email = companyEmail.getText().toString();
        final String confirm = confirmEmail.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Company name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Company email address required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirm))
        {
            Toast.makeText(this, "Please confirm your email address", Toast.LENGTH_SHORT).show();
        }
        else if (!email.equals(confirm))
        {
            Toast.makeText(this, "Email addresses do not match", Toast.LENGTH_SHORT).show();
        }
        else
        {
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    loadingBar.setTitle("Sending  verification email");
                    loadingBar.setMessage("Please wait while we verify the details...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    firebaseAuth.createUserWithEmailAndPassword(companyEmail.getText().toString(),
                            confirmEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            loadingBar.dismiss();
                            if (task.isSuccessful())
                            {
                                firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(VerifyCompanyActivity.this, "Email verification sent successfully!", Toast.LENGTH_SHORT).show();
                                            Intent verifyIntent = new Intent(VerifyCompanyActivity.this, AdminPanelActivity.class);
                                            startActivity(verifyIntent);
                                        }
                                        else
                                        {
                                            Toast.makeText(VerifyCompanyActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(VerifyCompanyActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            });
        }
    }
}
