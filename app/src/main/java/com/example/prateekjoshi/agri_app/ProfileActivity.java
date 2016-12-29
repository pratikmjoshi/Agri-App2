package com.example.prateekjoshi.agri_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;

public class ProfileActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        realm = Realm.getDefaultInstance();
    }



}
