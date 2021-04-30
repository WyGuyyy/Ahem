package com.me.ahem;

import android.provider.Telephony;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AhemViewModel extends ViewModel {

    private MutableLiveData<List<RowItem>> listItems;

    private MutableLiveData<ReminderModel> reminder;
    private MutableLiveData<LocationModel> location;
    private MutableLiveData<AddressModel> address;


    public LiveData<List<RowItem>> getListItems(){

        return listItems;
    }

    public void addListItem(RowItem rowItem){

    }

    public void removeListItem(RowItem rowItem){

    }

    public void editListItem(RowItem rowItem){

    }

    public LiveData<ReminderModel> getReminder(){

        return reminder;
    }

    public void setReminder(ReminderModel reminder){

    }

    public LiveData<LocationModel> getLocation(){

        return location;
    }

    public void setLocation(LocationModel location){

    }

    public LiveData<AddressModel> getAddress(){

        return address;
    }

    public void setAddress(AddressModel address){

    }

    private void loadReminders(){

    }

}
