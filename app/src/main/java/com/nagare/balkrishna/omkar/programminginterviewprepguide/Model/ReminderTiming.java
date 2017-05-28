package com.nagare.balkrishna.omkar.programminginterviewprepguide.Model;

/**
 * Created by OMKARNAGARE on 5/28/2017.
 */

public class ReminderTiming {

    private int hour;
    private int minute;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    private boolean isEnabled;

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {

        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
