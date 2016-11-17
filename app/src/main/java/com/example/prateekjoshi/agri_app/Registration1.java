package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;


public class Registration1 extends Fragment {
    private android.support.design.widget.TextInputEditText editTextPhone;
    private android.support.design.widget.TextInputEditText editTextPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration1, container, false);

        editTextPhone = (TextInputEditText) v.findViewById(R.id.reg1_phone_edittext);
        editTextPassword = (TextInputEditText) v.findViewById(R.id.reg1_password_edittext);


        return v;

    }
    public static Registration1 newInstance(String text) {

        Registration1 f = new Registration1();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
