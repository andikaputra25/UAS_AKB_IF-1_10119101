package com.andikaputra.uasakbif_110119101;


/**
 * Nim   : 10119101
 * Nama  : Andika Putra
 * Kelas : IF-1
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SliderActivity extends AppCompatActivity {

    ViewPager mSlideViewPager;
    LinearLayout mDotLayout;
    Button backbtn, nextbtn, skipbtn;

    TextView[] dots;

    ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        //Deklarasi button
        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);
        skipbtn = findViewById(R.id.skipbtn);


        //Mendeklarasikan Button Kembali (Back Button)
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getitem(0) > 0){

                    mSlideViewPager.setCurrentItem(getitem(-1),true);

                }
            }
        });

        //Mendeklarasikan Button Next (Next Button)
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getitem(0) < 3)
                    mSlideViewPager.setCurrentItem(getitem(1), true);
                else {
                    Intent intent = new Intent(SliderActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        //Mendeklarasikan Button lewati (Skip Button)
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SliderActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);
        viewPagerAdapter = new ViewPagerAdapter(this);
        mSlideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

    }

    private void setUpindicator(int position) {

        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.active, getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpindicator(position);
            if (position < 3
            ) {
                nextbtn.setText("Next");
            } else {
                nextbtn.setText("Start");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i) {
        return mSlideViewPager.
                getCurrentItem() + i;
    }
}