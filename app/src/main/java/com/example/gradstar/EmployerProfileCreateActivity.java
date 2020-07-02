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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class EmployerProfileCreateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    Button gradeButton;
    TextView gradesSelected;

    RadioGroup honoursGroup;
    RadioButton honours;

    Button universitiesButton;
    TextView universitiesSelected;

    Button coursesButton;
    TextView coursesSelected;

    Button employmentDate;
    TextView employmentDisplay;
    String[] employmentDateList;

    String[] gradeList;
    boolean[] checkGrades;
    ArrayList<Integer> gradeNumbers = new ArrayList<>();

    String[] coursesList;
    boolean[] checkCourses;
    ArrayList<Integer> coursesNames = new ArrayList<>();

    String[] universitiesList;
    boolean[] checkUniversities;
    ArrayList<Integer> universitiesNames = new ArrayList<>();

    EditText companyName, companyWebsite, gradContactName, telNumber, companyEmail,
            applicantsSpecs, progOverview, applyDetails, anythingElse, employerPassword;
    TextView openDate, closeDate, employmentStartDate, universitiesPref, coursesPref, gradeAverage;
    Button proceedButton;

    FirebaseAuth eAuth;
    DatabaseReference employerReference;
    String currentEmployerId;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile_create);

        companyName = findViewById(R.id.com_name);
        companyEmail = findViewById(R.id.email_employer);
        companyWebsite = findViewById(R.id.url);
        gradContactName = findViewById(R.id.contact_name);
        telNumber = findViewById(R.id.tel_employer);
        openDate = findViewById(R.id.programme_app_open_tv);
        closeDate = findViewById(R.id.programme_app_close_tv);
        employmentStartDate = findViewById(R.id.employment_date_tv);
        universitiesPref = findViewById(R.id.universities_tv);
        coursesPref = findViewById(R.id.course_tv);
        gradeAverage = findViewById(R.id.grade_tv);
        applicantsSpecs = findViewById(R.id.spec_edit);
        progOverview = findViewById(R.id.overview_edit);
        applyDetails =findViewById(R.id.apply_edit);
        anythingElse = findViewById(R.id.anything_else_edit);
        employerPassword = findViewById(R.id.password_edit);
        proceedButton = findViewById(R.id.register_btn);

        eAuth = FirebaseAuth.getInstance();
        currentEmployerId = eAuth.getCurrentUser().getUid();
        employerReference = FirebaseDatabase.getInstance().getReference().child("employers").child(currentEmployerId);

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pd = new ProgressDialog(EmployerProfileCreateActivity.this);
                pd.setTitle("Creating Account");
                pd.setMessage("Please wait while we verify your credentials");
                pd.show();

                String str_companyName = companyName.getText().toString();
                String str_companyEmail = companyEmail.getText().toString();
                String str_gradContactName = gradContactName.getText().toString();
                String str_telNumber = telNumber.getText().toString();
                String str_openDate = openDate.getText().toString();
                String str_closeDate = closeDate.getText().toString();
                String str_employmentStartDate = employmentStartDate.getText().toString();
                String str_universitiesPref = universitiesPref.getText().toString();
                String str_coursesPref = coursesPref.getText().toString();
                String str_gradeAverage = gradeAverage.getText().toString();
                String str_applicantsSpecs = applicantsSpecs.getText().toString();
                String str_progOverview = progOverview.getText().toString();
                String str_applyDetails = applyDetails.getText().toString();
                String str_anythingElse = anythingElse.getText().toString();
                String str_employerPassword = employerPassword.getText().toString();

                if (TextUtils.isEmpty(str_companyName)||TextUtils.isEmpty(str_companyEmail)||TextUtils.isEmpty(str_gradContactName)|| TextUtils.isEmpty(str_telNumber)||
                        TextUtils.isEmpty(str_employmentStartDate)||TextUtils.isEmpty(str_universitiesPref)||
                        TextUtils.isEmpty(str_coursesPref)||TextUtils.isEmpty(str_gradeAverage)||TextUtils.isEmpty(str_applicantsSpecs)||TextUtils.isEmpty(str_progOverview)||
                        TextUtils.isEmpty(str_applyDetails)||TextUtils.isEmpty(str_anythingElse) || TextUtils.isEmpty(str_employerPassword))
                {
                    pd.dismiss();
                    Toast.makeText(EmployerProfileCreateActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else if (employerPassword.length() < 6)
                {
                    pd.dismiss();
                    Toast.makeText(EmployerProfileCreateActivity.this, "Password must have at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isDigitsOnly(str_employerPassword))
                {
                    pd.dismiss();
                    Toast.makeText(EmployerProfileCreateActivity.this, "Password must have at least 1 letter", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pd.dismiss();
                    RegisterEmployer(str_companyName, str_companyEmail, str_gradContactName, str_telNumber, str_openDate, str_closeDate,
                            str_employmentStartDate, str_universitiesPref, str_coursesPref, str_gradeAverage, str_applicantsSpecs, str_progOverview,
                            str_applyDetails, str_anythingElse, str_employerPassword);
                }

            }
        });


        employmentDate = findViewById(R.id.employment_date);
        employmentDisplay = findViewById(R.id.employment_date_tv);

        gradeButton = (Button) findViewById(R.id.grade_btn);
        gradesSelected = findViewById(R.id.grade_tv);

        gradeList = getResources().getStringArray(R.array.grade_average);
        checkGrades = new boolean[gradeList.length];

        honoursGroup = findViewById(R.id.honours);

        coursesButton = (Button) findViewById(R.id.courses_btn);
        coursesSelected = findViewById(R.id.course_tv);

        coursesList = getResources().getStringArray(R.array.courses_preffered);
        checkCourses = new boolean[coursesList.length];

        universitiesButton = (Button) findViewById(R.id.universities_btn);
        universitiesSelected = findViewById(R.id.universities_tv);

        universitiesList = getResources().getStringArray(R.array.universities_preferred);
        checkUniversities = new boolean[universitiesList.length];

        employmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employmentDateList = new String[]{"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
                AlertDialog.Builder eBuilder = new AlertDialog.Builder(EmployerProfileCreateActivity.this);
                eBuilder.setTitle("Employment date");
                eBuilder.setSingleChoiceItems(employmentDateList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        employmentDisplay.setText(employmentDateList[which]);
                        dialog.dismiss();
                    }
                });
                eBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog eDialog = eBuilder.create();
                eDialog.show();
            }
        });

        universitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder uBuilder = new AlertDialog.Builder(EmployerProfileCreateActivity.this);
                uBuilder.setTitle("Universities preferred:");
                uBuilder.setMultiChoiceItems(universitiesList, checkUniversities, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {if (isChecked)
                    {
                        if (!universitiesNames.contains(position))
                        {
                            universitiesNames.add(position);
                        }
                    }
                    else if (universitiesNames.contains(position))
                    {
                        universitiesNames.remove(position);
                    }
                    }
                });
                uBuilder.setCancelable(false);
                uBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String universities = "";
                        for (int i = 0; i < universitiesNames.size(); i++)
                        {
                            universities = universities + universitiesList[universitiesNames.get(i)];
                            if (i != universitiesNames.size()-1)
                            {
                                universities = universities + ", ";
                            }
                        }
                        universitiesSelected.setText(universities);
                    }
                });
                uBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                uBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        for (int i = 0; i <checkUniversities.length;i++)
                        {
                            checkUniversities[i] = false;
                            universitiesNames.clear();
                            universitiesSelected.setText("");
                        }

                    }
                });
                AlertDialog uDialog = uBuilder.create();
                uDialog.show();
            }
        });

        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder cBuilder = new AlertDialog.Builder(EmployerProfileCreateActivity.this);
                cBuilder.setTitle("Courses preferred:");
                cBuilder.setMultiChoiceItems(coursesList, checkCourses, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {if (isChecked)
                    {
                        if (!coursesNames.contains(position))
                        {
                            coursesNames.add(position);
                        }
                    }
                    else if (coursesNames.contains(position))
                    {
                        coursesNames.remove(position);
                    }
                    }
                });
                cBuilder.setCancelable(false);
                cBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String courses = "";
                        for (int i = 0; i < coursesNames.size(); i++)
                        {
                            courses = courses + coursesList[coursesNames.get(i)];
                            if (i != coursesNames.size()-1)
                            {
                                courses = courses + ", ";
                            }
                        }
                        coursesSelected.setText(courses);
                    }
                });
                cBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                cBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        for (int i = 0; i <checkCourses.length;i++)
                        {
                            checkCourses[i] = false;
                            coursesNames.clear();
                            coursesSelected.setText("");
                        }

                    }
                });
                AlertDialog cDialog = cBuilder.create();
                cDialog.show();
            }
        });

        gradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder gBuilder = new AlertDialog.Builder(EmployerProfileCreateActivity.this);
                gBuilder.setTitle("Minimum grade average:");
                gBuilder.setMultiChoiceItems(gradeList, checkGrades, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {if (isChecked)
                    {
                        if (!gradeNumbers.contains(position))
                        {
                            gradeNumbers.add(position);
                        }
                    }
                    else if (gradeNumbers.contains(position))
                    {
                        gradeNumbers.remove(position);
                    }
                    }
                });
                gBuilder.setCancelable(false);
                gBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String grades = "";
                        for (int i = 0; i < gradeNumbers.size(); i++)
                        {
                            grades = grades + gradeList[gradeNumbers.get(i)];
                            if (i != gradeNumbers.size()-1)
                            {
                                grades = grades + ", ";
                            }
                        }
                        gradesSelected.setText(grades);
                    }
                });
                gBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                gBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        for (int i = 0; i <checkGrades.length;i++)
                        {
                            checkGrades[i] = false;
                            gradeNumbers.clear();
                            gradesSelected.setText("");
                        }

                    }
                });
                AlertDialog gDialog = gBuilder.create();
                gDialog.show();
            }
        });


        Button openButton = (Button) findViewById(R.id.programme_app_open);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFragment openDate = new DatePickerFragment();
                openDate.show(getSupportFragmentManager(), "application open date");
            }
        });

        Button closeButton = (Button) findViewById(R.id.programme_app_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogFragment closeDate = new DatePickerFragment();
                closeDate.show(getSupportFragmentManager(), "application closing date");

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        Calendar c  = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String openDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String closeDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

    }

    public void checkButton (View view){
        int radioId = honoursGroup.getCheckedRadioButtonId();

        honours = findViewById(radioId);
    }

    public void RegisterEmployer(final String companyName, final String companyEmail, final String gradContactName, final String telNumber,
                                 final String openDate, final String closeDate, final String employmentStartDate, final String universitiesPref,
                                 final String coursesPref, final String gradeAverage, final String applicantsSpecs, final String progOverview,
                                 final String applyDetails, final String anythingElse, final String employerPassword){

        pd.setTitle("Registering");
        pd.setMessage("Please wait while we register your account...");
        pd.show();
        pd.setCanceledOnTouchOutside(true);

        eAuth.createUserWithEmailAndPassword(companyEmail, employerPassword).addOnCompleteListener(EmployerProfileCreateActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {

                    HashMap<String, Object> employerMap = new HashMap<>();
                    employerMap.put("id", currentEmployerId);
                    employerMap.put("company", companyName);
                    employerMap.put("email address", companyEmail);
                    employerMap.put("grad contact name", gradContactName);
                    employerMap.put("telephone", telNumber);
                    employerMap.put("open date", openDate);
                    employerMap.put("close date", closeDate);
                    employerMap.put("employment start date", employmentStartDate);
                    employerMap.put("universities preferred", universitiesPref);
                    employerMap.put("courses preferred", coursesPref);
                    employerMap.put("grade average", gradeAverage);
                    employerMap.put("specifications", applicantsSpecs);
                    employerMap.put("overview", progOverview);
                    employerMap.put("how to apply", applyDetails);
                    employerMap.put("anything else?", anythingElse);
                    employerMap.put("employer password", employerPassword);
                    employerReference.updateChildren(employerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                pd.dismiss();
                                Intent intent = new Intent(EmployerProfileCreateActivity.this, EmployerLoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });

                }
                else 
                {
                    Toast.makeText(EmployerProfileCreateActivity.this, "Error: " + task.getException().getMessage() + "!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
