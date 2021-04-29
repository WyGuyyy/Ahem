package com.me.ahem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

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
        String createReminderTableStatement = "CREATE TABLE " + REMINDER_TABLE + " (" + REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LOCATION_ID + " INTEGER, " + NAME + " TEXT, " + REMINDER_DESCRIPTION + " TEXT)";
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

    //Populate the home list with all current reminders saved
    public List<RowItem> getAllRemindersForList(){

        List<RowItem> reminderList = new ArrayList<>();git clone https://wymtowne@bitbucket.org/wymtowne/ahem.git
        SQLiteDatabase db = this.getReadableDatabase();

        RowItem rowItem;

        String queryString = "Select r.name, l.longitude, l.latitude, l.radius, a.street, a.street_number, a.city, a.state, a.zip, a.country  FROM " + REMINDER_TABLE + " AS r," + ADDRESS_TABLE + " AS a," + LOCATION_TABLE + " AS l WHERE " + "r." + LOCATION_ID + "=" + "l." + LOCATION_ID + " AND " +
                             "l." + ADDRESS_ID + "=" + "a." + ADDRESS_ID;

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){

            do{

                rowItem = new RowItem();

                String name = cursor.getString(0);
                double radius = cursor.getDouble(1);
                double longitude = cursor.getDouble(2);
                double latitude = cursor.getDouble(3);

                String street = cursor.getString(4);
                int street_number = cursor.getInt(5);
                String city = cursor.getString(6);
                String state = cursor.getString(7);
                String zip = cursor.getString(8);
                String country = cursor.getString(9);

                String fullAddress = street_number + " " + street + " " + city + ", " + state + " " + country + " " + zip;

                rowItem.setAddress(fullAddress);
                rowItem.setDistance(radius);
                rowItem.setLatitude(latitude);
                rowItem.setLongitude(longitude);
                rowItem.setName(name);

                reminderList.add(rowItem);

            }while(cursor.moveToNext());

        }else{
            //Result set is empty. Add nothing to the return list.
        }

        cursor.close();
        db.close();

        return reminderList;

    }
}
