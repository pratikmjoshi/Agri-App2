package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.StringDef;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registration4 extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;

    private RadioGroup ownLandGroup;
    private RadioButton ownLandButton;
    public TextInputEditText landNameEditText;
    public TextInputEditText hectaresEditText;

    public boolean ownLand;
    public String landName;
    public int hectares;
    public String phone;

    private DBHelper db;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db= new DBHelper(this);

        Intent i =getIntent();
        phone=i.getStringExtra("Phone");

        next=(ImageButton)findViewById(R.id.reg4_btn_next);
        previous=(ImageButton)findViewById(R.id.reg4_btn_back);

        ownLandGroup= (RadioGroup) findViewById(R.id.reg4_ownland_radiogrp);
        landNameEditText=(TextInputEditText)findViewById(R.id.reg4_landname_edittext);
        hectaresEditText=(TextInputEditText)findViewById(R.id.reg4_hectares_edittext);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                landName=landNameEditText.getText().toString();
                hectares=Integer.parseInt(hectaresEditText.getText().toString());
                int selectedId=ownLandGroup.getCheckedRadioButtonId();
                ownLandButton = (RadioButton)findViewById(selectedId);
                if(selectedId==-1){
                    Toast.makeText(getApplicationContext(),"Please select if you rent or own land",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(ownLandButton.getText()=="Own"){
                        ownLand=true;
                    }
                    else
                    if(ownLandButton.getText()=="Rent"){
                        ownLand=false;
                    }

                    db.Profile4(phone,ownLand,landName,hectares);
                    Intent i=new Intent(Registration4.this,Registration5.class);
                    i.putExtra("Phone",phone);
                    startActivity(i);
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Registration4.this,Registration3.class);
                startActivity(i);
            }
        });
    }

    public boolean onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.reg4_ownland_radiobtnyes:
                if (checked)
                    ownLand=true;
                    break;
            case R.id.reg4_ownland_radiobtnno:
                if (checked)
                    ownLand=false;
                    break;
        }
        return checked;
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }
}





