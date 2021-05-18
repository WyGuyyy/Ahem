package com.me.ahem;

import android.app.Application;
import android.provider.Telephony;
import android.util.Log;

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

        dataMap = new MutableLiveData<TreeMap<String, String>>();
        reminder = new MutableLiveData<Reminder>();
        location = new MutableLiveData<Location>();
        address = new MutableLiveData<Address>();

        dataMap.setValue(new TreeMap<String, String>());
    }

    LiveData<List<Reminder>> getAllReminders() {return mAllReminders;}

    LiveData<List<RowItem>> getAllRowItems() {return mAllRowItems;}

    public void insert(Reminder reminder) {mRepository.insert(reminder);}

    public void insert(Reminder reminder, Location location, Address address){mRepository.insertNewRowItem(reminder, location, address);}

    public void setDataMap(TreeMap<String, String> newDataMap){

        int time = getTimeInSeconds(Integer.parseInt(newDataMap.get("hour")), Integer.parseInt(newDataMap.get("minute")), Integer.parseInt(newDataMap.get("second")));
        parseAddress(newDataMap);

        dataMap.setValue(newDataMap);
    }

    /*public void updateDataMap(String key, String value){
        TreeMap<String, String> tempMap = dataMap.getValue();
        tempMap.put(key, value);
        Log.d("before", key);
        dataMap.setValue(tempMap);
        Log.d("after", dataMap.getValue().keySet().toString());
    }*/

    /*public void submitDataMap(){
        Reminder newReminder = reminder.getValue();
        Location newLocation = location.getValue();
        Address newAddress = address.getValue();
        //TreeMap<String, String> currentDataMap = dataMap.getValue();
        TreeMap<String, String> currentDataMap = getDataMap().getValue();
        Log.d("test", currentDataMap.keySet().toString());

        int time = getTimeInSeconds(Integer.parseInt(currentDataMap.get("hour")), Integer.parseInt(currentDataMap.get("minute")), Integer.parseInt(currentDataMap.get("second")));
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
        TreeMap<String, String> addressMap = parseAddress(currentDataMap);

        newAddress.setStreet("Test");
        newAddress.setStreetNumber("Test");
        newAddress.setCity("Test");
        newAddress.setState("Test");
        newAddress.setCountry("Test");
        newAddress.setZip("Test");
        address.setValue(newAddress);
    }*/

    public int getTimeInSeconds(int hours, int minutes, int seconds){
        return (hours * 3600) + (minutes * 60) + seconds;
    }

    //start here next time, implement autocomplete for address and then parse here
    private TreeMap<String, String> parseAddress(TreeMap<String, String> currentDataMap){
        String address = currentDataMap.get("address");

        TreeMap<String, String> addressMap = new TreeMap<String, String>();

        return addressMap;

    }

    public MutableLiveData<TreeMap<String, String>> getDataMap(){
        return dataMap;
    }

}
