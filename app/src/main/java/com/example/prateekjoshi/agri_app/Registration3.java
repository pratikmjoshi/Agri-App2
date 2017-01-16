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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.realm.Realm;
import io.realm.RealmResults;


public class Registration3 extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;

    private android.support.design.widget.TextInputEditText editTextAddress;
    private android.support.design.widget.TextInputEditText editTextProvince;
    private android.support.design.widget.TextInputEditText editTextPostalCode;

    public String address;
    public String province;
    public String postalCode;
    public String phone;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.reg3_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white,getTheme()));
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        Intent i = getIntent();
        phone=i.getStringExtra("Phone");



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

                update(realm);

                Intent i=new Intent(Registration3.this,Registration5.class);
                i.putExtra("Phone",phone);
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
    public void update(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).equalTo("phone",phone).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(ProfileDetails profileDetails : results) {
                    profileDetails.setAddress(address);
                    profileDetails.setProvince(province);
                    profileDetails.setPostalCode(postalCode);
                    Log.d("kk",profileDetails.getFirstName());
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

}

