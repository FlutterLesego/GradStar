package com.example.gradstar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.FirebaseDatabase;

public class EmployerRegisterActivity extends AppCompatActivity
{
    private EditText comName, comEmail,comEmailConfirm,comTel, fullname;
    private Button verify;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_register);

        fullname = (EditText) findViewById(R.id.employer_fullname);
        comName = (EditText) findViewById(R.id.employer_name);
        comEmail = (EditText) findViewById(R.id.employer_email);
        comEmailConfirm = (EditText) findViewById(R.id.employer_email_confirm);
        comTel = (EditText) findViewById(R.id.employer_tel);
        verify = (Button) findViewById(R.id.verify_btn);


        loadingBar = new ProgressDialog(this);


        verify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseDatabase.getInstance().getReference().child("Tokens").child("");
                createNotification();
            }
        });


    }
//building the notification
    private void createNotification()
    {
        String names = fullname.getText().toString();
        String com = comName.getText().toString();
        String email = comEmail.getText().toString();
        String emailConfirm = comEmailConfirm.getText().toString();
        String tel = comTel.getText().toString();

        if (TextUtils.isEmpty(names))
        {
            Toast.makeText(this, "Full name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(com))
        {
            Toast.makeText(this, "Company name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Company email address required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailConfirm))
        {
            Toast.makeText(this, "Please confirm email address", Toast.LENGTH_SHORT).show();
        }
        else if (!email.equals(emailConfirm))
        {
            Toast.makeText(this, "Email addresses do not match", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tel))
        {
            Toast.makeText(this, "Company telephone number required", Toast.LENGTH_SHORT).show();
        }
        else if (!TextUtils.isDigitsOnly(tel))
        {
            Toast.makeText(this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
        }
        else {
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    loadingBar.setTitle("Requesting verification");
                    loadingBar.setMessage("Please wait while we check your credentials");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    Notify();
                }
            });

        }
    }

    private void Notify()
    {
        NotificationCompat.Builder companyCreateNotification = new NotificationCompat.Builder(this);
        companyCreateNotification.setAutoCancel(true);

        companyCreateNotification.setSmallIcon(R.drawable.applogo_white);
        companyCreateNotification.setTicker("New company verification requested");
        companyCreateNotification.setWhen(System.currentTimeMillis());
        companyCreateNotification.setContentTitle("Verify new company");
        companyCreateNotification.setContentText(comName+ "has requested verification");

        Intent createCompanyIntent = new Intent(this, VerifyCompanyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, createCompanyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        companyCreateNotification.setContentIntent(pendingIntent);

        //builds notification and issuing
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, companyCreateNotification.build());

        Intent intent = new Intent(this, WaitForVerificationActivity.class);
        startActivity(intent);
    }
}
