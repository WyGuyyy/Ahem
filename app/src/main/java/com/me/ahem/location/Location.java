package com.me.ahem.location;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LOCATION_TABLE")
public class Location {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "location_id")
    private long locationID;

    @ColumnInfo(name = "reminder_id")
    private long reminderID;

    @ColumnInfo(name = "address_id")
    private long addressID;

    @ColumnInfo(name = "longitude")
    private float longitude;

    @ColumnInfo(name = "latitude")
    private float latitude;

    @ColumnInfo(name = "radius")
    private float radius;

    @ColumnInfo(name = "time")
    private int time;

    public Location() {

    }

    public Location(@NonNull int locationID, int reminderID, int addressID, float longitude, float latitude, float radius, int time) {
        this.locationID = locationID;
        this.reminderID = reminderID;
        this.addressID = addressID;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationID=" + locationID +
                ", reminderID=" + reminderID +
                ", addressID=" + addressID +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", radius=" + radius +
                ", time=" + time +
                '}';
    }

    public long getLocationID() {
        return locationID;
    }

    public void setLocationID(long locationID) {
        this.locationID = locationID;
    }

    public long getReminderID() {
        return reminderID;
    }

    public void setReminderID(long reminderID) {
        this.reminderID = reminderID;
    }

    public long getAddressID() {
        return addressID;
    }

    public void setAddressID(long addressID) {
        this.addressID = addressID;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
