package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Prateek Joshi on 1/16/2017.
 */

public class TeacherViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView videoName;
    public ImageView videoPicture;
    List<VideoItem> list;
    private Context context;

    public TeacherViewHolder(View itemView, Context context, List<VideoItem> list) {
        super(itemView);
        this.context = context;
        this.list = list;
        videoName = (TextView) itemView.findViewById(R.id.teacher_video_title);
        videoPicture = (ImageView)itemView.findViewById(R.id.teacher_videopic);


        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + "jNQXAC9IVRw"));
        context.startActivity(intent);


    }
}