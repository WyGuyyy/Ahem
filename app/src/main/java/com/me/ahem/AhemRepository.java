package com.me.ahem;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.me.ahem.AhemRoomDatabase;
import com.me.ahem.address.Address;
import com.me.ahem.address.AddressDAO;
import com.me.ahem.location.Location;
import com.me.ahem.location.LocationDAO;
import com.me.ahem.reminder.Reminder;
import com.me.ahem.reminder.ReminderDAO;

import java.util.List;

public class AhemRepository {

    private ReminderDAO mReminderDAO;
    private LocationDAO mLocationDAO;
    private AddressDAO mAddressDAO;

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

    void insert(Reminder reminder){
        AhemRoomDatabase.databaseWriteExecutor.execute(() -> {
            mReminderDAO.insert(reminder);
        });
    }

    void insertNewRowItem(Reminder reminder, Location location, Address address){
        AhemRoomDatabase.databaseWriteExecutor.execute(() -> {
            mReminderDAO.insert(reminder);
            mLocationDAO.insert(location);
            mAddressDAO.insert(address);
        });
    }

}
