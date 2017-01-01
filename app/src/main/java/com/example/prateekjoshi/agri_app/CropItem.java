package com.example.prateekjoshi.agri_app;

/**
 * Created by Prateek Joshi on 12/31/2016.
 */

public class CropItem {
    private String crop;
    private int hectares;
    private int quintals;

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public int getHectares() {
        return hectares;
    }

    public void setHectares(int hectares) {
        this.hectares=hectares;
    }

    public int getQuintals() {
        return quintals;
    }

    public void setQuintals(int quintals) {
        this.quintals = quintals;
    }
}
