package com.me.ahem;

public class ReminderModel {

    private int reminderID;
    private String name;
    private String reminderDescription;

    public ReminderModel() {
    }

    public ReminderModel(int reminderID, String name, String reminderDescription) {
        this.reminderID = reminderID;
        this.name = name;
        this.reminderDescription = reminderDescription;
    }

    @Override
    public String toString() {
        return "ReminderModel{" +
                "reminderID=" + reminderID +
                ", name='" + name + '\'' +
                ", reminderDescription='" + reminderDescription + '\'' +
                '}';
    }

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
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
