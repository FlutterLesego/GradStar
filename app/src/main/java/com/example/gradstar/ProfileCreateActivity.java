package com.example.gradstar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProfileCreateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText InputName, InputIdNumber, InputLanguage, InputStudentNumber, InputHobbies;
    private EditText InputJobs, InputTraits, InputCompanies, InputGrade, InputMobile, InputWhatsapp;
    private Button ProceedButton;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create);

        ProceedButton = (Button) findViewById(R.id.proceed_btn);
        InputName = (EditText) findViewById(R.id.register_name_input);
        InputIdNumber = (EditText) findViewById(R.id.register_id_number);
        InputLanguage = (EditText) findViewById(R.id.register_language);
        InputStudentNumber = (EditText) findViewById(R.id.register_stud_card_number);
        InputHobbies= (EditText) findViewById(R.id.register_interests);
        InputJobs = (EditText) findViewById(R.id.register_part_time);
        InputTraits= (EditText) findViewById(R.id.register_traits);
        InputCompanies = (EditText) findViewById(R.id.register_brands);
        InputGrade = (EditText) findViewById(R.id.register_gpa);
        InputMobile = (EditText) findViewById(R.id.register_mobile);
        InputWhatsapp = (EditText) findViewById(R.id.register_whatsapp);
        loader = new ProgressDialog(this);

        ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SaveUserProfile();

            }
        });


        Button dateButton = (Button) findViewById(R.id.employment_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Choose date");

            }
        });
    }

    private void SaveUserProfile()
    {
        String name = InputName.getText().toString();
        String idnumber = InputIdNumber.getText().toString();
        String language = InputLanguage.getText().toString();
        String student = InputStudentNumber.getText().toString();
        String hobbies = InputHobbies.getText().toString();
        String jobs = InputJobs.getText().toString();
        String traits = InputTraits.getText().toString();
        String companies = InputCompanies.getText().toString();
        String grade = InputGrade.getText().toString();
        String mobile = InputMobile.getText().toString();
        
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(idnumber))
        {
            Toast.makeText(this, "Identification required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(language))
        {
            Toast.makeText(this, "Language required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(student))
        {
            Toast.makeText(this, "Student number required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(hobbies))
        {
            Toast.makeText(this, "Interests/hobbies required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(jobs))
        {
            Toast.makeText(this, "Previous jobs required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(traits))
        {
            Toast.makeText(this, "Characteristics required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(companies))
        {
            Toast.makeText(this, "Companies required for matching", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(grade))
        {
            Toast.makeText(this, "Grade required", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(mobile))
        {
            Toast.makeText(this, "Mobile number required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loader.setTitle("Registering");
            loader.setMessage("Please wait while we process your information");
            loader.setCanceledOnTouchOutside(false);
            loader.show();

            ValidateidNumber(name, idnumber,language, student, hobbies, jobs, traits, companies, grade, mobile);
        }

    }

    private void ValidateidNumber(final String name, final String idnumber, final String language, final String student, final String hobbies, final String jobs, final String traits, final String companies, final String grade, final String mobile)
    {
        final DatabaseReference RootReference;
        RootReference = FirebaseDatabase.getInstance().getReference();

        RootReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(idnumber).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("id number", idnumber);
                    userdataMap.put("name", name);
                    userdataMap.put("language", language);
                    userdataMap.put("student number", student);
                    userdataMap.put("interests/hobbies", hobbies);
                    userdataMap.put("part time jobs", jobs);
                    userdataMap.put("characteristics", traits);
                    userdataMap.put("companies", companies);
                    userdataMap.put("grade", grade);
                    userdataMap.put("mobile", mobile);

                    RootReference.child("Users").child(idnumber).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(ProfileCreateActivity.this, "Congratulations! You're successfully registered on GradStar", Toast.LENGTH_SHORT).show();
                                        loader.dismiss();
                                        
                                        Intent intent = new Intent(ProfileCreateActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } 
                                    else 
                                    {
                                        Toast.makeText(ProfileCreateActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(ProfileCreateActivity.this, " " + idnumber + "" + "already registered", Toast.LENGTH_SHORT).show();
                    loader.dismiss();
                    Toast.makeText(ProfileCreateActivity.this, "Please contact support", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ProfileCreateActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView closingDate = (TextView) findViewById(R.id.employment_date_display);
        closingDate.setText(currentDateString);

    }
}
