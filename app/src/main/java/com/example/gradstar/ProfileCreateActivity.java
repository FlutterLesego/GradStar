package com.example.gradstar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

    private String [] employmentDateList, universitiesList, coursesList, gradeList, ethnicityList, genderList, disabilityList, bursaryList;
    private EditText InputName, InputIdNumber, InputLanguage, InputStudentNumber, InputHobbies;
    private EditText InputJobs, InputTraits, InputCompanies;
    private TextView EmploymentDateDisplay, UniversityDisplay, CourseDisplay, GradeDisplay, EthnicityDisplay, GenderDisplay, DisabilityDisplay, BursaryDisplay;
    private Button ProceedButton, EmploymentDate, UniversityStudent, CourseButton, GradeButton, EthnicityButton, GenderButton, DisabilityButton, BursaryButton;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create);

        EmploymentDate = (Button) findViewById(R.id.employment_date);
        EmploymentDateDisplay = (TextView) findViewById(R.id.employment_date_display);

        UniversityStudent = (Button) findViewById(R.id.universities_btn);
        UniversityDisplay = (TextView) findViewById(R.id.universities_tv);
        universitiesList = getResources().getStringArray(R.array.universities_preferred);

        CourseButton = (Button) findViewById(R.id.courses_btn);
        CourseDisplay = (TextView) findViewById(R.id.course_tv);
        coursesList = getResources().getStringArray(R.array.courses_preffered);

        GradeButton = (Button) findViewById(R.id.grade_btn);
        GradeDisplay =(TextView) findViewById(R.id.grade_tv);

        EthnicityButton = (Button) findViewById(R.id.ethnicity_btn);
        EthnicityDisplay = (TextView) findViewById(R.id.ethnicity_tv);

        GenderButton = (Button) findViewById(R.id.gender_btn);
        GenderDisplay = (TextView) findViewById(R.id.gender_tv);

        DisabilityButton = (Button) findViewById(R.id.disability_btn);
        DisabilityDisplay = (TextView) findViewById(R.id.disability_tv);

        BursaryButton = (Button) findViewById(R.id.bursary_btn);
        BursaryDisplay = (TextView) findViewById(R.id.bursary_tv);

        ProceedButton = (Button) findViewById(R.id.proceed_btn);
        InputName = (EditText) findViewById(R.id.register_name_input);
        InputIdNumber = (EditText) findViewById(R.id.register_id_number);
        InputLanguage = (EditText) findViewById(R.id.register_language);
        InputStudentNumber = (EditText) findViewById(R.id.register_stud_card_number);
        InputHobbies= (EditText) findViewById(R.id.register_interests);
        InputJobs = (EditText) findViewById(R.id.register_part_time);
        InputTraits= (EditText) findViewById(R.id.register_traits);
        InputCompanies = (EditText) findViewById(R.id.register_brands);
        loader = new ProgressDialog(this);

        EmploymentDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //list of items
                employmentDateList = new String[] {"2021", "2022","2023","2024","2025","2026","2027","2028", "2029", "2030"};
                AlertDialog.Builder eBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                eBuilder.setTitle("Year available for employment:");
                eBuilder.setSingleChoiceItems(employmentDateList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EmploymentDateDisplay.setText(employmentDateList[which]);
                        dialog.dismiss();
                    }
                });
                eBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog eDialog = eBuilder.create();
                eDialog.show();
            }
        });

        UniversityStudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder uniBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                uniBuilder.setTitle("University:");
                uniBuilder.setSingleChoiceItems(universitiesList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UniversityDisplay.setText(universitiesList[which]);
                        dialog.dismiss();
                    }
                });
                uniBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog uniDialog = uniBuilder.create();
                uniDialog.show();
            }
        });

        CourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder coBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                coBuilder.setTitle("Course:");
                coBuilder.setSingleChoiceItems(coursesList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CourseDisplay.setText(coursesList[which]);
                        dialog.dismiss();
                    }
                });
                coBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog coDialog = coBuilder.create();
                coDialog.show();
            }
        });

        GradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //list of items
                gradeList = new String[] {"90+", "80% - 89%","70% - 79%","60% - 69%","50% - 59%","40% - 49%","less than 40%"};
                AlertDialog.Builder gBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                gBuilder.setTitle("Grade average:");
                gBuilder.setSingleChoiceItems(gradeList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GradeDisplay.setText(gradeList[which]);
                        dialog.dismiss();
                    }
                });
                gBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog gDialog = gBuilder.create();
                gDialog.show();

            }
        });

        ProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SaveUserProfile();

            }
        });

        EthnicityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //list of items
                ethnicityList = new String[] {"Black", "Coloured","Chinese","Indian","White","Other"};
                AlertDialog.Builder ethBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                ethBuilder.setTitle("Ethnicity:");
                ethBuilder.setSingleChoiceItems(ethnicityList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EthnicityDisplay.setText(ethnicityList[which]);
                        dialog.dismiss();
                    }
                });
                ethBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog ethDialog = ethBuilder.create();
                ethDialog.show();
            }
        });

        GenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //list of items
                genderList = new String[] {"Male", "Female"};
                AlertDialog.Builder gBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                gBuilder.setTitle("Gender:");
                gBuilder.setSingleChoiceItems(genderList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GenderDisplay.setText(genderList[which]);
                        dialog.dismiss();
                    }
                });
                gBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog gDialog = gBuilder.create();
                gDialog.show();
            }
        });

        DisabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //list of items
                disabilityList = new String[] {"Yes", "No"};
                AlertDialog.Builder dBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                dBuilder.setTitle("Type of disability:");
                dBuilder.setSingleChoiceItems(disabilityList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DisabilityDisplay.setText(disabilityList[which]);
                        dialog.dismiss();
                    }
                });
                dBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog dDialog = dBuilder.create();
                dDialog.show();

            }
        });

        BursaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //list of items
                bursaryList = new String[] {"Yes", "No"};
                AlertDialog.Builder bBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                bBuilder.setTitle("Bursary:");
                bBuilder.setSingleChoiceItems(bursaryList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BursaryDisplay.setText(bursaryList[which]);
                        dialog.dismiss();
                    }
                });
                bBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog bDialog = bBuilder.create();
                bDialog.show();

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
        else
        {
            loader.setTitle("Registering");
            loader.setMessage("Please wait while we process your information");
            loader.setCanceledOnTouchOutside(false);
            loader.show();

            ValidateidNumber(name, idnumber,language, student, hobbies, jobs, traits, companies);
        }

    }

    private void ValidateidNumber(final String name, final String idnumber, final String language, final String student, final String hobbies, final String jobs, final String traits, final String companies)
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
