package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Prateek Joshi on 1/16/2017.
 */

public class TeacherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<VideoItem> list;

    Context context;

    public TeacherAdapter(Context context,List<VideoItem> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.item_format_teachervideo, parent, false);
                viewHolder = new TeacherViewHolder(v1,parent.getContext(),list);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {

            final TeacherViewHolder vh = (TeacherViewHolder) holder;

            vh.videoName.setText(list.get(position).getVideoName());
            vh.videoPicture.setImageURI(Uri.parse("res:///" + list.get(position).getVideoPicture()));



        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

}
