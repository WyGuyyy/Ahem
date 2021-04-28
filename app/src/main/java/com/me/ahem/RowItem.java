package com.me.ahem;

public class RowItem {

    String name;
    double distance;
    double latitude;
    double longitude;
    String address;

    public RowItem(){

    }

    public RowItem(String name, double distance, double latitude, double longitude, String address){
        this.name = name;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
