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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
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
                Intent i=new Intent(LoginActivity.this,MenuScreen.class);
                startActivity(i);
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

}
