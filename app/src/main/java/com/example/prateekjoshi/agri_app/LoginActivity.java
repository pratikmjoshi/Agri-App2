package com.example.prateekjoshi.agri_app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    private Realm realm;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private String name;
    private String password;
    public Button logbutton;

    public TextView regbutton;
    public EditText editTextName;
    public EditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        overridePendingTransition(0, 0);

        FirebaseMessaging.getInstance().subscribeToTopic("alerts");

        realm = Realm.getDefaultInstance();

        logbutton= (Button)findViewById(R.id.login_button_login);
        regbutton=(TextView) findViewById(R.id.login_notregistered_link);

        editTextName= (EditText)findViewById(R.id.login_name_edittext);
        editTextPassword= (EditText)findViewById(R.id.login_password_edittext);
        editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
        editTextPassword.setTypeface(Typeface.DEFAULT);


        logbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=editTextName.getText().toString();
                password=editTextPassword.getText().toString();
                verify(realm,name,password);
            }
        });

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(LoginActivity.this,RegistrationCodeActivity.class);
                startActivity(i);
            }
        });


    }

    public void verify(Realm realm, String name, final String password) {
        boolean onPhone = false;
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).equalTo("phone",name).findAll();
        for(ProfileDetails profile : results) {
            if (profile.getPassword().equals(password)) {
                onPhone = true;
                Intent i=new Intent(LoginActivity.this,MenuNavActivity.class);
                startActivity(i);
            }
        }
        if(!onPhone) {
            final String phone = name;
            Query query = ref.child("Registration").orderByChild("Phone Number").equalTo(name);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot Snapshot: dataSnapshot.getChildren()) {

                        if(Snapshot.child("Password").getValue().toString().equals(password)) {
                            Intent i=new Intent(LoginActivity.this,MenuNavActivity.class);
                            i.putExtra("phone",phone);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Please enter the correct username or password",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Random:", "onCancelled", databaseError.toException());
                }
            });
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
