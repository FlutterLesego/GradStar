package com.example.gradstar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradstar.Model.Posts;
import com.example.gradstar.ViewHolder.PostsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class StudentsHomeActivity extends AppCompatActivity
{
    private NavigationView navigationView;
    private RecyclerView postList;
    private Toolbar mToolBar;
    private ProgressDialog loadingBar;
    private DatabaseReference UserRef, PostsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_home);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        postList = (RecyclerView) findViewById(R.id.all_posts_list);
        postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        //set Home
        bottomNavigationView.setSelectedItemId(R.id.nav_home_bottom);

        //itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home_bottom:
                        return true;

                    case R.id.nav_social_bottom:
                        startActivity(new Intent(getApplicationContext()
                                ,SocialActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_employer_bottom:
                        startActivity(new Intent(getApplicationContext()
                                ,ProfessionalActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_notifications_bottom:
                        startActivity(new Intent(getApplicationContext()
                                ,NotificationsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_universities_bottom:
                        startActivity(new Intent(getApplicationContext()
                                ,UniversitiesActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });



        FloatingActionButton fab_signout = findViewById(R.id.fab_signout_btn);

        fab_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent signoutIntent = new Intent(StudentsHomeActivity.this,LoginActivity.class);
                startActivity(signoutIntent);

            }
        });

        FloatingActionButton fab_settings = findViewById(R.id.fab_settings_btn);

        fab_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent settingsIntent = new Intent(StudentsHomeActivity.this,SettingsActivity.class);
                startActivity(settingsIntent);

            }
        });

        FloatingActionButton fab_about = findViewById(R.id.fab_about_btn);

        fab_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent aboutIntent = new Intent(StudentsHomeActivity.this,AboutActivity.class);
                startActivity(aboutIntent);

            }
        });

        FloatingActionButton fab_sponsors = findViewById(R.id.fab_sponsors_btn);

        fab_sponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent sponsorsIntent = new Intent(StudentsHomeActivity.this,SponsorsActivity.class);
                startActivity(sponsorsIntent);

            }
        });

        FloatingActionButton fab_events = findViewById(R.id.fab_events_btn);

        fab_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent eventsIntent = new Intent(StudentsHomeActivity.this,EventsActivity.class);
                startActivity(eventsIntent);

            }
        });

        FloatingActionButton fab_profile = findViewById(R.id.fab_profile_btn);

        fab_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent profileIntent = new Intent(StudentsHomeActivity.this,UserProfileActivity.class);
                startActivity(profileIntent);

            }
        });


        ImageButton inboxImageButton = findViewById(R.id.inbox_button);

        inboxImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent inboxIntent = new Intent(StudentsHomeActivity.this, InboxActivity.class);
                startActivity(inboxIntent);

            }
        });

        onStart();

        //Toolbar
       // mToolBar = (Toolbar) findViewById(R.id.main_page_toolbar);
       // setSupportActionBar(mToolBar);


        //drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        //actionBarDrawerToggle = new ActionBarDrawerToggle(StudentsHomeActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        //drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_profile);
        //navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //View navView = navigationView.inflateHeaderView(R.layout.navigation_header);


        //navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            //@Override
            //public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            //{
            //    UserMenuSelector(menuItem);
            //    return false;
           // }
        //});
    //}

    //Selector/Director
    //@Override
    //public boolean onOptionsItemSelected(@NonNull MenuItem item)
    //{
    //    if (actionBarDrawerToggle.onOptionsItemSelected(item))
      //  {
        //    return true;
        //}

        //return super.onOptionsItemSelected(item);
    //}

    //private void UserMenuSelector(MenuItem menuItem)
  //  {
   //     switch (menuItem.getItemId())
   //     {
   //         case R.id.nav_user_profile_name:
    //            Intent profileIntent = new Intent(StudentsHomeActivity.this,UserProfileActivity.class);
   //             startActivity(profileIntent);

   //             Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
    //            break;
    //        case R.id.nav_sponsors:
    //            Toast.makeText(this, "Sponsors", Toast.LENGTH_SHORT).show();
    //            break;
    //        case R.id.nav_events:
    //            Intent eventsIntent = new Intent(StudentsHomeActivity.this,EventsActivity.class);
    //            startActivity(eventsIntent);
    //            break;
    //        case R.id.nav_about:
     //           Intent aboutIntent = new Intent(StudentsHomeActivity.this,AboutActivity.class);
     //           startActivity(aboutIntent);
     //           break;
     //       case R.id.nav_settings:
     //           Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
     //           break;
      //      case R.id.nav_logout:

     //           Intent logoutIntent = new Intent(StudentsHomeActivity.this,LoginActivity.class);
      //          startActivity(logoutIntent);
      //          break;
      //  }
    }


    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<com.example.gradstar.Model.Posts> options =
                new FirebaseRecyclerOptions.Builder<com.example.gradstar.Model.Posts>().setQuery(PostsRef, com.example.gradstar.Model.Posts.class)
                        .build();

        FirebaseRecyclerAdapter<com.example.gradstar.Model.Posts, PostsViewHolder> adapter =
                new FirebaseRecyclerAdapter<com.example.gradstar.Model.Posts, PostsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PostsViewHolder postsViewHolder, int i, @NonNull Posts posts)
                    {
                        postsViewHolder.txtUser.setText(posts.getName());
                        postsViewHolder.txtDescription.setText(posts.getPostDescription());
                        Picasso.get().load(posts.getProfileImage()).into(postsViewHolder.userProfileImage);
                        Picasso.get().load(posts.getPostImage()).into(postsViewHolder.postImage);

                    }

                    @NonNull
                    @Override
                    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_layout, parent, false);
                        PostsViewHolder holder = new PostsViewHolder(view);
                        return holder;
                    }
                };
        postList.setAdapter(adapter);
        adapter.startListening();
    }
}
