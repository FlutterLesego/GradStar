package com.example.gradstar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity
{
    private Button CreateAccountButton;
    private ImageButton BackButton;
    private EditText InputName, InputEmailAddress, InputPassword;
    private ProgressDialog loadingBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        BackButton = (ImageButton) findViewById(R.id.back_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputEmailAddress = (EditText) findViewById(R.id.register_email_address);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar= new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        final String email = InputEmailAddress.getText().toString();
        final String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Email address required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
        }
        else if (password.length()<6)
        {
            Toast.makeText(this, "Password must have at least 6 characters", Toast.LENGTH_SHORT).show();
        }
        else
        {

            CreateAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    loadingBar.setTitle("Creating account");
                    loadingBar.setMessage("Please wait while we check your credentials");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    firebaseAuth.createUserWithEmailAndPassword(InputEmailAddress.getText().toString(),
                            InputPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                                            Toast.makeText(RegisterActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                                            Intent verifyIntent = new Intent(RegisterActivity.this, ProfileCreateActivity.class);
                                            startActivity(verifyIntent);
                                        }
                                        else
                                        {
                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            });
        }
    }

    //private void Validateemailaddress(final String name, final String email, final String password)
    //{
    //    final DatabaseReference RootRef;
      //  RootRef = FirebaseDatabase.getInstance().getReference();
//
  //      RootRef.addListenerForSingleValueEvent(new ValueEventListener()
    //    {
      //      @Override
        //    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
          //  {
            //    if (!(dataSnapshot.child("Users").child(email).exists()))
              //  {
                //    HashMap<String, Object> userdataMap = new HashMap<>();
                  //  userdataMap.put("email", email);
                    //userdataMap.put("name", name);
                    //userdataMap.put("password", password);
//
  //                  RootRef.child("Users").child(email).updateChildren(userdataMap)
    //                        .addOnCompleteListener(new OnCompleteListener<Void>() {
      //                          @Override
        //                        public void onComplete(@NonNull Task<Void> task)
          //                      {
            //                        if (task.isSuccessful())
              //                      {
                //                        Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                  //                      loadingBar.dismiss();
//
  //                                      Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
    //                                    startActivity(intent);
      //                              }
        //                            else
          //                          {
            //                            loadingBar.dismiss();
              //                          Toast.makeText(RegisterActivity.this, "Please check your network connectivity", Toast.LENGTH_SHORT).show();
                //                    }
//
  //                              }
    //                        });
      //          }
        //        else
          //      {
            //        Toast.makeText(RegisterActivity.this, ""+ email + " " + "already in use", Toast.LENGTH_SHORT).show();
              //      loadingBar.show();
                //    Toast.makeText(RegisterActivity.this, "Please try again using another phone number", Toast.LENGTH_SHORT).show();
                  //  loadingBar.dismiss();
//
  //                  Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
    //                startActivity(intent);
      //          }
        //    }
//
  //          @Override
            //public void onCancelled(@NonNull DatabaseError databaseError)
            //{

            //}
      //  });
    //}
}
