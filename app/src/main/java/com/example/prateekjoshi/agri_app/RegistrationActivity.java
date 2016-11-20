package com.example.prateekjoshi.agri_app;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.rd.PageIndicatorView;

import java.lang.reflect.Field;
import java.util.HashMap;

/*public class RegistrationActivity extends AppCompatActivity implements OnFragmentInteractionListener{
    public ImageButton next;
    public ImageButton previous;
    ViewPager pager;
    private OnFragmentInteractionListener listener;

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


        pager = (NonSwipeableViewPager) findViewById(R.id.registration_viewpager);
        pager.setAdapter(new RegistrationAdapter(getSupportFragmentManager()));
        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.registration_pageIndicatorView);
        pageIndicatorView.setViewPager(pager);

        next=(ImageButton)findViewById(R.id.btn_next);
        previous=(ImageButton)findViewById(R.id.btn_back);



    }

    @Override
    public void onResume(){
        super.onResume();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pageno= pager.getCurrentItem();

                switch(pageno)
                {
                    case 0: Registration1 frag = (Registration1)getSupportFragmentManager().findFragmentById(R.id.fragment_registration1);
                        if(frag==null){
                            frag=new Registration1();
                            HashMap hm= frag.send();
                            Toast.makeText(getApplicationContext(),(String)hm.get("Phone"),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (frag.send() == null)
                                Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(),(String)hm.get("Phone"),Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
                // pager.setCurrentItem(pager.getCurrentItem()+1);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem()-1);
            }
        });


    }
    @Override
    public HashMap<String, String> onReg1Completed(String phone,String password) {
        HashMap hm= new HashMap();
        hm.put("Phone",phone);
        hm.put("Password",password);
        return hm;
    }

    @Override
    public HashMap<String, String> onReg2Completed() {
        return null;
    }

    @Override
    public HashMap<String, String> onReg3Completed() {
        return null;
    }

    @Override
    public HashMap<String, String> onReg4Completed() {
        return null;
    }

    @Override
    public HashMap<String, String> onReg5Completed() {
        return null;
    }

    @Override
    public HashMap<String, String> onReg6Completed() {
        return null;
    }
}
*/