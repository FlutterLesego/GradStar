package com.example.gradstar;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminPanelActivity extends AppCompatActivity
{
    private DatabaseReference PostsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private Toolbar aToolBar;
    private ImageButton AddNewPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        aToolBar = (Toolbar) findViewById(R.id.admin_toolbar);
        setSupportActionBar(aToolBar);
        getSupportActionBar().setTitle("Admin Panel");

        recyclerView = findViewById(R.id.all_posts_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AddNewPostButton = (ImageButton) findViewById(R.id.add_post_btn);

        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        AddNewPostButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SendUserToCategoriesActivity();
            }
        });

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.admin_bottom_nav_bar);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.admin_nav_home_bottom);

        //perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.admin_nav_home_bottom:
                        return true;
                    case R.id.admin_nav_social_bottom:
                        Intent adminSocialIntent = new Intent(AdminPanelActivity.this, AdminSocialActivity.class);
                        startActivity(adminSocialIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_employer_bottom:
                        Intent adminEmployerIntent = new Intent(AdminPanelActivity.this, AdminProfessionalActivity.class);
                        startActivity(adminEmployerIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_notifications_bottom:
                        Intent adminNotificationsIntent = new Intent(AdminPanelActivity.this, AdminNotificationsActivity.class);
                        startActivity(adminNotificationsIntent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin_nav_universities_bottom:
                        Intent adminUniversitiesIntent = new Intent(AdminPanelActivity.this, AdminUniversitiesActivity.class);
                        startActivity(adminUniversitiesIntent);
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Posts> options =
                new FirebaseRecyclerOptions.Builder<Posts>().setQuery(PostsRef, Posts.class)
                .build();

        FirebaseRecyclerAdapter<Posts, PostsViewHolder> adapter =
                new FirebaseRecyclerAdapter<Posts, PostsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PostsViewHolder postsViewHolder, int i, @NonNull Posts posts)
                    {
                        postsViewHolder.txtUser.setText(posts.getName());
                        postsViewHolder.txtDescription.setText(posts.getPostDescription());
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
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void SendUserToCategoriesActivity()
    {
        Intent addNewPostIntent = new Intent(AdminPanelActivity.this,FeedActivity.class);
        startActivity(addNewPostIntent);
    }
}
