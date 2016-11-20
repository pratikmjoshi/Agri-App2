package com.example.prateekjoshi.agri_app;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
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

public class Registration2 extends AppCompatActivity{
    public ImageButton next;
    public ImageButton previous;

    private android.support.design.widget.TextInputEditText editTextFirstName;
    private android.support.design.widget.TextInputEditText editTextMiddleName;
    private android.support.design.widget.TextInputEditText editTextLastName;

    public String firstName;
    public String middleName;
    public String lastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        next=(ImageButton)findViewById(R.id.reg2_btn_next);
        previous=(ImageButton)findViewById(R.id.reg2_btn_back);

        editTextFirstName= (TextInputEditText) findViewById(R.id.reg2_firstname_edittext);
        editTextMiddleName= (TextInputEditText) findViewById(R.id.reg2_middlename_edittext);
        editTextLastName= (TextInputEditText) findViewById(R.id.reg2_lastname_edittext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextFirstName.getText().toString().equals("")||editTextLastName.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter at least first name or last name", Toast.LENGTH_SHORT).show();
                }
                else{
                    firstName=editTextFirstName.getText().toString();
                    middleName=editTextMiddleName.getText().toString();
                    lastName=editTextLastName.getText().toString();

                    Intent i=new Intent(Registration2.this,Registration3.class);
                    startActivity(i);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Registration2.this,Registration1.class);
                startActivity(i);
            }
        });

    }

}



