package com.me.ahem.reminder;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.me.ahem.AhemRoomDatabase;

import java.util.List;

public class ReminderRepository {

    private ReminderDAO mReminderDAO;
    private LiveData<List<Reminder>> mAllReminders;

    ReminderRepository(Application application){
        AhemRoomDatabase db = AhemRoomDatabase.getDatabase(application);
        mReminderDAO = db.reminderDAO();
        mAllReminders = mReminderDAO.getAllReminders();
    }

    LiveData<List<Reminder>> getAllReminders(){
        return mAllReminders;
    }

    void insert(Reminder reminder){
        AhemRoomDatabase.databaseWriteExecutor.execute(() -> {
            mReminderDAO.insert(reminder);
        });
    }

}
