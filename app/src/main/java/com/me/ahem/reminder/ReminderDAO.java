package com.me.ahem.reminder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.me.ahem.RowItem;

import java.util.List;

@Dao
public interface ReminderDAO
{

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Reminder reminder);

    @Query("DELETE FROM REMINDER_TABLE")
    void deleteAll();

    @Query("SELECT * FROM REMINDER_TABLE")
    LiveData<List<Reminder>> getAllReminders();

    @Query("SELECT r.name, l.radius, l.latitude, l.longitude, a.street, a.street_number, a.city, a.state, a.zip, a.country FROM REMINDER_TABLE as r, LOCATION_TABLE as l, ADDRESS_TABLE as a WHERE r.reminder_id = l.reminder_id AND l.address_id = a.address_id")
    LiveData<List<RowItem>> getAllRowItems();

}
