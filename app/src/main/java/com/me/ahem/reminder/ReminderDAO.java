package com.me.ahem.reminder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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

}
