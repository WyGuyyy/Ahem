package com.me.ahem.reminder;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "REMINDER_TABLE")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "reminder_id")
    private long reminderID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "reminderDescription")
    private String reminderDescription;

    public Reminder() {

    }

    public Reminder(@NonNull int reminderID, String name, String reminderDescription) {
        this.reminderID = reminderID;
        this.name = name;
        this.reminderDescription = reminderDescription;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "reminderID=" + reminderID +
                ", name='" + name + '\'' +
                ", reminderDescription='" + reminderDescription + '\'' +
                '}';
    }

    public long getReminderID() {
        return reminderID;
    }

    public void setReminderID(long reminderID) {
        this.reminderID = reminderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReminderDescription() {
        return reminderDescription;
    }

    public void setReminderDescription(String reminderDescription) {
        this.reminderDescription = reminderDescription;
    }
}
