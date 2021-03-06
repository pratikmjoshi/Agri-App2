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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private String phone;
    private String crop;
    private String buyer;
    private String buyerPhone;
    private String buyerDesig;
    private String buyerAddress;
    private String location;
    private Map<String, Object> map;
    private List<Map<String, Object>> spinnerList;

    private Spinner cropSpinner;
    private TextInputEditText buyerEditText;
    private TextInputEditText buyerPhoneEditText;
    private RadioGroup buyerDesigRadioGroup;
    private RadioButton buyerDesigRadioButton;
    private TextInputEditText buyerAddressEditText;
    private Spinner locationSpinner;
    private TextInputEditText locationOtherEditText;

    private MenuItem edit;
    private MenuItem save;
    private MenuItem cancel;

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    public DeliveryFragment() {
        // Required empty public constructor
    }


    public static DeliveryFragment newInstance() {
        DeliveryFragment fragment = new DeliveryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v = getView();

        //Pass as a parameter
        //phone = i.getStringExtra("phone");

        cropSpinner = (Spinner) v.findViewById(R.id.delivery_crop_spinner);
        buyerEditText = (TextInputEditText) v.findViewById(R.id.delivery_buyer_edittext);
        buyerPhoneEditText = (TextInputEditText) v.findViewById(R.id.delivery_phone_edittext);
        buyerDesigRadioGroup = (RadioGroup) v.findViewById(R.id.delivery_desig_radiogrp);
        buyerAddressEditText = (TextInputEditText) v.findViewById(R.id.delivery_address_edittext);
        locationSpinner = (Spinner) v.findViewById(R.id.delivery_location_spinner);
        locationOtherEditText = (TextInputEditText) v.findViewById(R.id.delivery_otherlocation_edittext);
        Button submit = (Button) v.findViewById(R.id.submit);

        String[] crops = new String[]{"Piña", "Naranja", "Banana", "Cacao", "Maricamba", "Chia semillahe", "Grano de Quinoa"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.crop_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSpinner.setAdapter(adapter);
        //initializeImageList(crops);
        //CustomSpinnerAdapter customadapter = new CustomSpinnerAdapter(getContext(),spinnerList,android.R.layout.simple_spinner_item, new String[] {"Name", "Icon"},new int[] {R.id.spinner_crop_item_text,R.id.spinner_crop_item_image});
        //cropSpinner.setAdapter(customadapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.location_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter1);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getContext())) {
                    updateOnlineRegistrationDetails();
                }
                Toast.makeText(getContext(), "Submitted Delivery Details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String radioCheck(RadioGroup group, RadioButton button) {
        int selectedId = group.getCheckedRadioButtonId();
        button = (RadioButton) getView().findViewById(selectedId);
        if (selectedId == -1) {

        } else {
            if (button.getText().toString().equals("Proveedor local")) {

                return "Local Vendor";
            } else if (button.getText().toString().equals("Exportador")) {
                return "Exporter";
            } else if (button.getText().toString().equals("Otro")) {
                return "Other";
            }

        }
        return "Other";
    }

    public int setImage(String crop) {
        if (crop.equals("Piña")) {
            return R.drawable.pineapple;
        }
        if (crop.equals("Naranja")) {
            return R.drawable.orange;
        }
        if (crop.equals("Banana")) {
            return R.drawable.banana;
        }
        if (crop.equals("Cacao")) {
            return R.drawable.cacaopod;
        }
        if (crop.equals("Maricamba")) {
            return R.drawable.passionfruit;
        }
        if (crop.equals("Chia semillahe")) {
            return R.drawable.chia_seeds;
        }
        if (crop.equals("Grano de Quinoa")) {
            return R.drawable.quinoagrain;
        }
        return 0;
    }

    public void updateOnlineRegistrationDetails() {
        crop = spantoeng(cropSpinner.getSelectedItem().toString());
        buyer = buyerEditText.getText().toString();
        buyerPhone = buyerPhoneEditText.getText().toString();
        buyerDesig = radioCheck(buyerDesigRadioGroup, buyerDesigRadioButton);
        buyerAddress = buyerAddressEditText.getText().toString();
        location = locationSpinner.getSelectedItem().toString();

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> finalMap = new HashMap<String, Object>();
        map.put("Phone Number", phone);
        map.put("BuyerName", buyer);
        map.put("BuyerPhone", buyerPhone);
        map.put("BuyerAddress", buyerAddress);
        map.put("BuyerDesignation", buyerDesig);
        map.put("Crop", crop);
        map.put("Location", location);

        String uniqueId = ref.child("Delivery").push().getKey();

        finalMap.put("Delivery/" + uniqueId, map);

        ref.updateChildren(finalMap);
    }

    private void initializeImageList(String[] crops) {
        for (String crop1 : crops) {
            map = new HashMap<String, Object>();

            map.put("Name", crop1);
            map.put("Icon", setImage(crop1));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public String spantoeng(String spcrop) {
        if(spcrop.equals("Piña"))
            return "pineapple";
        else if(spcrop.equals("Naranja"))
            return "orange";
        else if(spcrop.equals("Banana"))
            return "banana";
        else if(spcrop.equals("Cacao"))
            return "cacao pod";
        else if(spcrop.equals("Maricamba"))
            return "passionfruit";
        else if(spcrop.equals("Chia semillahe"))
            return "chia seeds";
        else if(spcrop.equals("Naranja"))
            return "quinoa grains";

        return "";
    }
}
