package com.example.gradstar;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class SponsorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        Element adsElement = new Element();

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.accenture)
                .setImage(R.drawable.mentorship)
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("yusuf@blackbark.co.za")
                .addWebsite("http://gradstar.co.za/")
                .addFacebook("https://www.facebook.com/GradStarSA/")
                .addTwitter("https://twitter.com/gradstarsa")
                .addYoutube("UC92el_bvMKzJXmoP_YYEdPw")
                .create();
        setContentView(aboutPage);
    }
}
