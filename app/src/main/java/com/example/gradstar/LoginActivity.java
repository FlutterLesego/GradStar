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
    private TextView AdminLink, NotAdmin;

    private FirebaseAuth firebaseAuth;


    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RegisterButton = (Button) findViewById(R.id.register_edit) ;
        LoginButton = (Button) findViewById(R.id.login_btn);
        InputEmailAddress = (EditText) findViewById(R.id.login_email_address);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        AdminLink = (TextView) findViewById(R.id.admin_panel);
        NotAdmin = (TextView) findViewById(R.id.not_admin_panel);
        loadingBar= new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();


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
                loadingBar.show();
                loadingBar.setMessage("Please wait...");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoginButton.setText("Login admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdmin.setVisibility(View.VISIBLE);
                parentDbName = "Admins";

            }
        });

        NotAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdmin.setVisibility(View.INVISIBLE);
                parentDbName = "Users";

            }
        });

    }

    private void LoginUser()
    {
        String email = InputEmailAddress.getText().toString();
        String password = InputPassword.getText().toString();

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
                    firebaseAuth.signInWithEmailAndPassword(InputEmailAddress.getText().toString(),
                            InputPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            loadingBar.dismiss();
                            if (task.isSuccessful())
                            {
                                if (firebaseAuth.getCurrentUser().isEmailVerified()
                                ) {
                                    startActivity(new Intent(LoginActivity.this, StudentsHomeActivity.class));

                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
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
                            else if (parentDbName.equals("Users"))
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
