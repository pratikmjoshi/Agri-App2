package com.example.prateekjoshi.agri_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class Registration2 extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;

    private android.support.design.widget.TextInputEditText editTextFirstName;
    private android.support.design.widget.TextInputEditText editTextMiddleName;
    private android.support.design.widget.TextInputEditText editTextLastName;

    public String firstName;
    public String middleName;
    public String lastName;
    public String phone;

    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.reg2_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        Intent i = getIntent();
        phone = i.getStringExtra("Phone");


        next = (ImageButton) findViewById(R.id.reg2_btn_next);
        previous = (ImageButton) findViewById(R.id.reg2_btn_back);

        editTextFirstName = (TextInputEditText) findViewById(R.id.reg2_firstname_edittext);
        editTextFirstName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editTextMiddleName = (TextInputEditText) findViewById(R.id.reg2_middlename_edittext);
        editTextMiddleName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editTextLastName = (TextInputEditText) findViewById(R.id.reg2_lastname_edittext);
        editTextLastName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextFirstName.getText().toString().equals("") || editTextLastName.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a first name or a last name", Toast.LENGTH_SHORT).show();
                } else {
                    firstName = editTextFirstName.getText().toString();
                    middleName = editTextMiddleName.getText().toString();
                    lastName = editTextLastName.getText().toString();

                    update(realm);
                    Intent i = new Intent(Registration2.this, Registration3.class);
                    i.putExtra("Phone", phone);
                    startActivity(i);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registration2.this, PreRegistration.class);
                i.putExtra("Phone", phone);
                startActivity(i);
            }
        });

    }

    public void regfinishdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your registration is complete!\nPlease fill out your profile details.")
                .setTitle("Registration complete")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.6f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
        dialog.getWindow().setAttributes(lp);

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
                    profileDetails.setFirstName(firstName);
                    profileDetails.setMiddleName(middleName);
                    profileDetails.setLastName(lastName);
                    Log.d("kk", profileDetails.getFirstName());
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



