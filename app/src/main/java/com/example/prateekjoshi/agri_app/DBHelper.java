package com.example.prateekjoshi.agri_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Prateek Joshi on 11/21/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProfileDetails.db";
    public static final String PROFILE_TABLE_NAME = "profile";
    public static final String PHONE_COLUMN = "phone";
    public static final String PASSWORD_COLUMN = "password";
    public static final String FIRST_NAME_COLUMN = "first name";
    public static final String MIDDLE_NAME_COLUMN = "middle name";
    public static final String LAST_NAME_COLUMN= "last name";
    public static final String ADDRESS_COLUMN = "address";
    public static final String PROVINCE_COLUMN = "province";
    public static final String POSTAL_CODE_COLUMN = "postal code";
    public static final String OWN_LAND_COLUMN= "own land";
    public static final String NAME_LAND_COLUMN = "name land";
    public static final String HECTARES_COLUMN = "hectares";
    public static final String CROPDETAILS_COLUMN= "crop details";
    public static final String UNIQUE_ID_COLUMN= "id";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table profile " +
                        "(id integer primary key, phone text, password text, first name text, middle name text, last name text, address text, province text, own land boolean, name land text, hectares integer,crop details text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean Profile1(String phone,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PHONE_COLUMN,phone);
        contentValues.put(PASSWORD_COLUMN, password);
        contentValues.put(FIRST_NAME_COLUMN,"");
        contentValues.put(MIDDLE_NAME_COLUMN,"");
        contentValues.put(LAST_NAME_COLUMN,"");
        contentValues.put(ADDRESS_COLUMN,"");
        contentValues.put(PROVINCE_COLUMN,"");
        contentValues.put(POSTAL_CODE_COLUMN,"");
        contentValues.put(OWN_LAND_COLUMN,false);
        contentValues.put(NAME_LAND_COLUMN,"");
        contentValues.put(HECTARES_COLUMN,0);
        contentValues.put(CROPDETAILS_COLUMN,"");

        db.insert(PROFILE_TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean Profile2 (int uniqueId,String firstname,String middlename,String lastname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME_COLUMN, firstname);
        contentValues.put(MIDDLE_NAME_COLUMN, middlename);
        contentValues.put(LAST_NAME_COLUMN, lastname);
        db.update(PROFILE_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(uniqueId) } );
        return true;
    }
    public boolean Profile3 (int uniqueId,String address,String province,String postalCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADDRESS_COLUMN, address);
        contentValues.put(PROVINCE_COLUMN, province);
        contentValues.put(POSTAL_CODE_COLUMN, postalCode);
        db.update(PROFILE_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(uniqueId) } );
        return true;
    }
    public boolean Profile4 (int uniqueId,boolean ownLand,String nameLand,int hectares) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(OWN_LAND_COLUMN, ownLand);
        contentValues.put(NAME_LAND_COLUMN, nameLand);
        contentValues.put(HECTARES_COLUMN, hectares);
        db.update(PROFILE_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(uniqueId) } );
        return true;
    }
    public boolean Profile5 (int uniqueId,String cropDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROPDETAILS_COLUMN, cropDetails);
        db.update(PROFILE_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(uniqueId) } );
        return true;
    }

    public Integer deleteProfile (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PROFILE_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

}
