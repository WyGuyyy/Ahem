package com.me.ahem;

import androidx.room.ColumnInfo;

public class RowItem {

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "radius")
    double radius;

    @ColumnInfo(name = "latitude")
    double latitude;

    @ColumnInfo(name = "longitude")
    double longitude;

    @ColumnInfo(name = "street")
    String street;

    @ColumnInfo(name = "street_number")
    int street_number;

    @ColumnInfo(name = "city")
    String city;

    @ColumnInfo(name = "state")
    String state;

    @ColumnInfo(name = "zip")
    int zip;

    @ColumnInfo(name = "country")
    String country;

    public RowItem(){

    }

    public RowItem(String name, double radius, double latitude, double longitude, String street,
                   int streetNumber, String city, String state, int zip, String country){
        this.name = name;
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.street_number = streetNumber;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreet_number() {
        return street_number;
    }

    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress(){
        return street_number + " " + street + " " + city + " " + state + " " + country + " " + zip;
    }

}
