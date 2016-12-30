package com.example.prateekjoshi.agri_app;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProfileActivity extends AppCompatActivity {

    private Realm realm;
    private TextInputEditText phone;
    private TextInputEditText password;
    private TextInputEditText firstName;
    private TextInputEditText middleName;
    private TextInputEditText lastName;
    private TextInputEditText address;
    private TextInputEditText province;
    private TextInputEditText postalCode;
    private RadioGroup ownLandGroup;
    private RadioButton ownLandButtonyes;
    private RadioButton ownLandButtonno;
    private TextInputEditText nameLand;
    private TextInputEditText hectares;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileactivity_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white,getTheme()));
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        phone = (TextInputEditText)findViewById(R.id.profile_phone_edittext);
        password = (TextInputEditText)findViewById(R.id.profile_password_edittext);
        firstName = (TextInputEditText)findViewById(R.id.profile_firstname_edittext);
        middleName = (TextInputEditText)findViewById(R.id.profile_middlename_edittext);
        lastName = (TextInputEditText)findViewById(R.id.profile_lastname_edittext);
        address = (TextInputEditText)findViewById(R.id.profile_address_edittext);
        province = (TextInputEditText)findViewById(R.id.profile_province_edittext);
        postalCode = (TextInputEditText)findViewById(R.id.profile_postalcode_edittext);
        nameLand = (TextInputEditText)findViewById(R.id.profile_landname_edittext);
        hectares = (TextInputEditText)findViewById(R.id.profile_hectares_edittext);

        ownLandGroup= (RadioGroup) findViewById(R.id.profile_ownland_radiogrp);
        ownLandButtonyes = (RadioButton)findViewById(R.id.profile_ownland_radiobtnyes);
        ownLandButtonno = (RadioButton)findViewById(R.id.profile_ownland_radiobtnno);

        update(realm);

        disableFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_edit) {

        }

        return super.onOptionsItemSelected(item);
    }

    public void disableFields() {
        phone.setEnabled(false);
        password.setEnabled(false);
        firstName.setEnabled(false);
        middleName.setEnabled(false);
        lastName.setEnabled(false);
        address.setEnabled(false);
        province.setEnabled(false);
        postalCode.setEnabled(false);
        nameLand.setEnabled(false);
        hectares.setEnabled(false);
        ownLandButtonyes.setEnabled(false);
        ownLandButtonno.setEnabled(false);
    }

    public void update(Realm realm) {

        RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).findAll();
        ProfileDetails profile = new ProfileDetails();
        for(ProfileDetails temp : results) {
            profile = temp;
        }

        phone.setHint(profile.getPhone());
        password.setHint(profile.getPassword());
        firstName.setHint(profile.getFirstName());
        middleName.setHint(profile.getMiddleName());
        lastName.setHint(profile.getLastName());
        address.setHint(profile.getAddress());
        province.setHint(profile.getProvince());
        postalCode.setHint(profile.getPostalCode());
        nameLand.setHint(profile.getNameLand());
        if(hectares!=null) {
            hectares.setHint(Integer.toString(profile.getHectares()));
        }

        if(profile.getOwnLand()){
            ownLandButtonyes.setChecked(true);
            ownLandButtonno.setChecked(false);
        }
        else {
            ownLandButtonno.setChecked(true);
            ownLandButtonyes.setChecked(false);
        }

    }
}
