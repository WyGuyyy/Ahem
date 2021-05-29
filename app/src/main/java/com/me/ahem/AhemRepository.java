package com.me.ahem;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.me.ahem.AhemRoomDatabase;
import com.me.ahem.address.Address;
import com.me.ahem.address.AddressDAO;
import com.me.ahem.address.AddressRepository;
import com.me.ahem.location.Location;
import com.me.ahem.location.LocationDAO;
import com.me.ahem.reminder.Reminder;
import com.me.ahem.reminder.ReminderDAO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AhemRepository {

    private ReminderDAO mReminderDAO;
    private LocationDAO mLocationDAO;
    private AddressDAO mAddressDAO;

    Reminder reminder;
    Location location;
    Address address;

    private LiveData<List<Reminder>> mAllReminders;
    private final LiveData<List<Location>> mAllLocations;
    private final LiveData<List<Address>> mAllAddresses;
    private LiveData<List<RowItem>> mAllRowItems;

    AhemRepository(Application application){
        AhemRoomDatabase db = AhemRoomDatabase.getDatabase(application);
        mReminderDAO = db.reminderDAO();
        mLocationDAO = db.locationDAO();
        mAddressDAO = db.addressDAO();
        mAllReminders = mReminderDAO.getAllReminders();
        mAllLocations = mLocationDAO.getAllLocations();
        mAllAddresses = mAddressDAO.getAllAddresses();
        mAllRowItems = mReminderDAO.getAllRowItems();
    }

    LiveData<List<Reminder>> getAllReminders(){
        return mAllReminders;
    }

    LiveData<List<Location>> getAllLocations(){
        return mAllLocations;
    }

    LiveData<List<Address>> getmAllAddresses(){
        return mAllAddresses;
    }

    LiveData<List<RowItem>> getAllRowItems(){
         return mAllRowItems;
    }

    long insertReminder(Reminder reminder){
        long id = 0;

        try {
            id = AhemRoomDatabase.databaseWriteExecutor.submit(() -> {
               return mReminderDAO.insert(reminder);
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return id;
    }

    long insertLocation(Location location) {
        long id = 0;

        try {
            id = AhemRoomDatabase.databaseWriteExecutor.submit(() -> {
               return mLocationDAO.insert(location);
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return id;
    }

    long insertAddress(Address address){
        long id = 0;

        try {
            id = AhemRoomDatabase.databaseWriteExecutor.submit(() -> {
                return mAddressDAO.insert(address);
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return id;
    }

    void insertNewRowItem(Reminder reminder, Location location, Address address){
        AhemRoomDatabase.databaseWriteExecutor.execute(() -> {
            mReminderDAO.insert(reminder);
            mLocationDAO.insert(location);
            mAddressDAO.insert(address);
        });
    }

    public Reminder getReminderFromDatabase(long reminderID){

        Reminder reminder = null;

        try {
            reminder = AhemRoomDatabase.databaseWriteExecutor.submit(() -> {
                return mReminderDAO.getReminderFromDatabase(reminderID);
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return reminder;
    }

    public Location getLocationFromDatabase(long locationID){
        Location location = null;

        try {
            location = AhemRoomDatabase.databaseWriteExecutor.submit(() -> {
                return mLocationDAO.getLocationFromDatabase(locationID);
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return location;
    }

    public Address getAddressFromDatabase(long addressID){

        Address address = null;

        try {
            address = AhemRoomDatabase.databaseWriteExecutor.submit(() -> {
                return mAddressDAO.getAddressFromDatabase(addressID);
            }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return address;
    }

}
