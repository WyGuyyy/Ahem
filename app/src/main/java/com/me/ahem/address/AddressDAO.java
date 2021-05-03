package com.me.ahem.address;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AddressDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Address address);

    @Query("DELETE FROM ADDRESS_TABLE")
    void deleteAll();

    @Query("SELECT * FROM ADDRESS_TABLE")
    LiveData<List<Address>> getAllAddresses();

}
