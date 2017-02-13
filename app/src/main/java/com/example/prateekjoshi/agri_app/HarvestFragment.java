package com.example.prateekjoshi.agri_app;


import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HarvestFragment extends Fragment {

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private String phone;
    private String totalAmount;
    private String amountPerHectare;
    private boolean color;
    private boolean colorStem;
    private boolean marks;
    private boolean diseases;
    private boolean bugs;
    private boolean colorSoil;
    private boolean pesticide;

    private TextInputEditText totalAmountEditText;
    private TextInputEditText amountPerHectareEditText;
    private RadioGroup colorGroup;
    private RadioButton colorButton;
    private RadioGroup colorStemGroup;
    private RadioButton colorStemButton;
    private RadioGroup marksGroup;
    private RadioButton marksButton;
    private RadioGroup diseasesGroup;
    private RadioButton diseasesButton;
    private RadioGroup bugsGroup;
    private RadioButton bugsButton;
    private RadioGroup colorSoilGroup;
    private RadioButton colorSoilButton;
    private RadioGroup pesticideGroup;
    private RadioButton pesticideButton;
    private Button submit;

    private MenuItem edit;
    private MenuItem save;
    private MenuItem cancel;

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public HarvestFragment() {
        // Required empty public constructor
    }

    public static HarvestFragment newInstance() {
        HarvestFragment fragment = new HarvestFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_harvest, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();

        totalAmountEditText = (TextInputEditText) v.findViewById(R.id.harvest_totalamount_edittext);
        amountPerHectareEditText = (TextInputEditText) v.findViewById(R.id.harvest_amountphectare_edittext);
        colorGroup = (RadioGroup) v.findViewById(R.id.harvest_colordiff_radiogrp);
        colorStemGroup = (RadioGroup) v.findViewById(R.id.harvest_colordiffstem_radiogrp);
        marksGroup = (RadioGroup) v.findViewById(R.id.harvest_marks_radiogrp);
        diseasesGroup = (RadioGroup) v.findViewById(R.id.harvest_diseases_radiogrp);
        bugsGroup = (RadioGroup) v.findViewById(R.id.harvest_bugs_radiogrp);
        colorSoilGroup = (RadioGroup) v.findViewById(R.id.harvest_soilcolor_radiogrp);
        pesticideGroup = (RadioGroup) v.findViewById(R.id.harvest_pesticide_radiogrp);
        submit = (Button) v.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getContext())) {
                    updateOnlineRegistrationDetails();
                }


                Toast.makeText(getContext(), "Detalles de la cosecha presentada", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void updateOnlineRegistrationDetails() {
        totalAmount = totalAmountEditText.getText().toString();
        amountPerHectare = amountPerHectareEditText.getText().toString();
        color = radioCheck(colorGroup, colorButton);
        colorStem = radioCheck(colorStemGroup, colorStemButton);
        marks = radioCheck(marksGroup, marksButton);
        diseases = radioCheck(diseasesGroup, diseasesButton);
        bugs = radioCheck(bugsGroup, bugsButton);
        colorSoil = radioCheck(colorSoilGroup, colorSoilButton);
        pesticide = radioCheck(pesticideGroup, pesticideButton);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> finalMap = new HashMap<String, Object>();
        map.put("Phone Number", phone);
        map.put("TotalProduction", totalAmount);
        map.put("AmountPerHectare", amountPerHectare);
        map.put("Color", color);
        map.put("ColorStem", colorStem);
        map.put("Marks", marks);
        map.put("Diseases", diseases);
        map.put("Bugs", bugs);
        map.put("SoilColor", colorSoil);
        map.put("Pesticide", pesticide);

        String uniqueId = ref.child("Harvest").push().getKey();

        finalMap.put("Harvest/" + uniqueId, map);

        ref.updateChildren(finalMap);


    }

    public boolean radioCheck(RadioGroup group, RadioButton button) {
        int selectedId = group.getCheckedRadioButtonId();


        button = (RadioButton) getView().findViewById(selectedId);
        if (selectedId == -1) {

        } else {
            if (button.getText().toString().equals("Sí")) {

                return true;
            } else if (button.getText().toString().equals("No")) {
                return false;
            }


        }
        return false;
    }
}
