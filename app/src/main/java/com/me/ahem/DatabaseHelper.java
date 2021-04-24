package com.me.ahem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Tables
    public static final String REMINDER_TABLE = "REMINDER_TABLE";
    public static final String LOCATION_TABLE = "LOCATION_TABLE";
    public static final String ADDRESS_TABLE = "ADDRESS_TABLE";

    //Columns
    public static final String REMINDER_ID = "reminder_id";
    public static final String NAME = "name";
    public static final String REMINDER_DESCRIPTION = "reminder_description";
    public static final String LOCATION_ID = "location_id";
    public static final String ADDRESS_ID = "address_id";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String RADIUS = "radius";
    public static final String TIME = "time";
    public static final String STREET = "street";
    public static final String STREET_NUMBER = "street_number";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String ZIP = "zip";
    public static final String COUNTRY = "country";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Ahem.db", null, 1);
    }

    // Call first time database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createReminderTableStatement = "CREATE TABLE " + REMINDER_TABLE + " (" + REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + REMINDER_DESCRIPTION + " TEXT)";
        String createLocationTableStatement = "CREATE TABLE " + LOCATION_TABLE + "(" + LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ADDRESS_ID + " INTEGER, " + LONGITUDE + " REAL, " + LATITUDE + " REAL, " + RADIUS + " REAL, " + TIME + " INTEGER)";
        String createAddressTableStatement = "CREATE TABLE " + ADDRESS_TABLE + "(" + ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + STREET + " TEXT, " + STREET_NUMBER + " INTEGER, " + CITY + " TEXT, " + STATE + " TEXT, " + ZIP + " TEXT, " + COUNTRY + " TEXT)";

        db.execSQL(createReminderTableStatement);
        db.execSQL(createLocationTableStatement);
        db.execSQL(createAddressTableStatement);
    }

    // Call when version number of database changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
