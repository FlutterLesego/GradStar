package com.example.gradstar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    String[] gradeList;
    boolean[] checkGrades;
    ArrayList<Integer> gradeNumbers = new ArrayList<>();

    String[] coursesList;
    boolean[] checkCourses;
    ArrayList<Integer> coursesNames = new ArrayList<>();

    String[] universitiesList;
    boolean[] checkUniversities;
    ArrayList<Integer> universitiesNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_profile_create);

        gradeButton = (Button) findViewById(R.id.grade_btn);

        gradeList = getResources().getStringArray(R.array.grade_average);
        checkGrades = new boolean[gradeList.length];

        honoursGroup = findViewById(R.id.honours);

        coursesButton = (Button) findViewById(R.id.courses_btn);

        coursesList = getResources().getStringArray(R.array.courses_preffered);
        checkCourses = new boolean[coursesList.length];

        universitiesButton = (Button) findViewById(R.id.universities_btn);

        universitiesList = getResources().getStringArray(R.array.universities_preferred);
        checkUniversities = new boolean[universitiesList.length];

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

}
