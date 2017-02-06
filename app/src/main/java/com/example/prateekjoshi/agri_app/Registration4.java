package com.example.prateekjoshi.agri_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;


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

    private Realm realm;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.reg4_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        Intent i = getIntent();
        phone = i.getStringExtra("phone");

        next = (ImageButton) findViewById(R.id.reg4_btn_next);
        previous = (ImageButton) findViewById(R.id.reg4_btn_back);

        ownLandGroup = (RadioGroup) findViewById(R.id.reg4_ownland_radiogrp);
        landNameEditText = (TextInputEditText) findViewById(R.id.reg4_landname_edittext);
        landNameEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        hectaresEditText = (TextInputEditText) findViewById(R.id.reg4_hectares_edittext);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = ownLandGroup.getCheckedRadioButtonId();
                ownLandButton = (RadioButton) findViewById(selectedId);
                if (landNameEditText.getText().toString().equals("") || hectaresEditText.getText().toString().equals("") || selectedId == -1) {
                    Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
                } else {
                    update(realm);
                    updateOnlineRegistrationDetails();
                    Toast.makeText(getApplicationContext(), "Registration finished!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Registration4.this, MenuNavActivity.class);
                    i.putExtra("phone", phone);
                    startActivity(i);
                    /*if (landNameEditText.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please enter the name of the land owner", Toast.LENGTH_SHORT).show();
                    } else {
                        landName = landNameEditText.getText().toString();
                    }
                    if (hectaresEditText.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please enter hectares of land", Toast.LENGTH_SHORT).show();
                        hectares = 0;
                    } else {
                        hectares = Integer.parseInt(hectaresEditText.getText().toString());
                    }

                    if (selectedId == -1) {
                        Toast.makeText(getApplicationContext(), "Please select if you rent or own land", Toast.LENGTH_SHORT).show();
                    } else {
                        if (ownLandButton.getText() == "Own") {
                            ownLand = true;
                        } else if (ownLandButton.getText() == "Rent") {
                            ownLand = false;
                        }
                    }*/
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registration4.this, Registration5.class);
                i.putExtra("Phone", phone);
                startActivity(i);
            }
        });
    }

    public boolean onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.reg4_ownland_radiobtnyes:
                if (checked)
                    ownLand = true;
                break;
            case R.id.reg4_ownland_radiobtnno:
                if (checked)
                    ownLand = false;
                break;
        }
        return checked;
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    public void update(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).equalTo("phone", phone).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (ProfileDetails profileDetails : results) {
                    profileDetails.setOwnLand(ownLand);
                    profileDetails.setNameLand(landName);
                    profileDetails.setHectares(hectares);
                }
            }
        });

    }

    public Map<String, Object> realmMap(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).equalTo("phone", phone).findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        for (ProfileDetails temp : results) {
            Map<String, Object> post = temp.toMap();
            map.put("Registration/", post);
            return map;
        }
        return map;
    }

    public void updateOnlineRegistrationDetails() {
        Map<String, Object> realmMap = realmMap(realm);
        Map<String, Object> realmValueMap = (Map<String, Object>) realmMap.get("Registration/");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> finalMap = new HashMap<String, Object>();
        map.put("Phone Number", realmValueMap.get("Phone Number"));
        map.put("Password", realmValueMap.get("Password"));
        map.put("Version", realmValueMap.get("Version"));
        map.put("First Name", realmValueMap.get("First Name"));
        map.put("Middle Name", realmValueMap.get("Middle Name"));
        map.put("Last Name", realmValueMap.get("Last Name"));
        map.put("Address", realmValueMap.get("Address"));
        map.put("Province", realmValueMap.get("Province"));
        map.put("Postal Code", realmValueMap.get("Postal Code"));
        map.put("Rent or Own Land", realmValueMap.get("Rent or Own Land"));
        map.put("Name Land", realmValueMap.get("Name Land"));
        map.put("Hectares of Land", realmValueMap.get("Hectares of Land"));
        map.put("Crop Details", realmValueMap.get("Crop Details"));


        String uniqueId = ref.child("Registration").push().getKey();

        finalMap.put("Registration/" + uniqueId, map);

        ref.updateChildren(finalMap);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }
}





