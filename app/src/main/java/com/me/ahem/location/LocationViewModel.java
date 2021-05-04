package com.me.ahem.location;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {

    private LocationRepository mRepository;

    private final LiveData<List<Location>> mAllLocations;

    public LocationViewModel(Application application){
        super(application);
        mRepository = new LocationRepository(application);
        mAllLocations = mRepository.getmAllLocations();
    }

    LiveData<List<Location>> getmAllLocations() {return mAllLocations;}

    public void insert(Location location) {mRepository.insert(location);}

}
