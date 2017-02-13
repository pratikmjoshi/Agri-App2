package com.example.prateekjoshi.agri_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Prateek Joshi on 1/16/2017.
 */

public class PreRegistration extends AppCompatActivity {
    public ImageButton next;
    public ImageButton previous;

    private RadioGroup versionGroup;
    private RadioButton versionButton;

    public String version;
    public String phone;


    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preregistration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.prereg_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        toolbar.setTitle("Registro");
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        Intent i = getIntent();
        if (i.getBooleanExtra("Registerdialog", false) == true) {
            phone = i.getStringExtra("Phone");
            Log.d("confirm", phone);
            regfinishdialog();
        }

        versionGroup = (RadioGroup) findViewById(R.id.prereg_version_radiogrp);

        next = (ImageButton) findViewById(R.id.prereg_btn_next);
        previous = (ImageButton) findViewById(R.id.prereg_btn_back);
        previous.setVisibility(View.INVISIBLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = versionGroup.getCheckedRadioButtonId();
                versionButton = (RadioButton) findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(getApplicationContext(), "Seleccione una versión", Toast.LENGTH_SHORT).show();
                } else {
                    if (versionButton.getText().toString().equals("Teléfono inteligente")) {
                        version = "Smartphone";
                    } else if (versionButton.getText().toString().equals("Teléfono móvil")) {
                        version = "Mobile Phone";
                    } else if (versionButton.getText().toString().equals("Computadora personal")) {
                        version = "Personal Computer";
                    } else if (versionButton.getText().toString().equals("Teléfono fijo(Voz)")) {
                        version = "Telephone";
                    }
                    Log.d("version", version);
                    update(realm);
                    Intent i = new Intent(PreRegistration.this, Registration2.class);
                    i.putExtra("Phone", phone);
                    startActivity(i);
                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:

                moveTaskToBack(true);

                return true;
        }
        return false;
    }
    public void update(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).equalTo("phone", phone).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (ProfileDetails profileDetails : results) {
                    profileDetails.setVersion(version);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

    public void regfinishdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tu registro esta completo.\nPor favor, rellene los detalles de su perfil.")
                .setTitle("\t\t\t\t\tRegistro completo")
                .setCancelable(false)
                .setPositiveButton("\n" +
                        "Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.6f; // Dim level. 0.0 - no dim, 1.0 - completely opaque
        dialog.getWindow().setAttributes(lp);

    }

}
