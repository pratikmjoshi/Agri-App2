package com.example.prateekjoshi.agri_app;

import android.app.Activity;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmResults;

public class MenuNavActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Registration");
    Query query;
    private Realm realm;
    private ValueEventListener listener;

    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        toolbar.setTitle("KIKI Central");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        realm = Realm.getDefaultInstance();
        Intent i = getIntent();
        phone = i.getStringExtra("phone");

        listener = returnEventListener();
        query = ref.orderByChild("Phone Number").equalTo(phone);
        query.addValueEventListener(listener);

        //syncLocalToOnline();

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // Start all your intent for activities in here

        //get id of the view that was clicked
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            Intent i = new Intent(this,ProfileActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_teacher) {

            //Intent aboutUsActivity = new Intent(MainActivity.this, WavesAbout.class);
            //startActivity(aboutUsActivity);


        } else if (id == R.id.nav_alert) {

            Intent intent = new Intent(this,AlertActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_crop) {

            Intent i = new Intent(getApplicationContext(),HarvestActivity.class);
            i.putExtra("phone",phone);
            startActivity(i);


        } else if (id == R.id.nav_delivery) {
            Intent intent = new Intent(this, DeliveryActivity.class);
            intent.putExtra("phone",phone);
            startActivity(intent);

        }

        //Close drawer upon click
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_menu_nav, container, false);
            return rootView;
        }

    }
    @Override
    public void onStart() {
        super.onStart();


        realm = Realm.getDefaultInstance();

        listener = returnEventListener();
        query = ref.orderByChild("Phone Number").equalTo(phone);
        query.addValueEventListener(listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        query.removeEventListener(listener);
        realm.close();
    }

    private ValueEventListener returnEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(ProfileDetails.class);
                    }
                });
                for (DataSnapshot shot : dataSnapshot.getChildren()) {

                    try {
                        final String phone = shot.child("Phone Number").getValue().toString();
                        final String password = shot.child("Password").getValue().toString();
                        final String firstName = shot.child("First Name").getValue().toString();
                        final String middleName = shot.child("Middle Name").getValue().toString();
                        final String lastName = shot.child("Last Name").getValue().toString();
                        final String address = shot.child("Address").getValue().toString();
                        final String province = shot.child("Province").getValue().toString();
                        final String postalCode = shot.child("Postal Code").getValue().toString();
                        final boolean ownLand = (Boolean) shot.child("Rent or Own Land").getValue();
                        final String nameLand = shot.child("Name Land").getValue().toString();
                        final int hectares = (int) (long) shot.child("Hectares of Land").getValue();
                        final String cropDetails = shot.child("Crop Details").getValue().toString();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                ProfileDetails bar = realm.createObject(ProfileDetails.class);
                                bar.setPhone(phone);
                                bar.setPassword(password);
                                Log.d("pass",password);
                                bar.setFirstName(firstName);
                                bar.setMiddleName(middleName);
                                bar.setLastName(lastName);
                                bar.setAddress(address);
                                bar.setProvince(province);
                                bar.setPostalCode(postalCode);
                                bar.setOwnLand(ownLand);
                                bar.setNameLand(nameLand);
                                bar.setHectares(hectares);
                                bar.setCropDetails(cropDetails);
                            }
                        });
                    } catch (Exception e) {
                        Log.d("Exception", "not saved");
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Random:", "onCancelled", databaseError.toException());
            }
        };
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }

    /*public void syncLocalToOnline() {
        Query query = ref.child("Registration").orderByChild("Phone Number").equalTo(phone);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<ProfileDetails> result = realm.where(ProfileDetails.class).findAll();
                            result.deleteAllFromRealm();
                        }
                    });
                for (DataSnapshot shot : dataSnapshot.getChildren()) {

                    try {
                            final String phone = shot.child("Phone Number").getValue().toString();
                            final String password = shot.child("Password").getValue().toString();
                            final String firstName = shot.child("First Name").getValue().toString();
                            final String middleName = shot.child("Middle Name").getValue().toString();
                            final String lastName = shot.child("Last Name").getValue().toString();
                            final String address = shot.child("Address").getValue().toString();
                            final String province = shot.child("Province").getValue().toString();
                            final String postalCode = shot.child("Postal Code").getValue().toString();
                            final boolean ownLand = (Boolean) shot.child("Rent or Own Land").getValue();
                            final String nameLand = shot.child("Name Land").getValue().toString();
                            final int hectares = (Integer) shot.child("Hectares").getValue();
                            final String cropDetails = shot.child("Crop Details").getValue().toString();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    ProfileDetails bar = realm.createObject(ProfileDetails.class);
                                    bar.setPhone(phone);
                                    bar.setPassword(password);
                                    bar.setFirstName(firstName);
                                    bar.setMiddleName(middleName);
                                    bar.setLastName(lastName);
                                    bar.setAddress(address);
                                    bar.setProvince(province);
                                    bar.setPostalCode(postalCode);
                                    bar.setOwnLand(ownLand);
                                    bar.setNameLand(nameLand);
                                    bar.setHectares(hectares);
                                    bar.setCropDetails(cropDetails);
                                }
                            });
                        } catch (Exception e) {
                            Log.d("Exception2","Naat");
                            e.printStackTrace();
                        }

                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Random:", "onCancelled", databaseError.toException());
            }
        });
    }*/

    public void update(Realm realm) {



    }
}
