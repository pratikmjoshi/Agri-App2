package com.example.prateekjoshi.agri_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.rd.PageIndicatorView;

public class RegistrationActivity extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final ViewPager pager = (ViewPager) findViewById(R.id.registration_viewpager);
        pager.setAdapter(new RegistrationAdapter(getSupportFragmentManager()));
        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.registration_pageIndicatorView);
        pageIndicatorView.setViewPager(pager);

        next=(ImageButton)findViewById(R.id.btn_next);
        previous=(ImageButton)findViewById(R.id.btn_back);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem()+1);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem()-1);
            }
        });


    }

}
