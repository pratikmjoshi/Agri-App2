package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.Toast;

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

public class ProfileCropDetailsActivity extends AppCompatActivity {

    private Realm realm;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<CropItem> crops;

    private MenuItem edit;
    private MenuItem save;
    private MenuItem cancel;

    private boolean editmenu;
    private String phone;

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_crop_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileactivity_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white,getTheme()));
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        Intent i =getIntent();
        phone = i.getStringExtra("phone");

        editmenu=false;

        realm = Realm.getDefaultInstance();

        crops = update(realm);

        mRecyclerView = (RecyclerView)findViewById(R.id.profile_cropdetails_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CropDetailsAdapter(this, crops,editmenu,false);
        mRecyclerView.setAdapter(mAdapter);





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_toolbar, menu);
        edit = menu.findItem(R.id.profile_edit);
        save = menu.findItem(R.id.profile_save);
        cancel = menu.findItem(R.id.profile_cancel);
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

            editmenu = true;
            mAdapter = new CropDetailsAdapter(this, crops,editmenu,false);
            mRecyclerView.setAdapter(mAdapter);
            invalidateOptionsMenu();
        }
        else
        if (id == R.id.profile_cancel) {

            editmenu = false;
            crops=update(realm);
            mAdapter = new CropDetailsAdapter(this, crops,editmenu,false);
            mRecyclerView.setAdapter(mAdapter);
            invalidateOptionsMenu();
        }
        else
        if (id==R.id.profile_save) {
            saveNew(realm,crops);
            if(isNetworkAvailable(getApplicationContext())) {
                updateOnlineRegistrationDetails();
            }

            editmenu = false;
            crops=update(realm);
            mAdapter = new CropDetailsAdapter(this, crops,editmenu,true);
            mRecyclerView.setAdapter(mAdapter);
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(editmenu==true) {
            edit.setVisible(false);
            save.setVisible(true);
            cancel.setVisible(true);
        }
        else {
            edit.setVisible(true);
            save.setVisible(false);
            cancel.setVisible(false);
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
        Intent i =new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public List<CropItem> update(Realm realm) {

        RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).findAll();
        ProfileDetails profile = new ProfileDetails();
        for(ProfileDetails temp : results) {
            profile = temp;
        }

        String csvList = profile.getCropDetails();
        String[] items = csvList.split(";");
        List<CropItem> list = new ArrayList<CropItem>();
        int num = 0;
        if(csvList==""){
            num=1;
        }
        for(int i=num; i < items.length; i++){
            String[] details = items[i].split(":");
            CropItem temp = new CropItem();
            temp.setCrop(details[0]);
            temp.setHectares(Integer.parseInt(details[1]));
            temp.setQuintals(Integer.parseInt(details[2]));
            list.add(temp);
        }
        return list;

    }



    public void saveNew(Realm realm,List<CropItem> crops) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).equalTo("phone",phone).findAll();
        final StringBuilder csvList = new StringBuilder();
        for(CropItem s : crops){
            String temp=convertToString(s.getCrop(),Integer.toString(s.getHectares()),Integer.toString(s.getQuintals()));
            csvList.append(temp);
        }
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(ProfileDetails profileDetails : results) {
                    profileDetails.setCropDetails(csvList.toString());
                }
            }
        });
    }
    public String convertToString(String crop,String hectares,String quintals) {
        String temp = new String();
        temp=crop + ":" + hectares + ":" + quintals + ";";
        return temp;
    }

    public Map<String, Object> realmMap(Realm realm) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).equalTo("phone",phone).findAll();
        Map<String, Object> map = new HashMap<String, Object>();
        for(ProfileDetails temp: results) {
            Map<String, Object> post = temp.toMap();
            map.put("Registration/", post);
            return map;
        }
        return map;
    }
    public void updateOnlineRegistrationDetails() {
        Map<String,Object> realmMap = realmMap(realm);
        Map<String,Object> realmValueMap = (Map<String,Object>) realmMap.get("Registration/");
        final Map<String,Object> map= new HashMap<>();
        final Map<String,Object> finalMap = new HashMap<String,Object>();
        map.put("Phone Number", realmValueMap.get("Phone Number"));
        map.put("Password",realmValueMap.get("Password") );
        map.put("First Name", realmValueMap.get("First Name"));
        map.put("Middle Name", realmValueMap.get("Middle Name"));
        map.put("Last Name", realmValueMap.get("Last Name"));
        map.put("Address",realmValueMap.get("Address"));
        map.put("Province",realmValueMap.get("Province"));
        map.put("Postal Code",realmValueMap.get("Postal Code"));
        map.put("Rent or Own Land",realmValueMap.get("Rent or Own Land"));
        map.put("Name Land", realmValueMap.get("Name Land"));
        map.put("Hectares of Land", realmValueMap.get("Hectares of Land"));
        map.put("Crop Details",realmValueMap.get("Crop Details"));

        String phone = realmValueMap.get("Phone Number").toString();
        Query query = ref.child("Registration").orderByChild("Phone Number").equalTo(phone);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot Snapshot: dataSnapshot.getChildren()) {
                    Snapshot.getRef().updateChildren(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Random:", "onCancelled", databaseError.toException());
            }
        });



    }
}
