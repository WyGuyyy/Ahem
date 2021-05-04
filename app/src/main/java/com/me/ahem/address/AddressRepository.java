package com.me.ahem.address;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.me.ahem.AhemRoomDatabase;

import java.util.List;

public class AddressRepository {

    private AddressDAO mAddressDAO;
    private LiveData<List<Address>> mAllAddresses;

    AddressRepository(Application application){
        AhemRoomDatabase db = AhemRoomDatabase.getDatabase(application);
        mAddressDAO = db.addressDAO();
        mAllAddresses = mAddressDAO.getAllAddresses();
    }

    LiveData<List<Address>> getmAllAddresses(){
        return mAllAddresses;
    }

    void insert(Address address){
        AhemRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAddressDAO.insert(address);
        });
    }

}
