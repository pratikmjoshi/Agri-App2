package com.example.prateekjoshi.agri_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationCodeActivity extends AppCompatActivity {
    private String code;
    public EditText codeEntry;
    public Button enterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_code);

        codeEntry=(EditText)findViewById(R.id.registration_code_edittext);
        enterButton=(Button) findViewById(R.id.registration_code_button);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code= codeEntry.getText().toString();
                if(code.equals("1111")){
                    Intent i=new Intent(RegistrationCodeActivity.this,RegistrationActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(RegistrationCodeActivity.this,"Please enter correct code.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
