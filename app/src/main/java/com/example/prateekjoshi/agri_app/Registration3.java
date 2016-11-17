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



public class Registration3 extends Fragment {
    private android.support.design.widget.TextInputEditText editTextAddress;
    private android.support.design.widget.TextInputEditText editTextProvince;
    private android.support.design.widget.TextInputEditText editTextPostalCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration3, container, false);

        editTextAddress= (TextInputEditText) v.findViewById(R.id.reg3_address_edittext);
        editTextProvince= (TextInputEditText) v.findViewById(R.id.reg3_province_edittext);
        editTextPostalCode= (TextInputEditText) v.findViewById(R.id.reg3_postalcode_edittext);

        return v;

    }
    public static Registration3 newInstance(String text) {

        Registration3 f = new Registration3();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
