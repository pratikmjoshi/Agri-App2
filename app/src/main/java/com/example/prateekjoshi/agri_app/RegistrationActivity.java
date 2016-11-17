package com.example.prateekjoshi.agri_app;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.rd.PageIndicatorView;

import java.lang.reflect.Field;

public class RegistrationActivity extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;

    public static void setInputTextLayoutColor(TextInputLayout til, @ColorInt int color) {
        try {
            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{color}));

            Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("mFocusedTextColor");
            fFocusedTextColor.setAccessible(true);
            fFocusedTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{color}));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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
