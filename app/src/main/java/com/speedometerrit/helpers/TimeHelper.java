package com.speedometerrit.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeHelper {
    private Calendar currentTime;
    private String time;
    private int amPm;

    public static final int AM = Calendar.AM;
    public static final int PM = Calendar.PM;

    public TimeHelper() {
        this.currentTime = Calendar.getInstance();
        this.time = getTimeString();
        this.amPm = getAmPm();
    }

    public TimeHelper(Calendar currentTime) {
        this.currentTime = currentTime;
        this.time = getTimeString();
        this.amPm = getAmPm();
    }

    public void refreshCurrentTime() {
        this.currentTime = Calendar.getInstance();
        this.time = getTimeString();
        this.amPm = getAmPm();
    }

    public Calendar getCurrentTime() {
        return this.currentTime;
    }

    public String getTimeString() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        return timeFormat.format(this.currentTime.getTime());
    }

    public int getAmPm() {
        return this.currentTime.get(Calendar.AM_PM);
    }

}
