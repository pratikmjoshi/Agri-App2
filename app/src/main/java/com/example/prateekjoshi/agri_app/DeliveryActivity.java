package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryActivity extends AppCompatActivity {

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    private String phone;
    private String crop;
    private String buyer;
    private String buyerPhone;
    private String buyerDesig;
    private String buyerAddress;
    private String location;
    private Map<String,Object> map;
    private List<Map<String,Object>> spinnerList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_delivery_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white,getTheme()));
        toolbar.setTitle("Delivery");
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        phone = i.getStringExtra("phone");

        cropSpinner = (Spinner) findViewById(R.id.delivery_crop_spinner);
        buyerEditText = (TextInputEditText) findViewById(R.id.delivery_buyer_edittext);
        buyerPhoneEditText = (TextInputEditText)findViewById(R.id.delivery_phone_edittext);
        buyerDesigRadioGroup = (RadioGroup) findViewById(R.id.delivery_desig_radiogrp);
        buyerAddressEditText = (TextInputEditText) findViewById(R.id.delivery_address_edittext);
        locationSpinner = (Spinner) findViewById(R.id.delivery_location_spinner);
        locationOtherEditText = (TextInputEditText) findViewById(R.id.delivery_otherlocation_edittext);

        String[] crops = new String[] {"pineapple","orange","banana","cacao pod","passionfruit","chia seeds","quinoa grains"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.crop_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSpinner.setAdapter(adapter);
        //initializeImageList(crops);
        //CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this,spinnerList,android.R.layout.simple_spinner_item, new String[] {"Name", "Icon"},new int[] {R.id.spinner_crop_item_text,R.id.spinner_crop_item_image});
        cropSpinner.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.location_array,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_toolbar, menu);
        edit = menu.findItem(R.id.profile_edit);
        save = menu.findItem(R.id.profile_save);
        cancel = menu.findItem(R.id.profile_cancel);
        edit.setVisible(false);
        save.setVisible(true);
        save.setTitle("Submit");
        cancel.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id==R.id.profile_save) {
            if(isNetworkAvailable(getApplicationContext())) {
                updateOnlineRegistrationDetails();
            }

            Intent i = new Intent(getApplicationContext(),MenuNavActivity.class);
            Toast.makeText(getApplicationContext(),"Submitted Delivery Details",Toast.LENGTH_SHORT).show();
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent i =new Intent(this, MenuNavActivity.class);
        startActivity(i);
    }

    private void initializeImageList(String[] crops) {

        for(int i=0;i<crops.length;i++) {
            map = new HashMap<String,Object>();

            map.put("Name",crops[i]);
            map.put("Icon",setImage(crops[i]));

        }

    }

    public int setImage(String crop){
        if(crop.equals("pineapple")){
            return R.drawable.pineapple;
        }
        if(crop.equals("orange")){
            return R.drawable.orange;
        }
        if(crop.equals("banana")){
            return R.drawable.banana;
        }
        if(crop.equals("cacao pod")){
            return R.drawable.cacaopod;
        }
        if(crop.equals("passionfruit")){
            return R.drawable.passionfruit;
        }
        if(crop.equals("chia seeds")){
            return R.drawable.chia_seeds;
        }
        if(crop.equals("quinoa grains")){
            return R.drawable.quinoagrain;
        }
        return 0;
    }

    public void updateOnlineRegistrationDetails() {

        crop = cropSpinner.getSelectedItem().toString();
        buyer = buyerEditText.getText().toString();
        buyerPhone = buyerPhoneEditText.getText().toString();
        buyerDesig = radioCheck(buyerDesigRadioGroup,buyerDesigRadioButton);
        buyerAddress = buyerAddressEditText.getText().toString();
        location = locationSpinner.getSelectedItem().toString();

        Map<String,Object> map= new HashMap<>();
        Map<String,Object> finalMap = new HashMap<String,Object>();
        map.put("Phone Number", phone);
        map.put("BuyerName",buyer);
        map.put("BuyerPhone", buyerPhone);
        map.put("BuyerAddress", buyerAddress);
        map.put("BuyerDesignation", buyerDesig);
        map.put("Crop",crop);
        map.put("Location",location);

        String uniqueId = ref.child("Delivery").push().getKey();

        finalMap.put("Delivery/" + uniqueId,map);

        ref.updateChildren(finalMap);

    }

    public String radioCheck(RadioGroup group,RadioButton button) {
        int selectedId=group.getCheckedRadioButtonId();
        button = (RadioButton)findViewById(selectedId);
        if(selectedId==-1){

        }
        else{
            if(button.getText().toString().equals("Local Vendor")){

                return "Local Vendor";
            }
            else
            if(button.getText().toString().equals("Exporter")){
                return "Exporter";
            }
            else
            if(button.getText().toString().equals("Other")){
                return "Other";
            }

        }
        return "Other";
    }


}
