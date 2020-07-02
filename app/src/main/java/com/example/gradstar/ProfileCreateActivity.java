package com.example.gradstar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileCreateActivity extends AppCompatActivity{

    String[] universityList;
    boolean[] checkUniversity;
    ArrayList<Integer> universityName = new ArrayList<>();

    private String [] employmentDateList, courseList, gradeList, ethnicityList, genderList, disabilityList, bursaryList;
    private EditText InputName, InputIdNumber, InputLanguage, InputStudentNumber, InputHobbies, InputNationality, InputMajor;
    private EditText InputJobs, InputTraits, InputCompanies, InputQualification, InputHonours, InputDisability, InputBursary,
    InputMobileNumber, InputEmailAddress, InputWhatsapp, InputPassword, InputConfirmPassword, InputConfirmEmail;
    private TextView EmploymentDateDisplay, UniversityDisplay, CourseDisplay;
    private TextView GradeDisplay, EthnicityDisplay, GenderDisplay, DisabilityDisplay, BursaryDisplay;
    private Button ProceedButton, EmploymentDate, UniversityButton ;
    private Button CourseButton, GradeButton, EthnicityButton, GenderButton, DisabilityButton, BursaryButton;
    private ProgressDialog loader;

    FirebaseAuth uAuth;
    String currentUserId;
    DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create);

        EmploymentDate = (Button) findViewById(R.id.employment_date);
        EmploymentDateDisplay = (TextView) findViewById(R.id.employment_date_display);

        UniversityButton = findViewById(R.id.universities_btn);
        UniversityDisplay = findViewById(R.id.universities_tv);

        universityList = getResources().getStringArray(R.array.universities_preferred);
        checkUniversity = new boolean[universityList.length];

        CourseButton = findViewById(R.id.courses_btn);
        CourseDisplay = findViewById(R.id.course_tv);

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

        ProceedButton = findViewById(R.id.proceed_btn);
        InputName = findViewById(R.id.register_name_input);
        InputIdNumber = findViewById(R.id.register_id_number);
        InputLanguage = findViewById(R.id.register_language);
        InputStudentNumber = findViewById(R.id.register_stud_card_number);
        InputHobbies= findViewById(R.id.register_interests);
        InputJobs = findViewById(R.id.register_part_time);
        InputTraits= findViewById(R.id.register_traits);
        InputCompanies = findViewById(R.id.register_brands);
        InputNationality = findViewById(R.id.nationality_edit);
        InputQualification = findViewById(R.id.qualification_edit);
        InputMajor = findViewById(R.id.major_edit);
        InputHonours = findViewById(R.id.honours_edit);
        InputDisability = findViewById(R.id.disability_edit);
        InputBursary = findViewById(R.id.bursary_edit);
        InputMobileNumber = findViewById(R.id.register_mobile);
        InputEmailAddress = findViewById(R.id.register_email);
        InputWhatsapp = findViewById(R.id.register_whatsapp);
        InputPassword = findViewById(R.id.register_password);
        InputConfirmEmail = findViewById(R.id.confirm_email);
        InputConfirmPassword = findViewById(R.id.confirm_password);

        uAuth = FirebaseAuth.getInstance();
        currentUserId = uAuth.getCurrentUser().getUid();
        userReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

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

        /*UniversityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //universities
                AlertDialog.Builder uniBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                uniBuilder.setTitle("Universities preferred:");
                uniBuilder.setMultiChoiceItems(universityList, checkUniversity, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {if (isChecked)
                    {
                        if (!universityName.contains(position))
                        {
                            universityName.add(position);
                        }
                    }
                    else if (universityName.contains(position))
                    {
                        universityName.remove(position);
                    }
                    }
                });
                uniBuilder.setCancelable(false);
                uniBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String university = "";
                        for (int i = 0; i < universityName.size(); i++)
                        {
                            university = university + universityList[universityName.get(i)];
                            if (i != universityName.size()-1)
                            {
                                university = university + ", ";
                            }
                        }
                        UniversityDisplay.setText(university);
                    }
                });
                uniBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                uniBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        for (int i = 0; i <checkUniversity.length;i++)
                        {
                            checkUniversity[i] = false;
                            universityName.clear();
                            UniversityDisplay.setText("");
                        }

                    }
                });
                AlertDialog uniDialog = uniBuilder.create();
                uniDialog.show();
            }
        });

        /*CourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                courseList = new String[]{"Accounting and related services", "Aerospace, Aeronautical and astronautical engineering", "Agricultural/biological engineering and bio-engineering",
                        "Agriculture, agricultural operations and related sciences", "Architecture"};
                AlertDialog.Builder coBuilder = new AlertDialog.Builder(ProfileCreateActivity.this);
                coBuilder.setTitle("Course:");
                coBuilder.setSingleChoiceItems(courseList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CourseDisplay.setText(courseList[which]);
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
        });*/

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
                ProgressDialog pd = new ProgressDialog(ProfileCreateActivity.this);
                pd.setTitle("Creating Account");
                pd.setMessage("Please wait while we verify your credentials");
                pd.show();

                String str_InputName = InputName.getText().toString();
                String str_InputIdNumber = InputIdNumber.getText().toString();
                String str_InputLanguage = InputLanguage.getText().toString();
                String str_InputStudentNumber = InputStudentNumber.getText().toString();
                String str_InputHobbies = InputHobbies.getText().toString();
                String str_InputJobs = InputJobs.getText().toString();
                String str_InputTraits = InputTraits.getText().toString();
                String str_InputCompanies = InputCompanies.getText().toString();
                String str_InputNationality = InputNationality.getText().toString();
                String str_InputQualification = InputQualification.getText().toString();
                String str_InputMajor = InputMajor.getText().toString();
                String str_InputHonours = InputHonours.getText().toString();
                String str_InputDisability = InputDisability.getText().toString();
                String str_InputBursary = InputBursary.getText().toString();
                String str_InputMobileNumber = InputMobileNumber.getText().toString();
                String str_InputEmailAddress = InputEmailAddress.getText().toString();
                String str_InputWhatsapp = InputWhatsapp.getText().toString();
                String str_InputPassword = InputPassword.getText().toString();
                String str_InputConfirmEmail = InputConfirmEmail.getText().toString();
                String str_InputConfirmPassword = InputConfirmPassword.getText().toString();
                String dis_GradeDisplay = GradeDisplay.getText().toString();
                String dis_EmploymentDateDisplay = EmploymentDateDisplay.getText().toString();
                /*String dis_UniversityDisplay = UniversityDisplay.getText().toString();
                String dis_CourseDisplay = CourseDisplay.getText().toString();*/
                String dis_EthnicityDisplay = EthnicityDisplay.getText().toString();
                String dis_GenderDisplay = GenderDisplay.getText().toString();
                String dis_DisabilityDisplay = DisabilityDisplay.getText().toString();
                String dis_BursaryDisplay = BursaryDisplay.getText().toString();
                

                if (TextUtils.isEmpty(str_InputName)||TextUtils.isEmpty(str_InputIdNumber)||TextUtils.isEmpty(str_InputLanguage)||TextUtils.isEmpty(str_InputStudentNumber)||
                        TextUtils.isEmpty(str_InputHobbies)||TextUtils.isEmpty(str_InputJobs)||TextUtils.isEmpty(str_InputTraits)||TextUtils.isEmpty(str_InputCompanies)||
                        TextUtils.isEmpty(str_InputNationality)||TextUtils.isEmpty(str_InputQualification)||TextUtils.isEmpty(str_InputMajor)||TextUtils.isEmpty(str_InputHonours)||TextUtils.isEmpty(str_InputMobileNumber)||TextUtils.isEmpty(str_InputEmailAddress)||
                        TextUtils.isEmpty(str_InputConfirmEmail)||TextUtils.isEmpty(str_InputPassword)||TextUtils.isEmpty(str_InputConfirmPassword) ||TextUtils.isEmpty(dis_GradeDisplay)
                        ||TextUtils.isEmpty(dis_EmploymentDateDisplay)||TextUtils.isEmpty(dis_EthnicityDisplay)
                        ||TextUtils.isEmpty(dis_GenderDisplay) ||TextUtils.isEmpty(dis_DisabilityDisplay) ||TextUtils.isEmpty(dis_BursaryDisplay) )
                {
                    pd.dismiss();
                    Toast.makeText(ProfileCreateActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }
                else if (!str_InputEmailAddress.equals(str_InputConfirmEmail))
                {
                    pd.dismiss();
                    Toast.makeText(ProfileCreateActivity.this, "Email addresses do not match", Toast.LENGTH_SHORT).show();
                }
                else if (!str_InputPassword.equals(str_InputConfirmPassword))
                {
                    pd.dismiss();
                    Toast.makeText(ProfileCreateActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else if (str_InputPassword.length()<6)
                {
                    pd.dismiss();
                    Toast.makeText(ProfileCreateActivity.this, "Password must have at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else if (dis_DisabilityDisplay.startsWith("y")|| dis_DisabilityDisplay.startsWith("Y") && str_InputDisability.isEmpty())
                {
                    pd.dismiss();
                    Toast.makeText(ProfileCreateActivity.this, "Please explain your disability", Toast.LENGTH_SHORT).show();
                }
                else if (dis_BursaryDisplay.startsWith("y")|| dis_BursaryDisplay.startsWith("Y") && str_InputBursary.isEmpty())
                {
                    pd.dismiss();
                    Toast.makeText(ProfileCreateActivity.this, "Bursary provider required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isDigitsOnly(str_InputPassword))
                {
                    pd.dismiss();
                    Toast.makeText(ProfileCreateActivity.this, "Password must contain at least one letter", Toast.LENGTH_SHORT).show();
                }
                else {

                    RegisterUser(str_InputName,str_InputIdNumber,str_InputLanguage,str_InputStudentNumber, str_InputHobbies, str_InputJobs,
                            str_InputTraits, str_InputCompanies, str_InputNationality, str_InputQualification, str_InputMajor, str_InputHonours,
                            str_InputDisability, str_InputBursary, str_InputMobileNumber, str_InputEmailAddress, str_InputConfirmEmail,
                            str_InputPassword, str_InputConfirmPassword, str_InputWhatsapp, dis_GradeDisplay, dis_EmploymentDateDisplay, dis_EthnicityDisplay, dis_GenderDisplay, dis_DisabilityDisplay, dis_BursaryDisplay);
                }
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
                dBuilder.setTitle("Disability:");
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
    }

    private void RegisterUser(final String str_inputName, final String str_inputIdNumber, final String str_inputLanguage, final String str_inputStudentNumber,
                              final String str_inputHobbies, final String str_inputJobs, final String str_inputTraits, final String str_inputCompanies,
                              final String str_inputNationality, final String str_inputQualification, final String str_inputMajor, final String str_inputHonours,
                              final String str_inputDisability, final String str_inputBursary, final String str_inputMobileNumber, final String str_inputEmailAddress,
                              final String str_inputConfirmEmail, final String str_inputPassword, final String str_inputConfirmPassword, final String str_inputWhatsapp,
                              final String dis_gradeDisplay, final String dis_employmentDateDisplay, final String dis_ethnicityDisplay, final String dis_genderDisplay, final String dis_disabilityDisplay, final String dis_bursaryDisplay)
    {
        final ProgressDialog pd = new ProgressDialog(ProfileCreateActivity.this);
        pd.setTitle("Registering");
        pd.setMessage("Please wait while we register your account");
        pd.show();
        pd.setCanceledOnTouchOutside(true);

        uAuth.createUserWithEmailAndPassword(str_inputEmailAddress, str_inputPassword)
                .addOnCompleteListener(ProfileCreateActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {

                            HashMap<String, Object> usersMap = new HashMap<>();
                            usersMap.put("user id", currentUserId);
                            usersMap.put("full names", str_inputName);
                            usersMap.put("id number", str_inputIdNumber);
                            usersMap.put("language", str_inputLanguage);
                            usersMap.put("student card number", str_inputStudentNumber);
                            usersMap.put("hobbies", str_inputHobbies);
                            usersMap.put("part time jobs", str_inputJobs);
                            usersMap.put("characteristics", str_inputTraits);
                            usersMap.put("companies", str_inputCompanies);
                            usersMap.put("nationality", str_inputNationality);
                            usersMap.put("qualification", str_inputQualification);
                            usersMap.put("major", str_inputMajor);
                            usersMap.put("honours", str_inputHonours);
                            usersMap.put("disability type", str_inputDisability);
                            usersMap.put("bursary type", str_inputBursary);
                            usersMap.put("mobile number", str_inputMobileNumber);
                            usersMap.put("email address", str_inputEmailAddress);
                            usersMap.put("confirm email address", str_inputConfirmEmail);
                            usersMap.put("password", str_inputPassword);
                            usersMap.put("confirm password", str_inputConfirmPassword);
                            usersMap.put("whatsapp", str_inputWhatsapp);
                            usersMap.put("grade average", dis_gradeDisplay);
                            usersMap.put("year available", dis_employmentDateDisplay);
                           /*hashMap.put("university", dis_universityDisplay);
                            hashMap.put("course", dis_courseDisplay);*/
                            usersMap.put("ethnicity", dis_ethnicityDisplay);
                            usersMap.put("gender", dis_genderDisplay);
                            usersMap.put("disability", dis_disabilityDisplay);
                            usersMap.put("bursary", dis_bursaryDisplay);

                            userReference.setValue(usersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        pd.dismiss();
                                        Toast.makeText(ProfileCreateActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProfileCreateActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        pd.dismiss();
                                        Toast.makeText(ProfileCreateActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                        else
                        {
                            pd.dismiss();
                            Toast.makeText(ProfileCreateActivity.this, "Account with this email address already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
