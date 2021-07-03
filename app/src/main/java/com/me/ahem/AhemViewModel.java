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

    private final LiveData<List<Location>> mAllLocations;

    private final LiveData<List<Address>> mAllAddresses;

    private final LiveData<List<RowItem>> mAllRowItems;

    private MutableLiveData<Reminder> reminder;

    private MutableLiveData<Location> location;

    private MutableLiveData<Address> address;

    private MutableLiveData<RowItem> rowItem;

    private MutableLiveData<String> mode;

    private MutableLiveData<TreeMap<String, String>> dataMap;

    public AhemViewModel(Application application){
        super(application);
        mRepository = new AhemRepository(application);
        mAllReminders = mRepository.getAllReminders();
        mAllLocations = mRepository.getAllLocations();
        mAllAddresses = mRepository.getmAllAddresses();
        mAllRowItems = mRepository.getAllRowItems();

        dataMap = new MutableLiveData<TreeMap<String, String>>();
        reminder = new MutableLiveData<Reminder>();
        location = new MutableLiveData<Location>();
        address = new MutableLiveData<Address>();
        rowItem = new MutableLiveData<RowItem>();

        mode = new MutableLiveData<String>();

        dataMap.setValue(new TreeMap<String, String>());
        reminder.setValue(new Reminder());
        location.setValue(new Location());
        address.setValue(new Address());
    }

    LiveData<List<Reminder>> getAllReminders() {

        List<Reminder> allReminders = mAllReminders.getValue();
        List<Location> allLocations = mAllLocations.getValue();
        List<Address> allAddresses = mAllAddresses.getValue();

        return mAllReminders;
    }

    LiveData<List<RowItem>> getAllRowItems() {return mAllRowItems;}

    public void insert(Reminder reminder) {mRepository.insertReminder(reminder);}

    public void insert(Reminder reminder, Location location, Address address){mRepository.insertNewRowItem(reminder, location, address);}

    public void setDataMap(TreeMap<String, String> newDataMap){

        //int time = getTimeInSeconds(Integer.parseInt(newDataMap.get("hour")), Integer.parseInt(newDataMap.get("minute")), Integer.parseInt(newDataMap.get("second")));
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

    public void submitDataMap(){ //Start here and submit file path!!!!
        Reminder newReminder = reminder.getValue();
        Location newLocation = location.getValue();
        Address newAddress = address.getValue();
        //TreeMap<String, String> currentDataMap = dataMap.getValue();
        TreeMap<String, String> currentDataMap = getDataMap().getValue();

        float longitude = Float.parseFloat(currentDataMap.get("longitude"));
        float latitude = Float.parseFloat(currentDataMap.get("latitude"));
        String strAddress = currentDataMap.get("address");
        String name = currentDataMap.get("name");
        String description = currentDataMap.get("description");
        String filepath = currentDataMap.get("filepath");

        String distanceType = currentDataMap.get("distance_type");
        String distanceUnit = currentDataMap.get("distance_unit");

        Float distanceAmount = -1.0F;

        if(!(currentDataMap.get("distance_amount") == null)){
            distanceAmount = Float.parseFloat(currentDataMap.get("distance_amount"));
        }

        int hour = -1;
        int minute = -1;
        int second = -1;
        int time = -1;

        if(!(currentDataMap.get("hour") == null)){
            hour = Integer.parseInt(currentDataMap.get("hour"));
            minute = Integer.parseInt(currentDataMap.get("minute"));
            second = Integer.parseInt(currentDataMap.get("second"));

            time = getTimeInSeconds(hour, minute, second);
        }

        String soundType = currentDataMap.get("sound_type");
        String customSound = currentDataMap.get("custom");
        String defaultSound = currentDataMap.get("default");
        String pingSound = currentDataMap.get("ping");

        String soundSelection = "";

        if(customSound.equals("ON")){
            soundSelection = "Custom";
        }else if(defaultSound.equals("ON")){
            soundSelection = "Default";
        }else if(pingSound.equals("ON")){
            soundSelection = "Ping";
        }else{
            soundSelection = "None";
        }

        parseAddress(currentDataMap);

        newReminder.setName(name);
        newReminder.setReminderDescription(description);
        newReminder.setSoundType(soundType);
        newReminder.setSoundSelection(soundSelection);
        newReminder.setSoundFilePath(filepath);
        reminder.setValue(newReminder);

        long reminderID = mRepository.insertReminder(reminder.getValue());

        TreeMap<String, String> addressMap = parseAddress(currentDataMap);

        newAddress.setStreet(strAddress);
        newAddress.setStreetNumber(strAddress);
        newAddress.setCity(strAddress);
        newAddress.setState(strAddress);
        newAddress.setCountry(strAddress);
        newAddress.setZip(strAddress);
        address.setValue(newAddress);

        long addressID = mRepository.insertAddress(address.getValue());

        newLocation.setLongitude(longitude);
        newLocation.setLatitude(latitude);
        newLocation.setDistanceType(distanceType);
        newLocation.setDistanceUnit(distanceUnit);
        newLocation.setRadius(distanceAmount);
        newLocation.setTime(time);
        newLocation.setReminderID(reminderID);
        newLocation.setAddressID(addressID);
        location.setValue(newLocation);

        long locationID = mRepository.insertLocation(location.getValue());

        if(getMode().equals("edit")){
            getLocationFromDatabase(locationID);
            getAddressFromDatabase(addressID);
            getReminderFromDatabase(reminderID);
        }

        //mRepository.insertNewRowItem(reminder.getValue(), location.getValue(), address.getValue());
    }

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

    public void setRowItem(RowItem newRowItem){
        rowItem.setValue(newRowItem);
    }

    public void setMode(String newMode){
        mode.setValue(newMode);
    }

    public RowItem getRowItem(){
        return rowItem.getValue();
    }

    public Reminder getReminder(){
        return reminder.getValue();
    }

    public Location getLocation(){
        return location.getValue();
    }

    public Address getAddress(){
        return address.getValue();
    }

    public void getReminderFromDatabase(long reminderID){
        reminder.setValue(mRepository.getReminderFromDatabase(reminderID));
    }

    public void getLocationFromDatabase(long locationID){
        location.setValue(mRepository.getLocationFromDatabase(locationID));
    }

    public void getAddressFromDatabase(long addressID){
        address.setValue(mRepository.getAddressFromDatabase(addressID));
    }

    public String getMode(){
        return mode.getValue();
    }

}
