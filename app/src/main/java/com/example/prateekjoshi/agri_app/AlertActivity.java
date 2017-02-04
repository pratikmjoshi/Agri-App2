package com.example.prateekjoshi.agri_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlertActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;


    List<String> alerts;

    private static final int PREFERENCE_MODE_PRIVATE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.alert_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        toolbar.setTitle("Alert Notifications");
        setSupportActionBar(toolbar);

        sharedPreferences = getPreferences(PREFERENCE_MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        alerts = load();

        mRecyclerView = (RecyclerView) findViewById(R.id.alert_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Intent i = getIntent();
        String alert = i.getStringExtra("Alert Message");
        if (alert != null) {
            String link = i.getStringExtra("link");
            alert = alert + "-" + link;
            Log.d("stringy", alert);
            alerts.add(alert);
        }


        store(alerts);


        mAdapter = new AlertAdapter(this, alerts);
        mRecyclerView.setAdapter(mAdapter);


    }

    public List<String> load() {
        String csvList = sharedPreferences.getString("myList", "");
        String[] items = csvList.split(",");
        List<String> list = new ArrayList<String>();
        int num = 0;
        if (csvList.isEmpty()) {
            num = 1;
        }
        list.addAll(Arrays.asList(items).subList(num, items.length));
        return list;
    }

    public void store(List<String> alerts) {
        StringBuilder csvList = new StringBuilder();
        for (String s : alerts) {
            csvList.append(s);
            csvList.append(",");
        }

        sharedPreferencesEditor.putString("myList", csvList.toString());
        sharedPreferencesEditor.commit();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MenuNavActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alert_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notif_clear) {
            alerts.clear();
            store(alerts);
            mAdapter = new AlertAdapter(getApplicationContext(), alerts);
            mRecyclerView.setAdapter(mAdapter);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
