package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


public class Registration3 extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;

    private android.support.design.widget.TextInputEditText editTextAddress;
    private android.support.design.widget.TextInputEditText editTextProvince;
    private android.support.design.widget.TextInputEditText editTextPostalCode;

    public String address;
    public String province;
    public String postalCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        next=(ImageButton)findViewById(R.id.reg3_btn_next);
        previous=(ImageButton)findViewById(R.id.reg3_btn_back);

        editTextAddress= (TextInputEditText) findViewById(R.id.reg3_address_edittext);
        editTextProvince= (TextInputEditText) findViewById(R.id.reg3_province_edittext);
        editTextPostalCode= (TextInputEditText) findViewById(R.id.reg3_postalcode_edittext);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address=editTextAddress.getText().toString();
                province=editTextProvince.getText().toString();
                postalCode=editTextPostalCode.getText().toString();

                Intent i=new Intent(Registration3.this,Registration4.class);
                startActivity(i);

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Registration3.this,Registration2.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }


}

