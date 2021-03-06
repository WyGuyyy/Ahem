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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Reminder reminder);

    @Query("DELETE FROM REMINDER_TABLE")
    void deleteAll();

    @Query("SELECT * FROM REMINDER_TABLE")
    LiveData<List<Reminder>> getAllReminders();

    @Query("SELECT r.name, r.reminder_id, l.radius, l.latitude, l.longitude, l.location_id, a.street, a.street_number, a.city, a.state, a.zip, a.country, a.address_id FROM REMINDER_TABLE as r, LOCATION_TABLE as l, ADDRESS_TABLE as a WHERE r.reminder_id = l.reminder_id AND l.address_id = a.address_id")
    LiveData<List<RowItem>> getAllRowItems();

    @Query("SELECT * FROM REMINDER_TABLE WHERE reminder_id = :reminderID")
    Reminder getReminderFromDatabase(long reminderID);
}
