package com.nagare.balkrishna.omkar.programminginterviewprepguide.Model;

import android.net.Uri;

/**
 * Created by OMKARNAGARE on 6/4/2017.
 */

public class NotificationDetails {

    private boolean isVibrationOn;

    private boolean isRingtoneSet;

    private Uri sound;

    public boolean isVibrationOn() {
        return isVibrationOn;
    }

    public void setVibrationOn(boolean vibrationOn) {
        isVibrationOn = vibrationOn;
    }

    public boolean isRingtoneSet() {
        return isRingtoneSet;
    }

    public void setRingtoneSet(boolean ringtoneSet) {
        isRingtoneSet = ringtoneSet;
    }

    public Uri getSound() {
        return sound;
    }

    public void setSound(Uri sound) {
        this.sound = sound;
    }
}
