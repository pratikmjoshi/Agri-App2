package com.example.prateekjoshi.agri_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Prateek Joshi on 11/21/2016.
 */

public class ProfileDetails {

    private String phone;
    private String password;
    private String teamMemberCount;
    private String uid;

    private String address;
    private String province;
    private String postalCode;

    private String firstname;
    private String middlename;
    private String lastname;

    private boolean ownLand;
    private String nameLand;
    private int hectares;

    private Vector<String> cropDetails;


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

    public Vector<String> getCropDetails() {
        return cropDetails;
    }

    public void setCropDetails(Vector<String> cropDetails) {
        this.cropDetails=cropDetails;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /*public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("User ID", uid);
        map.put("Name", name);
        map.put("Number", number);
        map.put("Email ID", email);
        map.put("College name",college);
        map.put("TeamMember Count", teamMemberCount);
        return map;
    }*/

}

