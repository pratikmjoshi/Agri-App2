package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HarvestActivity extends AppCompatActivity {

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private String phone;
    private String totalAmount;
    private String amountPerHectare;
    private boolean color;
    private boolean colorStem;
    private boolean marks;
    private boolean diseases;
    private boolean bugs;
    private boolean colorSoil;
    private boolean pesticide;

    private TextInputEditText totalAmountEditText;
    private TextInputEditText amountPerHectareEditText;
    private RadioGroup colorGroup;
    private RadioButton colorButton;
    private RadioGroup colorStemGroup;
    private RadioButton colorStemButton;
    private RadioGroup marksGroup;
    private RadioButton marksButton;
    private RadioGroup diseasesGroup;
    private RadioButton diseasesButton;
    private RadioGroup bugsGroup;
    private RadioButton bugsButton;
    private RadioGroup colorSoilGroup;
    private RadioButton colorSoilButton;
    private RadioGroup pesticideGroup;
    private RadioButton pesticideButton;


    private MenuItem edit;
    private MenuItem save;
    private MenuItem cancel;

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_harvest_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        toolbar.setTitle("Crops/Harvest");
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        phone = i.getStringExtra("phone");

        totalAmountEditText = (TextInputEditText) findViewById(R.id.harvest_totalamount_edittext);
        amountPerHectareEditText = (TextInputEditText) findViewById(R.id.harvest_amountphectare_edittext);
        colorGroup = (RadioGroup) findViewById(R.id.harvest_colordiff_radiogrp);
        colorStemGroup = (RadioGroup) findViewById(R.id.harvest_colordiffstem_radiogrp);
        marksGroup = (RadioGroup) findViewById(R.id.harvest_marks_radiogrp);
        diseasesGroup = (RadioGroup) findViewById(R.id.harvest_diseases_radiogrp);
        bugsGroup = (RadioGroup) findViewById(R.id.harvest_bugs_radiogrp);
        colorSoilGroup = (RadioGroup) findViewById(R.id.harvest_soilcolor_radiogrp);
        pesticideGroup = (RadioGroup) findViewById(R.id.harvest_pesticide_radiogrp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_toolbar, menu);
        edit = menu.findItem(R.id.profile_edit);
        save = menu.findItem(R.id.profile_save);
        cancel = menu.findItem(R.id.profile_cancel);
        edit.setVisible(false);
        save.setVisible(true);
        save.setTitle("Submit");
        cancel.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.profile_save) {
            if (isNetworkAvailable(getApplicationContext())) {
                updateOnlineRegistrationDetails();
            }

            Intent i = new Intent(getApplicationContext(), MenuNavActivity.class);
            Toast.makeText(getApplicationContext(), "Submitted Harvest Details", Toast.LENGTH_SHORT).show();
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MenuNavActivity.class);
        startActivity(i);
    }

    public void updateOnlineRegistrationDetails() {
        totalAmount = totalAmountEditText.getText().toString();
        amountPerHectare = amountPerHectareEditText.getText().toString();
        color = radioCheck(colorGroup, colorButton);
        colorStem = radioCheck(colorStemGroup, colorStemButton);
        marks = radioCheck(marksGroup, marksButton);
        diseases = radioCheck(diseasesGroup, diseasesButton);
        bugs = radioCheck(bugsGroup, bugsButton);
        colorSoil = radioCheck(colorSoilGroup, colorSoilButton);
        pesticide = radioCheck(pesticideGroup, pesticideButton);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> finalMap = new HashMap<String, Object>();
        map.put("Phone Number", phone);
        map.put("TotalProduction", totalAmount);
        map.put("AmountPerHectare", amountPerHectare);
        map.put("Color", color);
        map.put("ColorStem", colorStem);
        map.put("Marks", marks);
        map.put("Diseases", diseases);
        map.put("Bugs", bugs);
        map.put("SoilColor", colorSoil);
        map.put("Pesticide", pesticide);

        String uniqueId = ref.child("Harvest").push().getKey();

        finalMap.put("Harvest/" + uniqueId, map);

        ref.updateChildren(finalMap);
    }

    public boolean radioCheck(RadioGroup group, RadioButton button) {
        int selectedId = group.getCheckedRadioButtonId();
        button = (RadioButton) findViewById(selectedId);
        if (selectedId == -1) {

        } else {
            if (button.getText().toString().equals("Sí")) {

                return true;
            } else if (button.getText().toString().equals("No")) {
                return false;
            }
        }
        return false;
    }
}
