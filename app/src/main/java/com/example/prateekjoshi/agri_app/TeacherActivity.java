package com.example.prateekjoshi.agri_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<VideoItem> videos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.teacher_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, getTheme()));
        toolbar.setTitle("Teacher");
        setSupportActionBar(toolbar);

        videos = getList();

        mRecyclerView = (RecyclerView) findViewById(R.id.teacher_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TeacherAdapter(this, videos);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MenuNavActivity.class);
        startActivity(i);
    }

    public List<VideoItem> getList() {

        List<VideoItem> vids = new ArrayList<VideoItem>();
        vids.add(new VideoItem("Soil Management", R.drawable.soil));
        vids.add(new VideoItem("Irrigation", R.drawable.irrigation));
        vids.add(new VideoItem("Nutrition/Fertilizers", R.drawable.nutrition));
        vids.add(new VideoItem("Certification/Organic", R.drawable.certification));
        vids.add(new VideoItem("Pesticides", R.drawable.pesticides));
        vids.add(new VideoItem("Pruning Trees/Plants", R.drawable.pruning));
        vids.add(new VideoItem("Harvesting", R.drawable.harvesting));
        vids.add(new VideoItem("Loss Prevention", R.drawable.loss_prevention));
        vids.add(new VideoItem("Packing & Transport", R.drawable.packing));
        vids.add(new VideoItem("New Technologies", R.drawable.newtech));

        return vids;
    }
}
