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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Registration6 extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;
    public ImageView cropPicture;
    public TextView cropPictureLabel;
    public String crop;
    public TextInputEditText cropsPerHectareEditText;
    public TextInputEditText cropQuintalsEditText;
    public double cropsPerHectare;
    public double cropQuintals;
    public ArrayList<String> crops;
    public int repeats;
    public String phone;

    private DBHelper db;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration6);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db= new DBHelper(this);

        Intent i=getIntent();
        phone= i.getStringExtra("Phone");
        crops= i.getStringArrayListExtra("Type of crops");
        repeats= i.getIntExtra("Loops",1);

        crop= crops.get(repeats-1);



        next=(ImageButton)findViewById(R.id.reg6_btn_next);
        previous=(ImageButton)findViewById(R.id.reg6_btn_back);

        cropsPerHectareEditText=(TextInputEditText)findViewById(R.id.reg6_crop_per_hectare_edittext);
        cropQuintalsEditText=(TextInputEditText)findViewById(R.id.reg6_crop_quintal_edittext);

        cropPicture= (ImageView) findViewById(R.id.reg6_croppic);
        cropPicture.setImageResource(setImage(crop));
        cropPictureLabel = (TextView)findViewById(R.id.reg6_croppic_textview);
        cropPictureLabel.setText(setCropText(crop));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cropsPerHectareEditText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill in all details",Toast.LENGTH_SHORT).show();
                }
                else
                if(cropQuintalsEditText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill in all details",Toast.LENGTH_SHORT).show();
                }
                else {
                    String hectares= cropsPerHectareEditText.getText().toString();
                    String quintals= cropQuintalsEditText.getText().toString();
                    cropsPerHectare = Integer.parseInt(hectares);
                    cropQuintals = Integer.parseInt(quintals);
                    String cropDetail=convertToString(crop,hectares,quintals);
                    db.addCropDetail(phone,cropDetail);
                    if (repeats == 1) {
                        Toast.makeText(getApplicationContext(), "Registration finished!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Registration6.this, MenuNavActivity.class);
                        startActivity(i);
                    } else {
                        repeats--;
                        Intent i = new Intent(Registration6.this, Registration6.class);
                        i.putStringArrayListExtra("Type of crops", crops);
                        i.putExtra("Loops", repeats);
                        startActivity(i);
                    }
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public String setCropText(String crop){
        if(crop.equals("pineapple")){
            return "Pineapple";
        }
        if(crop.equals("orange")){
            return "Orange";
        }
        if(crop.equals("banana")){
            return "Banana";
        }
        if(crop.equals("cacao pod")){
            return "Cacao Pod";
        }
        if(crop.equals("passionfruit")){
            return "Passionfruit";
        }
        if(crop.equals("chia seeds")){
            return "Chia Seeds";
        }
        if(crop.equals("quinoa grains")){
            return "Quinoa Grains";
        }
        return "Crop";
    }

    public String convertToString(String crop,String hectares,String quintals) {
        String temp = "Crop:"+ crop + "Hectares:" + hectares + "Quintals:" + quintals;
        return temp;
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }


}
