package com.example.gradstar;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Element adsElement = new Element();
        adsElement.setTitle("Version 1.0");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.applogo_white)
                .setDescription("About us")
                .addItem(new Element().setTitle("GradStar is our programme that recognizes the Top 100 students across the country based on leadership qualities and readiness for the workplace. " +
                        "We received over 3,500 entries in 2016 and narrowed then down to the top 100, ‘then the 10 of the Finest’. Many of the Top 100 recognized students have received job offers from major employers as a result of being recognized through this programme.\n" +
                        "\n" +
                        "All varsity careers centres from across the country are contacted to market the GradStar programme to their students, ensuring representation from across the country and from all disciplines. We also advertise in relevant press and online forae.\n" +
                        "\n" +
                        "The students go through a rigorous 4 phase judging process, culminating in a day of workshops hosted by potential employers (sponsors). All flights and accommodation for the students to attend the judging and celebratory Gala Dinner are paid for by BlackBark Productions.\n" +
                        "\n" +
                        "In addition, all 100 top students are connected with a successful business mentor, recognised through the Rising Star Programme, to further ready them for the workplace. " +
                        "The mentorship café takes place at the Rising Star Summit the following day (www.risingstarsummit.co.za), a leadership conference which all the Top 100 attend free of charge."))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("yusuf@blackbark.co.za")
                .addWebsite("http://gradstar.co.za/")
                .addFacebook("https://www.facebook.com/GradStarSA/")
                .addTwitter("https://twitter.com/gradstarsa")
                .addYoutube("UC92el_bvMKzJXmoP_YYEdPw")
                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);

    }

    private Element createCopyright()
    {
        Element copyright = new Element();
        final String copyRightString = String.format("Copyright protected by GradStar", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyRightString);
        copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(AboutActivity.this,copyRightString, Toast.LENGTH_SHORT).show();

            }
        });
        return copyright;
    }
}
