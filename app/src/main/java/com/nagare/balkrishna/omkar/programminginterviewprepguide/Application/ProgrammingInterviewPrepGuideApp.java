package com.nagare.balkrishna.omkar.programminginterviewprepguide.Application;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Activity.SettingsActivity;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.NightModeTimings;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.ReminderTiming;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils.Constants;

import java.util.Calendar;

/**
 * Created by OMKARNAGARE on 5/26/2017.
 */

public class ProgrammingInterviewPrepGuideApp extends Application {

    private static Context mContext = null;
    private static SharedPreferences mSharedPreferences = null;
    private static SharedPreferences mSettingPreferences = null;
    private static final String TAG = "ProgrammingInterviewApp";
    private static AlarmManager mAlarmManager = null;
    private static PendingIntent mBroadCastPendingIntent = null;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mSharedPreferences = mContext.getSharedPreferences(Constants.PROGRAMMING_INTERVIEW_PREP_GUIDE_APP_PREF, MODE_PRIVATE);
        mSettingPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mAlarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        mBroadCastPendingIntent = PendingIntent.getBroadcast(mContext,
                Constants.REQUEST_CODE, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static void setAppContext(Context mContext) {
        ProgrammingInterviewPrepGuideApp.mContext = mContext;
    }

    public static SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor() {
        return mSharedPreferences.edit();
    }

    public void setmSharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public static void setThemeBasedOnPreferences(Activity activity) {

        if (mSharedPreferences.contains(Constants.PREF_THEME_ID)) {
            int mThemeId = mSharedPreferences.getInt(Constants.PREF_THEME_ID, 0);
            activity.setTheme(mThemeId);
        } else {
            activity.setTheme(R.style.AppTheme_Default);
        }

    }

    public static int getAlertDialogueThemeBasedOnPreferences() {

        int mTimePickerThemeId;
        if (mSharedPreferences.contains(Constants.PREF_TIME_PICKER_THEME_ID)) {
            mTimePickerThemeId = mSharedPreferences.getInt(Constants.PREF_TIME_PICKER_THEME_ID, 0);
        } else {
            mTimePickerThemeId = R.style.AppTheme_Dialog_Alert_Default;
        }
        return mTimePickerThemeId;
    }

    public static void changeDayNightModeFromPreferences(Activity activity) {

        boolean isNightMode = ProgrammingInterviewPrepGuideApp.getBooleanSetting(SettingsActivity.KEY_ENABLE_NIGHT_MODE);
        boolean isNightTime = ProgrammingInterviewPrepGuideApp.isNightTime();
        View view = activity.getWindow().getDecorView();
        if (isNightMode || isNightTime) {
            view.setBackgroundColor(Color.GRAY);
        } else {
            view.setBackgroundColor(Color.WHITE);
        }

    }

    private static boolean isNightTime() {

        NightModeTimings nightModeTimings = ProgrammingInterviewPrepGuideApp.getNightModeTimings();
        // Get Current Time
        Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);

        if (nightModeTimings.isEnabled()) {

            int from = nightModeTimings.getStartHour() * 100 + nightModeTimings.getStartMinute();
            int to = nightModeTimings.getEndHour() * 100 + nightModeTimings.getEndMinute();

            int t = mHour * 100 + mMinute;

            return (to > from && t >= from && t <= to || to < from && (t >= from || t <= to));

        }
        return false;
    }

    public static String getStringSetting(String key) {
        return mSettingPreferences.getString(key, SettingsActivity.NO_SUCH_KEY_FOR_STRING);
    }

    public static int getIntSetting(String key) {
        return mSettingPreferences.getInt(key, SettingsActivity.NO_SUCH_KEY_FOR_INT);
    }

    public static boolean getBooleanSetting(String key) {
        return mSettingPreferences.getBoolean(key, SettingsActivity.NO_SUCH_KEY_FOR_BOOLEAN);
    }

    public static ReminderTiming getReminderTiming() {
        ReminderTiming reminderTiming = new ReminderTiming();

        reminderTiming.setHour(mSharedPreferences.getInt(Constants.PREF_REMINDER_START_HOUR, 20));
        reminderTiming.setMinute(mSharedPreferences.getInt(Constants.PREF_REMINDER_START_MINUTE, 0));
        reminderTiming.setEnabled(getBooleanSetting(SettingsActivity.KEY_ENABLE_REMINDER));

        return reminderTiming;
    }

    public static NightModeTimings getNightModeTimings() {
        NightModeTimings nightModeTimings = new NightModeTimings();

        nightModeTimings.setStartHour(mSharedPreferences.getInt(Constants.PREF_NIGHT_MODE_START_HOUR, 21));
        nightModeTimings.setStartMinute(mSharedPreferences.getInt(Constants.PREF_NIGHT_MODE_START_MINUTE, 0));
        nightModeTimings.setEndHour(mSharedPreferences.getInt(Constants.PREF_NIGHT_MODE_END_HOUR, 8));
        nightModeTimings.setEndMinute(mSharedPreferences.getInt(Constants.PREF_NIGHT_MODE_END_MINUTE, 0));
        nightModeTimings.setEnabled(getBooleanSetting(SettingsActivity.KEY_ENABLE_AUTO_NIGHT_MODE));

        return nightModeTimings;
    }

    public static void setReminderTimings(ReminderTiming reminderTiming) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putInt(Constants.PREF_REMINDER_START_HOUR, reminderTiming.getHour());
        editor.putInt(Constants.PREF_REMINDER_START_MINUTE, reminderTiming.getMinute());

        editor.commit();
    }

    public static void setNightModeTimings(NightModeTimings nightModeTimings) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putInt(Constants.PREF_NIGHT_MODE_START_HOUR, nightModeTimings.getStartHour());
        editor.putInt(Constants.PREF_NIGHT_MODE_START_MINUTE, nightModeTimings.getStartMinute());
        editor.putInt(Constants.PREF_NIGHT_MODE_END_HOUR, nightModeTimings.getEndHour());
        editor.putInt(Constants.PREF_NIGHT_MODE_END_MINUTE, nightModeTimings.getEndMinute());

        editor.commit();
    }


    public static void showToast(String message) {

        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

    }

    public static boolean isAutoStartRedirectDialogueShown(){
        return mSharedPreferences.getBoolean(Constants.PREF_AUTOSTART_SETTING_SET_BY_USER, false);
    }

    public static void setAutoStartRedirectDialogueShown(boolean isShown){
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putBoolean(Constants.PREF_AUTOSTART_SETTING_SET_BY_USER, isShown);

        editor.commit();
    }

    public static void setUpAlarmBasedOnPreference() {

        ReminderTiming reminderTiming = ProgrammingInterviewPrepGuideApp.getReminderTiming();

        if(reminderTiming.isEnabled()) {

            Calendar alarmStartTime = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            alarmStartTime.set(Calendar.HOUR_OF_DAY, reminderTiming.getHour());
            alarmStartTime.set(Calendar.MINUTE, reminderTiming.getMinute());
            alarmStartTime.set(Calendar.SECOND, 0);

            if (now.after(alarmStartTime)) {

                Log.d(TAG, "setUpAlarmBasedOnPreference: Adding a day");
                alarmStartTime.add(Calendar.DATE, 1);

            }

            mAlarmManager.cancel(mBroadCastPendingIntent);
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    alarmStartTime.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, mBroadCastPendingIntent);

        }else{

            Log.d(TAG, "setUpAlarmBasedOnPreference: reminder is disabled");

        }

    }

}
