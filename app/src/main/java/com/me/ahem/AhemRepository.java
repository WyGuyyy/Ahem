package com.me.ahem;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.me.ahem.AhemRoomDatabase;
import com.me.ahem.reminder.Reminder;
import com.me.ahem.reminder.ReminderDAO;

import java.util.List;

public class AhemRepository {

    private ReminderDAO mReminderDAO;
    private LiveData<List<Reminder>> mAllReminders;
    private LiveData<List<RowItem>> mAllRowItems;

    AhemRepository(Application application){
        AhemRoomDatabase db = AhemRoomDatabase.getDatabase(application);
        mReminderDAO = db.reminderDAO();
        mAllReminders = mReminderDAO.getAllReminders();
        mAllRowItems = mReminderDAO.getAllRowItems();
    }

    LiveData<List<Reminder>> getAllReminders(){
        return mAllReminders;
    }

    LiveData<List<RowItem>> getAllRowItems(){
        return mAllRowItems;
    }

    void insert(Reminder reminder){
        AhemRoomDatabase.databaseWriteExecutor.execute(() -> {
            mReminderDAO.insert(reminder);
        });
    }

}