package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Registration2 extends Fragment {
    private android.support.design.widget.TextInputEditText editTextFirstName;
    private android.support.design.widget.TextInputEditText editTextMiddleName;
    private android.support.design.widget.TextInputEditText editTextLastName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration2, container, false);

        editTextFirstName= (TextInputEditText) v.findViewById(R.id.reg2_firstname_edittext);
        editTextMiddleName= (TextInputEditText) v.findViewById(R.id.reg2_middlename_edittext);
        editTextLastName= (TextInputEditText) v.findViewById(R.id.reg2_lastname_edittext);

        return v;

    }
    public static Registration2 newInstance(String text) {

        Registration2 f = new Registration2();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
