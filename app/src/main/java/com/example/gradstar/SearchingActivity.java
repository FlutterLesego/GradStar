package com.example.gradstar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchingActivity extends AppCompatActivity
{
    private String[] locationList, employmentDateSearchList;
    private TextView industryClick, locationClick, employmentDateClick;
    private TextView industrySelected, locationSelected, employmentDateSelected;
    private ImageView backArrow, searchingClick;
    private EditText searchingET;

    String[] industryList;
    boolean[] checkIndustry;
    ArrayList<Integer> industries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        industryClick = findViewById(R.id.industry_tv);
        locationClick = findViewById(R.id.location_tv);
        searchingClick =findViewById(R.id.search_btn);

        industrySelected = findViewById(R.id.industry_selected);
        locationSelected =findViewById(R.id.location_selected);
        employmentDateSelected = findViewById(R.id.employment_date_selected);

        employmentDateClick = findViewById(R.id.employment_date_tv);
        backArrow = findViewById(R.id.back_arrow);
        searchingET = findViewById(R.id.searching);

        industryList = getResources().getStringArray(R.array.course_list);
        checkIndustry = new boolean[industryList.length];

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent searchActivityIntent = new Intent(SearchingActivity.this, ProfessionalActivity.class);
                startActivity(searchActivityIntent);

            }
        });

        employmentDateClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //date list
                employmentDateSearchList = new String[] {"2021", "2022","2023","2024","2025","2026","2027", "2028", "2029", "2030"};
                AlertDialog.Builder edsBuilder = new AlertDialog.Builder(SearchingActivity.this);
                edsBuilder.setSingleChoiceItems(employmentDateSearchList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        employmentDateSelected.setText(employmentDateSearchList[which]);
                        dialog.dismiss();
                    }
                });
                edsBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog edsDialog = edsBuilder.create();
                edsDialog.show();
            }
        });

        locationClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //province list
                locationList = new String[] { "Eastern Cape", "Free State", "Gauteng","North West","Northern Cape","Western Cape","Polokwane","KwaZulu-Natal", "Mpumalanga"};
                AlertDialog.Builder edsBuilder = new AlertDialog.Builder(SearchingActivity.this);
                edsBuilder.setSingleChoiceItems(locationList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        locationSelected.setText(locationList[which]);
                        dialog.dismiss();
                    }
                });
                edsBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog edsDialog = edsBuilder.create();
                edsDialog.show();
            }
        });

        industryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder iBuilder = new AlertDialog.Builder(SearchingActivity.this);
                iBuilder.setMultiChoiceItems(industryList, checkIndustry, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked)
                    {if (isChecked)
                    {
                        if (!industries.contains(position))
                        {
                            industries.add(position);
                        }
                    }
                    else if (industries.contains(position))
                    {
                        industries.remove(position);
                    }
                    }
                });
                iBuilder.setCancelable(false);
                iBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String industry = "";
                        for (int i = 0; i < industries.size(); i++)
                        {
                            industry = industry + industryList[industries.get(i)];
                            if (i != industries.size()-1)
                            {
                                industry = industry + ", ";
                            }
                        }
                        industrySelected.setText(industry);
                    }
                });
                iBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                iBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        for (int i = 0; i <checkIndustry.length;i++)
                        {
                            checkIndustry[i] = false;
                            industries.clear();
                            industrySelected.setText("");
                        }

                    }
                });
                AlertDialog iDialog = iBuilder.create();
                iDialog.show();

            }
        });

    }
}
