package com.example.gradstar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gradstar.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity
{
    private EditText InputEmailAddress, InputPassword;
    private Button LoginButton, RegisterButton;
    private ProgressDialog loadingBar;
    private ImageView adminPanel;

    private FirebaseAuth firebaseAuth;


    private String parentDbName = "users";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RegisterButton = (Button) findViewById(R.id.register_edit) ;
        LoginButton = (Button) findViewById(R.id.login_btn);
        InputEmailAddress = (EditText) findViewById(R.id.login_email_address);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        adminPanel = (ImageView) findViewById(R.id.login_applogo);
        loadingBar= new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        adminPanel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                Intent adminIntent = new Intent(LoginActivity.this, AdminLoginActivity.class);
                startActivity(adminIntent);
                return false;

            }
        });


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadingBar.setMessage("Logging in...");
                loadingBar.show();
                LoginUser();

            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loadingBar.show();
                loadingBar.setMessage("Please wait...");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                loadingBar.dismiss();
            }
        });

    }

    private void LoginUser()
    {
        loadingBar.dismiss();
        final String email = InputEmailAddress.getText().toString();
        final String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Email address required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
        }
        else
        {

            LoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    loadingBar.setMessage("Please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    firebaseAuth.signInWithEmailAndPassword(email,
                            password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            loadingBar.dismiss();
                            if (task.isSuccessful())
                            {
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                                        .child("users").child(firebaseAuth.getCurrentUser().getUid());

                                userRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {
                                        if (firebaseAuth.getCurrentUser().isEmailVerified()
                                        ) {
                                            loadingBar.dismiss();
                                            startActivity(new Intent(LoginActivity.this, StudentsHomeActivity.class));

                                        }
                                        else
                                        {
                                            loadingBar.dismiss();
                                            Toast.makeText(LoginActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            });
        }
    }

    private void AllowAccessToAccount(final String email, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDbName).child(email).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(email).getValue(Users.class);
                    if (usersData.getEmail().equals(email))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "Logged in as admin", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this,AdminPanelActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("users"))
                            {
                                Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this,StudentsHomeActivity.class);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Please create a new account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
