package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProfileActivity extends AppCompatActivity {

    private Realm realm;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private TextInputEditText phone;
    private TextInputEditText password;
    private TextInputEditText firstName;
    private TextInputEditText middleName;
    private TextInputEditText lastName;
    private TextInputEditText address;
    private TextInputEditText province;
    private TextInputEditText postalCode;
    private RadioGroup ownLandGroup;
    private RadioButton ownLandButton;
    private RadioButton ownLandButtonyes;
    private RadioButton ownLandButtonno;
    private TextInputEditText nameLand;
    private TextInputEditText hectares;

    private Button cropDetailsButton;

    private MenuItem edit;
    private MenuItem save;
    private MenuItem cancel;
    private MenuItem delete;

    private boolean editmenu;
    public boolean ownLand;

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileactivity_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white,getTheme()));
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        editmenu=false;

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

        cropDetailsButton = (Button)findViewById(R.id.profile_cropdetails_button);

        update(realm);

        disableFields();


        cropDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),ProfileCropDetailsActivity.class);
                i.putExtra("phone",phone.getText().toString());
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_toolbar, menu);
        edit = menu.findItem(R.id.profile_edit);
        save = menu.findItem(R.id.profile_save);
        cancel = menu.findItem(R.id.profile_cancel);
        delete = menu.findItem(R.id.profile_delete);
        delete.setVisible(false);
        edit.setVisible(true);
        save.setVisible(false);
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
        if (id == R.id.profile_edit) {
            enableFields();
            editmenu = true;
            invalidateOptionsMenu();
        }
        else
        if (id == R.id.profile_cancel) {
            disableFields();
            editmenu = false;
            invalidateOptionsMenu();
        }
        else
        if (id==R.id.profile_save) {
            saveNew(realm);
            if(isNetworkAvailable(getApplicationContext())) {
                updateOnlineRegistrationDetails();
            }
            disableFields();
            editmenu = false;
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(editmenu==true) {
            edit.setVisible(false);
            save.setVisible(true);
            cancel.setVisible(true);
            delete.setVisible(false);
        }
        else {
            edit.setVisible(true);
            save.setVisible(false);
            cancel.setVisible(false);
            delete.setVisible(false);
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }
    @Override
    public void onBackPressed() {
        Intent i =new Intent(this, MenuNavActivity.class);
        startActivity(i);
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

    public void enableFields() {
        phone.setEnabled(true);
        password.setEnabled(true);
        firstName.setEnabled(true);
        middleName.setEnabled(true);
        lastName.setEnabled(true);
        address.setEnabled(true);
        province.setEnabled(true);
        postalCode.setEnabled(true);
        nameLand.setEnabled(true);
        hectares.setEnabled(true);
        ownLandButtonyes.setEnabled(true);
        ownLandButtonno.setEnabled(true);


    }
    public void update(Realm realm) {

        RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).findAll();
        ProfileDetails profile = new ProfileDetails();
        for(ProfileDetails temp : results) {
            profile = temp;
        }

        phone.setText(profile.getPhone());
        password.setText(profile.getPassword());
        firstName.setText(profile.getFirstName());
        middleName.setText(profile.getMiddleName());
        lastName.setText(profile.getLastName());
        address.setText(profile.getAddress());
        province.setText(profile.getProvince());
        postalCode.setText(profile.getPostalCode());
        nameLand.setText(profile.getNameLand());
        if(hectares!=null) {
            hectares.setText(Integer.toString(profile.getHectares()));
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

    public void saveNew(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).equalTo("phone",phone.getText().toString()).findAll();
        int selectedId=ownLandGroup.getCheckedRadioButtonId();
        ownLandButton = (RadioButton)findViewById(selectedId);
        if(selectedId==-1){
            Toast.makeText(getApplicationContext(),"Please select if you rent or own land",Toast.LENGTH_SHORT).show();
        }
        else {
            if (ownLandButton.getText() == "Own") {
                ownLand = true;
            } else if (ownLandButton.getText() == "Rent") {
                ownLand = false;
            }
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(ProfileDetails profileDetails : results) {
                    profileDetails.setPhone(phone.getText().toString());
                    profileDetails.setPassword(password.getText().toString());
                    profileDetails.setFirstName(firstName.getText().toString());
                    profileDetails.setMiddleName(middleName.getText().toString());
                    profileDetails.setLastName(lastName.getText().toString());
                    profileDetails.setAddress(address.getText().toString());
                    profileDetails.setProvince(province.getText().toString());
                    profileDetails.setPostalCode(postalCode.getText().toString());
                    profileDetails.setOwnLand(ownLand);
                    profileDetails.setNameLand(nameLand.getText().toString());
                    profileDetails.setHectares(Integer.parseInt(hectares.getText().toString()));
                }
            }
        });
    }

    public Map<String, Object> realmMap(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).equalTo("phone",phone.getText().toString()).findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        for(ProfileDetails temp: results) {
            Map<String, Object> post = temp.toMap();
            map.put("Registration/", post);
            return map;
        }
        return map;
    }
    public void updateOnlineRegistrationDetails() {
        Map<String,Object> realmMap = realmMap(realm);
        Map<String,Object> realmValueMap = (Map<String,Object>) realmMap.get("Registration/");
        final Map<String,Object> map= new HashMap<>();
        final Map<String,Object> finalMap = new HashMap<String,Object>();
        map.put("Phone Number", realmValueMap.get("Phone Number"));
        map.put("Password",realmValueMap.get("Password") );
        map.put("First Name", realmValueMap.get("First Name"));
        map.put("Middle Name", realmValueMap.get("Middle Name"));
        map.put("Last Name", realmValueMap.get("Last Name"));
        map.put("Address",realmValueMap.get("Address"));
        map.put("Province",realmValueMap.get("Province"));
        map.put("Postal Code",realmValueMap.get("Postal Code"));
        map.put("Rent or Own Land",realmValueMap.get("Rent or Own Land"));
        map.put("Name Land", realmValueMap.get("Name Land"));
        map.put("Hectares of Land", realmValueMap.get("Hectares of Land"));
        map.put("Crop Details",realmValueMap.get("Crop Details"));

        String phone = realmValueMap.get("Phone Number").toString();
        Query query = ref.child("Registration").orderByChild("Phone Number").equalTo(phone);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot: dataSnapshot.getChildren()) {
                    Snapshot.getRef().updateChildren(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Random:", "onCancelled", databaseError.toException());
            }
        });



    }

}
