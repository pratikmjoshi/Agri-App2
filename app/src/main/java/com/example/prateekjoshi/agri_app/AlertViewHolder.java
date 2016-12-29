package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Prateek Joshi on 12/28/2016.
 */

public class AlertViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView titleView;
    public TextView descView;
    List<String> list;
    private Context context;

    public AlertViewHolder(View itemView, Context context, List<String> list) {
        super(itemView);
        this.context = context;
        this.list = list;
        titleView = (TextView) itemView.findViewById(R.id.item_format_alert_title);
        titleView.setText("Alert");
        descView = (TextView) itemView.findViewById(R.id.item_format_alert_description);

        itemView.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

    }
}
