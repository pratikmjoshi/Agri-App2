package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class SingleCropProfileActivity extends AppCompatActivity {

    private Realm realm;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private String crop;

    private TextInputEditText cropHectares;
    private TextInputEditText cropQuintals;
    private ImageView cropPicture;
    private TextView cropName;


    private MenuItem edit;
    private MenuItem save;
    private MenuItem cancel;
    private MenuItem delete;

    private boolean editmenu;

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_crop_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_single_crop_profile_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        toolbar.setTitle("Crop Details");
        setSupportActionBar(toolbar);

        editmenu = false;

        realm = Realm.getDefaultInstance();

        cropHectares = (TextInputEditText) findViewById(R.id.scrop_crop_per_hectare_edittext);
        cropQuintals = (TextInputEditText) findViewById(R.id.scrop_crop_quintal_edittext);
        cropPicture = (ImageView) findViewById(R.id.scrop_croppic);
        cropName = (TextView) findViewById(R.id.scrop_croppic_textview);

        Intent i = getIntent();
        crop = i.getStringExtra("cropName");


        cropPicture.setImageResource(setImage(crop));
        cropName.setText(setCropText(crop));

        update(realm);

        disableFields();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_toolbar, menu);
        edit = menu.findItem(R.id.profile_edit);
        save = menu.findItem(R.id.profile_save);
        cancel = menu.findItem(R.id.profile_cancel);
        delete = menu.findItem(R.id.profile_delete);
        delete.setVisible(true);
        edit.setVisible(true);
        save.setVisible(false);
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
        if (id == R.id.profile_edit) {
            enableFields();
            editmenu = true;
            invalidateOptionsMenu();
        } else if (id == R.id.profile_cancel) {
            disableFields();
            editmenu = false;
            invalidateOptionsMenu();
        } else if (id == R.id.profile_save) {
            saveNew(realm);
            if (isNetworkAvailable(getApplicationContext())) {
                updateOnlineRegistrationDetails();
            }
            disableFields();
            editmenu = false;
            invalidateOptionsMenu();
        } else if (id == R.id.profile_delete) {
            openAlertDialog();
            if (isNetworkAvailable(getApplicationContext())) {
                updateOnlineRegistrationDetails();
            }
            disableFields();
            editmenu = false;
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (editmenu == true) {
            edit.setVisible(false);
            save.setVisible(true);
            cancel.setVisible(true);
            delete.setVisible(false);
        } else {
            edit.setVisible(true);
            save.setVisible(false);
            cancel.setVisible(false);
            delete.setVisible(true);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

    @Override
    public void onBackPressed() {
        if (isNetworkAvailable(getApplicationContext())) {
            updateOnlineRegistrationDetails();
        }
        Intent i = new Intent(this, ProfileCropDetailsActivity.class);
        startActivity(i);
    }

    public void disableFields() {
        cropHectares.setEnabled(false);
        cropQuintals.setEnabled(false);
    }

    public void enableFields() {
        cropHectares.setEnabled(true);
        cropQuintals.setEnabled(true);
    }

    public int setImage(String crop) {
        if (crop.equals("pineapple")) {
            return R.drawable.pineapple;
        }
        if (crop.equals("orange")) {
            return R.drawable.orange;
        }
        if (crop.equals("banana")) {
            return R.drawable.banana;
        }
        if (crop.equals("cacao pod")) {
            return R.drawable.cacaopod;
        }
        if (crop.equals("passionfruit")) {
            return R.drawable.passionfruit;
        }
        if (crop.equals("chia seeds")) {
            return R.drawable.chia_seeds;
        }
        if (crop.equals("quinoa grains")) {
            return R.drawable.quinoagrain;
        }
        return 0;
    }

    public String setCropText(String crop) {
        if (crop.equals("pineapple")) {
            return "Pineapple";
        }
        if (crop.equals("orange")) {
            return "Orange";
        }
        if (crop.equals("banana")) {
            return "Banana";
        }
        if (crop.equals("cacao pod")) {
            return "Cacao Pod";
        }
        if (crop.equals("passionfruit")) {
            return "Passionfruit";
        }
        if (crop.equals("chia seeds")) {
            return "Chia Seeds";
        }
        if (crop.equals("quinoa grains")) {
            return "Quinoa Grains";
        }
        return "Crop";
    }

    public void update(Realm realm) {

        RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).findAll();
        ProfileDetails profile = new ProfileDetails();
        for (ProfileDetails temp : results) {
            profile = temp;
        }

        String csvList = profile.getCropDetails();
        String[] items = csvList.split(";");
        List<CropItem> list = new ArrayList<CropItem>();
        int num = 0;
        if (csvList == "") {
            num = 1;
        }
        for (int i = num; i < items.length; i++) {

            String[] details = items[i].split(":");
            CropItem temp = new CropItem();
            if (details[0].equals(crop)) {
                cropHectares.setText(details[1]);
                cropQuintals.setText(details[2]);
            }
        }


    }

    public void saveNew(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).findAll();
        final String newCrop = convertToString(cropName.getText().toString(), cropHectares.getText().toString(), cropQuintals.getText().toString());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (ProfileDetails profileDetails : results) {
                    String newCropDetails = new String();
                    String csvList = profileDetails.getCropDetails();
                    String[] items = csvList.split(";");
                    int num = 0;
                    if (csvList == "") {
                        num = 1;
                    }
                    for (int i = num; i < items.length; i++) {

                        String[] details = items[i].split(":");
                        if (!details[0].equals(crop)) {
                            newCropDetails = newCropDetails + items[i] + ";";
                        }

                    }
                    newCropDetails = newCropDetails + newCrop;
                    profileDetails.setCropDetails(newCropDetails);
                }

            }
        });
    }

    public void deleteCrop(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (ProfileDetails profileDetails : results) {
                    String newCropDetails = new String();
                    String csvList = profileDetails.getCropDetails();
                    String[] items = csvList.split(";");
                    int num = 0;
                    if (csvList == "") {
                        num = 1;
                    }
                    for (int i = num; i < items.length; i++) {

                        String[] details = items[i].split(":");
                        if (!details[0].equals(crop)) {
                            newCropDetails = newCropDetails + items[i] + ";";
                        }

                    }
                    profileDetails.setCropDetails(newCropDetails);
                }

            }
        });

    }

    public String convertToString(String crop, String hectares, String quintals) {
        String temp = new String();
        temp = crop.toLowerCase() + ":" + hectares + ":" + quintals + ";";
        return temp;
    }

    public Map<String, Object> realmMap(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        for (ProfileDetails temp : results) {
            Map<String, Object> post = temp.toMap();
            map.put("Registration/", post);
            return map;
        }
        return map;
    }

    public void updateOnlineRegistrationDetails() {
        Map<String, Object> realmMap = realmMap(realm);
        Map<String, Object> realmValueMap = (Map<String, Object>) realmMap.get("Registration/");
        final Map<String, Object> map = new HashMap<>();
        final Map<String, Object> finalMap = new HashMap<String, Object>();
        map.put("Phone Number", realmValueMap.get("Phone Number"));
        map.put("Password", realmValueMap.get("Password"));
        map.put("First Name", realmValueMap.get("First Name"));
        map.put("Middle Name", realmValueMap.get("Middle Name"));
        map.put("Last Name", realmValueMap.get("Last Name"));
        map.put("Address", realmValueMap.get("Address"));
        map.put("Province", realmValueMap.get("Province"));
        map.put("Postal Code", realmValueMap.get("Postal Code"));
        map.put("Rent or Own Land", realmValueMap.get("Rent or Own Land"));
        map.put("Name Land", realmValueMap.get("Name Land"));
        map.put("Hectares of Land", realmValueMap.get("Hectares of Land"));
        map.put("Crop Details", realmValueMap.get("Crop Details"));

        String phone = realmValueMap.get("Phone Number").toString();
        Query query = ref.child("Registration").orderByChild("Phone Number").equalTo(phone);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    Snapshot.getRef().updateChildren(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Random:", "onCancelled", databaseError.toException());
            }
        });


    }

    public void openAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(SingleCropProfileActivity.this).create();
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Are you sure you want to delete this crop detail?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCrop(realm);
                        if (isNetworkAvailable(getApplicationContext())) {
                            updateOnlineRegistrationDetails();
                        }
                        Intent i = new Intent(getApplicationContext(), ProfileCropDetailsActivity.class);
                        startActivity(i);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
