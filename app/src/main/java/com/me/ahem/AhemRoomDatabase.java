package com.me.ahem;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.me.ahem.address.Address;
import com.me.ahem.address.AddressDAO;
import com.me.ahem.location.Location;
import com.me.ahem.location.LocationDAO;
import com.me.ahem.reminder.Reminder;
import com.me.ahem.reminder.ReminderDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Reminder.class, Location.class, Address.class}, version = 1, exportSchema = false)
public abstract class AhemRoomDatabase extends RoomDatabase {

    public abstract ReminderDAO reminderDAO();
    public abstract LocationDAO locationDAO();
    public abstract AddressDAO addressDAO();

    //UNDERSTAND ALL OF THESE BEFORE MOVING ON
    private static volatile AhemRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AhemRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AhemRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AhemRoomDatabase.class, "ahem_database").build();
                }
            }
        }
        return INSTANCE;
    }

}
