package com.example.prateekjoshi.agri_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Prateek Joshi on 12/31/2016.
 */

public class CropDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<CropItem> list;
    Context context;
    boolean edit;
    boolean save;

    private Realm realm;

    StringBuilder csvList;

    public CropDetailsAdapter(Context context,List<CropItem> list,boolean edit,boolean save) {
        this.list = list;
        this.context = context;
        this.edit= edit;
        this.save= save;

        csvList = new StringBuilder();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        realm = Realm.getDefaultInstance();

        switch (viewType) {
            case 0:
                View v1 = inflater.inflate(R.layout.item_format_cropdetails, parent, false);
                viewHolder = new CropDetailsViewHolder(v1,parent.getContext(),list);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {

            CropDetailsViewHolder vh = (CropDetailsViewHolder) holder;

            if(save==true) {
                String cropName = vh.cropName.getText().toString();
                String hectares = vh.cropHectares.getText().toString();
                String quintals = vh.cropQuintals.getText().toString();
                vh.cropName.setText(cropName);
                vh.cropHectares.setText(hectares);
                vh.cropQuintals.setText(quintals);
                addCrop(realm,cropName,hectares,quintals);

            }
            else {
                vh.cropName.setText(list.get(position).getCrop());
                vh.cropHectares.setText(Integer.toString(list.get(position).getHectares()));
                vh.cropQuintals.setText(Integer.toString(list.get(position).getQuintals()));
                vh.cropPicture.setImageResource(setImage(list.get(position).getCrop()));

            }
            if(edit==false) {
                vh.cropHectares.setEnabled(false);
                vh.cropQuintals.setEnabled(false);
            }
            else {

                vh.cropHectares.setEnabled(true);
                vh.cropQuintals.setEnabled(true);
            }




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

    public int setImage(String crop){
        if(crop.equals("pineapple")){
            return R.drawable.pineapple;
        }
        if(crop.equals("orange")){
            return R.drawable.orange;
        }
        if(crop.equals("banana")){
            return R.drawable.banana;
        }
        if(crop.equals("cacao pod")){
            return R.drawable.cacaopod;
        }
        if(crop.equals("passionfruit")){
            return R.drawable.passionfruit;
        }
        if(crop.equals("chia seeds")){
            return R.drawable.chia_seeds;
        }
        if(crop.equals("quinoa grains")){
            return R.drawable.quinoagrain;
        }
        return 0;
    }

    public void addCrop(Realm realm,String cropName,String hectares,String quintals) {
        final RealmResults<ProfileDetails> results = realm.where(ProfileDetails.class).findAll();
        String temp=convertToString(cropName,hectares,quintals);
        csvList.append(temp);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(ProfileDetails profileDetails : results) {
                    profileDetails.setCropDetails(csvList.toString());
                }
            }
        });
    }

    public String convertToString(String crop,String hectares,String quintals) {
        String temp = new String();
        temp=crop + ":" + hectares + ":" + quintals + ";";
        return temp;
    }


}
