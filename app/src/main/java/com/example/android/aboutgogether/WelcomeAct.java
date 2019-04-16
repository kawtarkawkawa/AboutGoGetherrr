package com.example.android.aboutgogether;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeAct extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPageer;
    private int[] layouts = {R.layout.first_slid,R.layout.second_slid,R.layout.third_slid};
    private MpageerAdapter mpageerAdapter;

    private LinearLayout Dots_Layout;
    private ImageView[] dots;

    private Button BtNext,BtSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (new PreferenceeManager(this).checkPreferencee())
        {
            loadHome();
        }

        if (Build.VERSION.SDK_INT >= 19)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_welcome);

        mPageer = (ViewPager)findViewById(R.id.viewpager);
        mpageerAdapter = new MpageerAdapter(layouts,this);
        mPageer.setAdapter(mpageerAdapter);

        Dots_Layout = (LinearLayout)findViewById(R.id.dotsLayout);
        BtNext = (Button)findViewById(R.id.btNext);
        BtSkip = (Button)findViewById(R.id.bnSkip);
        BtNext.setOnClickListener(this);
        BtSkip.setOnClickListener(this);
        creatDots(0);

        mPageer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                creatDots(position);
                if (position == layouts.length -1)
                {
                    BtNext.setText("Start");
                    BtSkip.setVisibility(View.INVISIBLE);
                }
                else
                {
                    BtNext.setText("Next");
                    BtSkip.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void creatDots(int current_position)
    {
        if (Dots_Layout != null)
            Dots_Layout.removeAllViews();
        dots = new ImageView[layouts.length];

        for (int i = 0; i<layouts.length; i++)
        {
            dots[i] = new ImageView(this);
            if (i == current_position)
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.selected_dots));
            }
            else
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            Dots_Layout.addView(dots[i],params);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btNext:
                loadNextSlide();
                break;

            case R.id.bnSkip:
                loadHome();
                new PreferenceeManager(this).writePreferencee();
                break;
        }

    }
    private void loadHome()
    {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    private void loadNextSlide()
    {
        int next_slid = mPageer.getCurrentItem()+1;
        if (next_slid < layouts.length)
        {
            mPageer.setCurrentItem(next_slid);
        }
        else
        {
            loadHome();
            new PreferenceeManager(this).writePreferencee();
        }
    }
}
