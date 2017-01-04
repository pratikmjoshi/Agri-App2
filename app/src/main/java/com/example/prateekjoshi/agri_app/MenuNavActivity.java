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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import io.realm.Realm;
import io.realm.RealmResults;

public class MenuNavActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Realm realm;
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

        RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).findAll();
        for(ProfileDetails temp : results) {
            phone = temp.getPhone();
        }

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
    public void onBackPressed() {
        // do nothing.
    }

}
