package com.example.prateekjoshi.agri_app;

import io.realm.RealmObject;

/**
 * Created by Prateek Joshi on 12/24/2016.
 */
public class RealmString extends RealmObject {
    private String val;

    public String get() {
        return val;
    }

    public void set(String value) {
        this.val = value;
    }
}