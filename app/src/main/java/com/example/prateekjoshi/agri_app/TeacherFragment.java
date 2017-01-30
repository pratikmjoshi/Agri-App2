package com.example.prateekjoshi.agri_app;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<VideoItem> videos;


    public TeacherFragment() {
        // Required empty public constructor
    }

    public static TeacherFragment newInstance() {
        TeacherFragment fragment = new TeacherFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fresco.initialize(getContext());
        return inflater.inflate(R.layout.fragment_teacher, container, false);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        videos = getList();
        View v = getView();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.teacher_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TeacherAdapter(getContext(), videos);
        mRecyclerView.setAdapter(mAdapter);
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
