package com.me.ahem;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.me.ahem.reminder.Reminder;

import java.util.List;

public class AhemViewModel extends AndroidViewModel {

    private AhemRepository mRepository;

    private final LiveData<List<Reminder>> mAllReminders;

    private final LiveData<List<RowItem>> mAllRowItems;

    public AhemViewModel(Application application){
        super(application);
        mRepository = new AhemRepository(application);
        mAllReminders = mRepository.getAllReminders();
        mAllRowItems = mRepository.getAllRowItems();
    }

    LiveData<List<Reminder>> getAllReminders() {return mAllReminders;}

    LiveData<List<RowItem>> getAllRowItems() {return mAllRowItems;}

    public void insert(Reminder reminder) {mRepository.insert(reminder);}

}
