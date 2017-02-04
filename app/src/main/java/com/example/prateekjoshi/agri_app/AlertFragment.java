package com.example.prateekjoshi.agri_app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Button clear;

    List<String> alerts;

    private static final int PREFERENCE_MODE_PRIVATE = 0;

    public AlertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert, container, false);
    }

    public static AlertFragment newInstance() {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferencesEditor = sharedPreferences.edit();

        alerts = load();

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.alert_recycler_view);
        clear = (Button) getView().findViewById(R.id.submit);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // store(alerts);

        mAdapter = new AlertAdapter(getContext(), alerts);
        mRecyclerView.setAdapter(mAdapter);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerts.clear();
                store(alerts);
                mAdapter = new AlertAdapter(getContext(), alerts);
                mRecyclerView.setAdapter(mAdapter);

            }
        });
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
}
