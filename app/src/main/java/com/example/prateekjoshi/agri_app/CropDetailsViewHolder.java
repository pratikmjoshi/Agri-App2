package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Prateek Joshi on 12/31/2016.
 */

public class CropDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView cropName;
    public TextInputEditText cropHectares;
    public TextInputEditText cropQuintals;
    public ImageView cropPicture;
    List<CropItem> list;
    private Context context;

    public CropDetailsViewHolder(View itemView, Context context, List<CropItem> list) {
        super(itemView);
        this.context = context;
        this.list = list;
        cropName = (TextView) itemView.findViewById(R.id.item_format_cropdetails_title);
        cropHectares = (TextInputEditText) itemView.findViewById(R.id.profile_cropdetails_hectares_edittext);
        cropQuintals = (TextInputEditText) itemView.findViewById(R.id.profile_cropdetails_quintals_edittext);
        cropPicture = (ImageView)itemView.findViewById(R.id.profile_cropdetails_croppic);


        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

    }
}
