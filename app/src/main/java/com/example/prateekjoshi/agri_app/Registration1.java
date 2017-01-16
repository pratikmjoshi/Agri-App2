package com.example.prateekjoshi.agri_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rd.PageIndicatorView;

import java.lang.reflect.Field;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;

public class Registration1 extends AppCompatActivity{
    public Button next;
    public ImageButton previous;
    public TextView showPassword;
    private TextInputEditText phoneEditText;
    private TextInputEditText passwordEditText;
    private TextInputLayout textInputLayoutPhone;
    private TextInputLayout textInputLayoutPassword;
    private String phone;
    private String password;
    private Realm realm;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static void setInputTextLayoutColor(TextInputLayout til, @ColorInt int color) {
        try {
            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{color}));

            Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("mFocusedTextColor");
            fFocusedTextColor.setAccessible(true);
            fFocusedTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{color}));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        next=(Button)findViewById(R.id.reg1_btn_next);
        //previous=(ImageButton)findViewById(R.id.reg1_btn_back);

        phoneEditText=(TextInputEditText)findViewById(R.id.reg1_phone_edittext);
        passwordEditText=(TextInputEditText)findViewById(R.id.reg1_password_edittext);
        passwordEditText.setTypeface(Typeface.DEFAULT);
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneEditText.getText().toString().equals("")||passwordEditText.getText().toString().equals("")) {
                    if (phoneEditText.getText().toString().equals("") && passwordEditText.getText().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please enter both details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if (passwordEditText.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please enter both details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if (phoneEditText.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please enter both details", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    phone=phoneEditText.getText().toString();
                    password=passwordEditText.getText().toString();

                    update(realm);

                    Intent i=new Intent(Registration1.this,PreRegistration.class);
                    i.putExtra("Registerdialog",true);
                    i.putExtra("Phone",phone);
                    startActivity(i);
                }
            }
        });

        /*previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitConfirmDialog();
            }
        }); */



    }
    public void exitConfirmDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Warning");
        alertDialogBuilder.setMessage("Are you sure you want to exit registration? All data will be lost.");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i= new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        exitConfirmDialog();
    }

    public void update(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<ProfileDetails> result = realm.where(ProfileDetails.class).findAll();
                result.deleteAllFromRealm();
            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person
                ProfileDetails profileDetails = realm.createObject(ProfileDetails.class);
                profileDetails.setPhone(phone);
                profileDetails.setPassword(password);
                profileDetails.setVersion("");
                profileDetails.setFirstName("");
                profileDetails.setMiddleName("");
                profileDetails.setLastName("");
                profileDetails.setAddress("");
                profileDetails.setProvince("");
                profileDetails.setPostalCode("");
                profileDetails.setOwnLand(false);
                profileDetails.setNameLand("");
                profileDetails.setHectares(0);
                profileDetails.setCropDetails("");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

}

