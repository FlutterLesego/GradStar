package com.example.gradstar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminCategoryActivity extends AppCompatActivity
{
    private CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    private CardView cardView6, cardView7, cardView8, cardView9, cardView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        cardView1 = (CardView) findViewById(R.id.card_view1);
        cardView2 = (CardView) findViewById(R.id.card_view2);
        cardView3 = (CardView) findViewById(R.id.card_view3);
        cardView4 = (CardView) findViewById(R.id.card_view4);
        cardView5 = (CardView) findViewById(R.id.card_view5);
        cardView6 = (CardView) findViewById(R.id.card_view6);
        cardView7 = (CardView) findViewById(R.id.card_view7);
        cardView8 = (CardView) findViewById(R.id.card_view8);
        cardView9 = (CardView) findViewById(R.id.card_view9);
        cardView10 = (CardView) findViewById(R.id.card_view10);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SendUserToPostActivity();

            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SendUserToPostActivity();

            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SendUserToPostActivity();

            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SendUserToPostActivity();

            }
        });


    }

    private void SendUserToPostActivity()
    {
        Intent postIntent = new Intent(AdminCategoryActivity.this, PostActivity.class);
        startActivity(postIntent);
    }
}
