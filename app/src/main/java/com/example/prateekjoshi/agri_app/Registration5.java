package com.example.prateekjoshi.agri_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;


public class Registration5 extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;
    public CheckBox pineapple, orange, banana, passionfruit, quinoa, chia, cocoa;
    public ArrayList<String> crops;
    public String phone;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.reg5_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        Intent i = getIntent();
        phone = i.getStringExtra("Phone");

        next = (ImageButton) findViewById(R.id.reg5_btn_next);
        previous = (ImageButton) findViewById(R.id.reg5_btn_back);
        crops = new ArrayList<>();
        pineapple = (CheckBox) findViewById(R.id.reg5_croptype_pineapple_checkbox);
        orange = (CheckBox) findViewById(R.id.reg5_croptype_orange_checkbox);
        banana = (CheckBox) findViewById(R.id.reg5_croptype_banana_checkbox);
        passionfruit = (CheckBox) findViewById(R.id.reg5_croptype_passionfruit_checkbox);
        quinoa = (CheckBox) findViewById(R.id.reg5_croptype_quinoa_checkbox);
        chia = (CheckBox) findViewById(R.id.reg5_croptype_chia_checkbox);
        cocoa = (CheckBox) findViewById(R.id.reg5_croptype_cocoa_checkbox);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cropsTypes();
                int amount = crops.size();
                if (amount == 0) {
                    Toast.makeText(getApplicationContext(), "Please choose at least one crop", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(Registration5.this, Registration6.class);
                    i.putExtra("Loops", amount);
                    i.putExtra("phone", phone);
                    i.putExtra("Oloops", amount);
                    i.putStringArrayListExtra("Type of crops", crops);
                    startActivity(i);
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registration5.this, Registration3.class);
                i.putExtra("Phone", phone);
                startActivity(i);
            }
        });
    }

    public void cropsTypes() {
        if (pineapple.isChecked()) {
            crops.add("pineapple");
        }
        if (orange.isChecked()) {
            crops.add("orange");
        }
        if (banana.isChecked()) {
            crops.add("banana");
        }
        if (passionfruit.isChecked()) {
            crops.add("passionfruit");
        }
        if (quinoa.isChecked()) {
            crops.add("quinoa grains");
        }
        if (chia.isChecked()) {
            crops.add("chia seeds");
        }
        if (cocoa.isChecked()) {
            crops.add("cacao pod");
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }
}
