package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Prateek Joshi on 12/28/2016.
 */

public class AlertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> list;
    Context context;

    public AlertAdapter(Context context,List<String> list) {
        this.list = list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.item_format_alert, parent, false);
                viewHolder = new AlertViewHolder(v1,parent.getContext(),list);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {



            AlertViewHolder vh = (AlertViewHolder) holder;
            vh.descView.setText(list.get(position));


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
