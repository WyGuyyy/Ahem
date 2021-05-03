package com.me.ahem.location;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDAO {

        @Insert(onConflict = OnConflictStrategy.ABORT)
        void insert(Location location);

        @Query("DELETE FROM LOCATION_TABLE")
        void deleteAll();

        @Query("SELECT * FROM LOCATION_TABLE")
        LiveData<List<Location>> getAllLocations();

}
