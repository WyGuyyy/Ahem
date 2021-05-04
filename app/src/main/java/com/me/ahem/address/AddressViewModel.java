package com.me.ahem.address;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {

    private AddressRepository mRepository;

    private final LiveData<List<Address>> mAllAddresses;

    public AddressViewModel(Application application){
        super(application);
        mRepository = new AddressRepository(application);
        mAllAddresses = mRepository.getmAllAddresses();
    }

    LiveData<List<Address>> getmAllAddresses() {return mAllAddresses;}

    public void insert(Address address) {mRepository.insert(address);}

}
