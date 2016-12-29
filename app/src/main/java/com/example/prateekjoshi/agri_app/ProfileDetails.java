package com.example.prateekjoshi.agri_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Prateek Joshi on 11/21/2016.
 */

public class ProfileDetails extends RealmObject{

    private String phone;
    private String password;

    private String address;
    private String province;
    private String postalCode;

    private String firstname;
    private String middlename;
    private String lastname;

    private boolean ownLand;
    private String nameLand;
    private int hectares;

    private String cropDetails;


    public ProfileDetails() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname=firstname;
    }

    public String getMiddleName() {
        return middlename;
    }

    public void setMiddleName(String middlename) {
        this.middlename=middlename;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname=lastname;
    }

    public boolean getOwnLand() {
        return ownLand;
    }

    public void setOwnLand(boolean ownLand) {
        this.ownLand=ownLand;
    }

    public String getNameLand() {
        return nameLand;
    }

    public void setNameLand(String nameLand) {
        this.nameLand=nameLand;
    }

    public int getHectares() {
        return hectares;
    }

    public void setHectares(int hectares) {
        this.hectares=hectares;
    }

    public String getCropDetails() {
        return cropDetails;
    }

    public void setCropDetails(String cropDetails) {
        this.cropDetails=cropDetails;
    }


    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Phone Number", phone);
        map.put("Password", password);
        map.put("First Name", firstname);
        map.put("Middle Name", middlename);
        map.put("Last Name", lastname);
        map.put("Address",address);
        map.put("Province",province);
        map.put("Postal Code",postalCode);
        map.put("Rent or Own Land",ownLand);
        map.put("Name Land", nameLand);
        map.put("Hectares of Land", hectares);
        map.put("Crop Details", cropDetails);
        return map;
    }

}

