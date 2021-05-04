package com.me.ahem.location;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.me.ahem.AhemRoomDatabase;

import java.util.List;

public class LocationRepository {

    private LocationDAO mLocationDAO;
    private LiveData<List<Location>> mAllLocations;

    LocationRepository(Application application){
        AhemRoomDatabase db = AhemRoomDatabase.getDatabase(application);
        mLocationDAO = db.locationDAO();
        mAllLocations = mLocationDAO.getAllLocations();
    }

    LiveData<List<Location>> getmAllLocations(){
        return mAllLocations;
    }

    void insert(Location location){
        AhemRoomDatabase.databaseWriteExecutor.execute(() -> {
            mLocationDAO.insert(location);
        });
    }

}
