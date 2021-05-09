package com.me.ahem;

import android.app.Application;
import android.provider.Telephony;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.me.ahem.address.Address;
import com.me.ahem.location.Location;
import com.me.ahem.reminder.Reminder;

import java.nio.DoubleBuffer;
import java.util.List;
import java.util.TreeMap;

public class AhemViewModel extends AndroidViewModel {

    private AhemRepository mRepository;

    private final LiveData<List<Reminder>> mAllReminders;

    private final LiveData<List<RowItem>> mAllRowItems;

    private MutableLiveData<Reminder> reminder;

    private MutableLiveData<Location> location;

    private MutableLiveData<Address> address;

    private MutableLiveData<TreeMap<String, String>> dataMap;

    public AhemViewModel(Application application){
        super(application);
        mRepository = new AhemRepository(application);
        mAllReminders = mRepository.getAllReminders();
        mAllRowItems = mRepository.getAllRowItems();
        dataMap.setValue(new TreeMap<String, String>());
    }

    LiveData<List<Reminder>> getAllReminders() {return mAllReminders;}

    LiveData<List<RowItem>> getAllRowItems() {return mAllRowItems;}

    public void insert(Reminder reminder) {mRepository.insert(reminder);}

    public void updateDataMap(String key, String value){
        TreeMap<String, String> tempMap = dataMap.getValue();
        tempMap.put(key, value);
        dataMap.setValue(tempMap);
    }

    public void submitDataMap(){
        Reminder newReminder = reminder.getValue();
        Location newLocation = location.getValue();
        Address newAddress = address.getValue();
        TreeMap<String, String> currentDataMap = dataMap.getValue();

        int time = getTimeInSeconds(Integer.parseInt(currentDataMap.get("hours")), Integer.parseInt(currentDataMap.get("minutes")), Integer.parseInt(currentDataMap.get("seconds")));
        parseAddress(currentDataMap);

        newReminder.setName(currentDataMap.get("name"));
        newReminder.setReminderDescription(currentDataMap.get("description"));
        reminder.setValue(newReminder);

        newLocation.setLongitude(Float.parseFloat(currentDataMap.get("longitude")));
        newLocation.setLatitude(Float.parseFloat(currentDataMap.get("Latitude")));
        newLocation.setRadius(Float.parseFloat(currentDataMap.get("radius")));
        newLocation.setTime(time);
        location.setValue(newLocation);

        //Need to figure out how to parse address here to store
        parseAddress(currentDataMap, newAddress);
        address.setValue(newAddress);

    }

    public int getTimeInSeconds(int hours, int minutes, int seconds){
        return (hours * 3600) + (minutes * 60) + seconds;
    }

    private void parseAddress(TreeMap<String, String> currentDataMap, Address newAddress){
        String address = currentDataMap.get("address");
        String street = "";
        String streetNumber = "";
        String city = "";
        String state = "";
        String country = "";
        String zip = "";

        newAddress.setStreet();
        newAddress.setStreetNumber();
        newAddress.setCity();
        newAddress.setState();
        newAddress.setCountry();
        newAddress.setZip();

    }

}
