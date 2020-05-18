package com.example.gradstar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

public class StudentsHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    CurvedBottomNavigationView bottomNavigationView;
    VectorMasterView homeFab, socialFab,jobsFab,notificationFab,universityFab;

    RelativeLayout lin_id;
    PathModel outline;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView postList;
    private Toolbar mToolBar;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_home);

        //View
        bottomNavigationView = (CurvedBottomNavigationView)findViewById(R.id.bottom_nav);
        homeFab = (VectorMasterView)findViewById(R.id.home_fab);
        socialFab = (VectorMasterView)findViewById(R.id.social_fab);
        jobsFab = (VectorMasterView)findViewById(R.id.jobs_fab);
        notificationFab = (VectorMasterView)findViewById(R.id.notification_fab);
        universityFab = (VectorMasterView)findViewById(R.id.university_fab);

        lin_id = (RelativeLayout)findViewById(R.id.lin_id);
        bottomNavigationView.inflateMenu(R.menu.main_menu);

        //event for bottom nav
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();

        //Toolbar
        mToolBar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Home");

        Toast.makeText(this, "Welcome to the GradStar App!", Toast.LENGTH_SHORT).show();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(StudentsHomeActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                UserMenuSelector(menuItem);
                return false;
            }
        });
    }

    //Selector/Director
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.nav_user_profile_name:
                Intent profileIntent = new Intent(StudentsHomeActivity.this,UserProfileActivity.class);
                startActivity(profileIntent);

                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_universities:
                Toast.makeText(this, "Universities", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_employer:
                Toast.makeText(this, "Employers", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sponsors:
                Toast.makeText(this, "Sponsors", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_events:
                Toast.makeText(this, "Events", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                loadingBar.setTitle("Logging out...");
                loadingBar.setMessage("Just a second");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                Intent intent = new Intent(StudentsHomeActivity.this,LoginActivity.class);
                startActivity(intent);

                Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.action_social:
                Toast.makeText(this, "Social", Toast.LENGTH_SHORT).show();

                //animation
                draw(6);
                //find the correct path using name
                lin_id.setX(bottomNavigationView.mFistCurveControlPoint1.x);
                socialFab.setVisibility(View.VISIBLE);
                jobsFab.setVisibility(View.GONE);
                homeFab.setVisibility(View.GONE);
                notificationFab.setVisibility(View.GONE);
                universityFab.setVisibility(View.GONE);
                drawAnimation(socialFab);
                break;

            case R.id.action_jobs:
                Toast.makeText(this, "Professional", Toast.LENGTH_SHORT).show();

                //animation
                draw(4);
                //find the correct path using name
                lin_id.setX(bottomNavigationView.mFistCurveControlPoint1.x);
                socialFab.setVisibility(View.GONE);
                jobsFab.setVisibility(View.VISIBLE);
                homeFab.setVisibility(View.GONE);
                notificationFab.setVisibility(View.GONE);
                universityFab.setVisibility(View.GONE);
                drawAnimation(socialFab);
                break;

            case R.id.action_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();

                //animation
                draw(2);
                //find the correct path using name
                lin_id.setX(bottomNavigationView.mFistCurveControlPoint1.x);
                socialFab.setVisibility(View.GONE);
                jobsFab.setVisibility(View.GONE);
                homeFab.setVisibility(View.VISIBLE);
                notificationFab.setVisibility(View.GONE);
                universityFab.setVisibility(View.GONE);
                drawAnimation(homeFab);
                break;

            case R.id.action_notification:
                Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show();

                //animation
                draw(4);
                //find the correct path using name
                lin_id.setX(bottomNavigationView.mFistCurveControlPoint1.x);
                socialFab.setVisibility(View.GONE);
                jobsFab.setVisibility(View.GONE);
                homeFab.setVisibility(View.GONE);
                notificationFab.setVisibility(View.VISIBLE);
                universityFab.setVisibility(View.GONE);
                drawAnimation(notificationFab);
                break;

            case R.id.action_universities:
                Toast.makeText(this, "Universities", Toast.LENGTH_SHORT).show();

                //animation
                draw();
                //find the correct path using name
                lin_id.setX(bottomNavigationView.mFistCurveControlPoint1.x);
                socialFab.setVisibility(View.GONE);
                jobsFab.setVisibility(View.GONE);
                homeFab.setVisibility(View.GONE);
                notificationFab.setVisibility(View.GONE);
                universityFab.setVisibility(View.VISIBLE);
                drawAnimation(universityFab);
                break;
        }
        return true;
    }

    private void draw()
    {
        bottomNavigationView.mFistCurveStartPoint.set((bottomNavigationView.mNavigationBarWidth *10/12)
        - (bottomNavigationView.CURVE_CIRCLE_RADIUS*2)
        - (bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);

        bottomNavigationView.mFirstCurveEndPoint.set(bottomNavigationView.mNavigationBarWidth*10/12,
                bottomNavigationView.CURVE_CIRCLE_RADIUS
                + (bottomNavigationView.CURVE_CIRCLE_RADIUS/4));

        bottomNavigationView.mSecondCurveStartPoint = bottomNavigationView.mFirstCurveEndPoint;
        bottomNavigationView.mSecondCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth*10/12)
                + (bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+(bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);

        bottomNavigationView.mFistCurveControlPoint1.set(bottomNavigationView.mFistCurveStartPoint.x
                        +bottomNavigationView.CURVE_CIRCLE_RADIUS + (bottomNavigationView.CURVE_CIRCLE_RADIUS/4),
                bottomNavigationView.mFistCurveStartPoint.y);

        bottomNavigationView.mFirstCurveControlPoint2.set(bottomNavigationView.mFirstCurveEndPoint.x-
                        (bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+bottomNavigationView.CURVE_CIRCLE_RADIUS,
                bottomNavigationView.mFirstCurveEndPoint.y);

        //second
        bottomNavigationView.mSecondCurveControlPoint1.set(bottomNavigationView.mSecondCurveStartPoint.x
                        +(bottomNavigationView.CURVE_CIRCLE_RADIUS*2) - bottomNavigationView.CURVE_CIRCLE_RADIUS,
                bottomNavigationView.mSecondCurveStartPoint.y);
        bottomNavigationView.mSecondCurveControlPoint2.set(bottomNavigationView.mSecondCurveEndPoint.x-
                (bottomNavigationView.CURVE_CIRCLE_RADIUS + (bottomNavigationView.CURVE_CIRCLE_RADIUS/4)),bottomNavigationView.mSecondCurveEndPoint.y);


    }

    private void drawAnimation(final VectorMasterView socialFab)
    {
        outline = socialFab.getPathModelByName("outline");
        outline.setStrokeColor(Color.WHITE);
        outline.setTrimPathEnd(0.0f);
        //Init valueAnimator
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f,1.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                outline.setTrimPathEnd((Float)valueAnimator.getAnimatedValue());
                socialFab.update();

            }
        });
        valueAnimator.start();
    }

    private void draw(int i)
    {
        bottomNavigationView.mFistCurveStartPoint.set((bottomNavigationView.mNavigationBarWidth/i)
        -(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)-(bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);

        bottomNavigationView.mFirstCurveEndPoint.set(bottomNavigationView.mNavigationBarWidth/i,bottomNavigationView.CURVE_CIRCLE_RADIUS
        + (bottomNavigationView.CURVE_CIRCLE_RADIUS/4));

        bottomNavigationView.mSecondCurveStartPoint = bottomNavigationView.mFirstCurveEndPoint;
        bottomNavigationView.mSecondCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth/i)
        + (bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+(bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);

        bottomNavigationView.mFistCurveControlPoint1.set(bottomNavigationView.mFistCurveStartPoint.x
        +bottomNavigationView.CURVE_CIRCLE_RADIUS + (bottomNavigationView.CURVE_CIRCLE_RADIUS/4),
                bottomNavigationView.mFistCurveStartPoint.y);

        bottomNavigationView.mFirstCurveControlPoint2.set(bottomNavigationView.mFirstCurveEndPoint.x-
                (bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+bottomNavigationView.CURVE_CIRCLE_RADIUS,
                bottomNavigationView.mFirstCurveEndPoint.y);

        //second
        bottomNavigationView.mSecondCurveControlPoint1.set(bottomNavigationView.mSecondCurveStartPoint.x
                        +(bottomNavigationView.CURVE_CIRCLE_RADIUS*2) - bottomNavigationView.CURVE_CIRCLE_RADIUS,
                bottomNavigationView.mSecondCurveStartPoint.y);
        bottomNavigationView.mSecondCurveControlPoint2.set(bottomNavigationView.mSecondCurveEndPoint.x-
                (bottomNavigationView.CURVE_CIRCLE_RADIUS + (bottomNavigationView.CURVE_CIRCLE_RADIUS/4)),bottomNavigationView.mSecondCurveEndPoint.y);

    }
}
