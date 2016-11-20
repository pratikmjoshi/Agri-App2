package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class Registration6 extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;
    public ImageView cropPicture;
    public TextInputEditText cropsPerHectareEditText;
    public TextInputEditText cropQuintalsEditText;
    public double cropsPerHectare;
    public double cropQuintals;
    public ArrayList<String> crops;
    public int repeats;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration6);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i=getIntent();
        crops= i.getStringArrayListExtra("Type of crops");
        repeats= i.getIntExtra("Loops",1);

        String crop= crops.get(repeats-1);



        next=(ImageButton)findViewById(R.id.reg6_btn_next);
        previous=(ImageButton)findViewById(R.id.reg6_btn_back);

        cropsPerHectareEditText=(TextInputEditText)findViewById(R.id.reg6_crop_per_hectare_edittext);
        cropQuintalsEditText=(TextInputEditText)findViewById(R.id.reg6_crop_quintal_edittext);

        cropPicture= (ImageView) findViewById(R.id.reg6_croppic);
        cropPicture.setImageResource(setImage(crop));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            cropsPerHectare=Integer.parseInt(cropsPerHectareEditText.getText().toString());
            cropQuintals=Integer.parseInt(cropQuintalsEditText.getText().toString());
            if(repeats==1) {
                Toast.makeText(getApplicationContext(), "Registration finished!", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Registration6.this, LoginActivity.class);
                startActivity(i);
            }
                else{
                repeats--;
                Intent i = new Intent(Registration6.this, Registration6.class);
                i.putStringArrayListExtra("Type of crops", crops);
                i.putExtra("Loops", repeats);
                startActivity(i);
            }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropsPerHectare=Integer.parseInt(cropsPerHectareEditText.getText().toString());
                cropQuintals=Integer.parseInt(cropQuintalsEditText.getText().toString());
                repeats++;
                Intent i=new Intent(Registration6.this,Registration6.class);
                i.putStringArrayListExtra("Type of crops",crops);
                i.putExtra("Loops",repeats);
                startActivity(i);
            }
        });

    }

    public int setImage(String crop){
        if(crop.equals("pineapple")){
            return R.drawable.pineapple;
        }
        if(crop.equals("orange")){
            return R.drawable.orange;
        }
        if(crop.equals("banana")){
            return R.drawable.banana;
        }
        if(crop.equals("cacao pod")){
            return R.drawable.cacaopod;
        }
        if(crop.equals("passionfruit")){
            return R.drawable.passionfruit;
        }
        if(crop.equals("chia seeds")){
            return R.drawable.chia_seeds;
        }
        if(crop.equals("quinoa grains")){
            return R.drawable.quinoagrain;
        }
        return 0;
    }

}
