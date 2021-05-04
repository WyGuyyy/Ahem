package com.me.ahem.reminder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel {

    private ReminderRepository mRepository;

    private final LiveData<List<Reminder>> mAllReminders;

    public ReminderViewModel(Application application){
        super(application);
        mRepository = new ReminderRepository(application);
        mAllReminders = mRepository.getAllReminders();
    }

    LiveData<List<Reminder>> getmAllReminders() {return mAllReminders;}

    public void insert(Reminder reminder) {mRepository.insert(reminder);}

}
