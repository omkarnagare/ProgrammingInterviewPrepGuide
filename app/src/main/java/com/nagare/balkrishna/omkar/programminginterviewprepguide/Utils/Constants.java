package com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils;

import android.graphics.Color;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;

/**
 * Created by OMKARNAGARE on 5/26/2017.
 */

public class Constants {

    public static final String PROGRAMMING_INTERVIEW_PREP_GUIDE_APP_PREF = "PROGRAMMING_INTERVIEW_PREP_GUIDE_APP_PREF";
    public static final String PREF_THEME_ID = "ThemeId";
    public static final String PREF_TIME_PICKER_THEME_ID = "TimePickerThemeId";
    public static final String PREF_NIGHT_MODE_START_HOUR = "NightModeStartHour";
    public static final String PREF_NIGHT_MODE_START_MINUTE = "NightModeStartMinute";
    public static final String PREF_NIGHT_MODE_END_HOUR = "NightModeEndHour";
    public static final String PREF_NIGHT_MODE_END_MINUTE = "NightModeEndMinute";
    public static final String PREF_REMINDER_START_HOUR = "ReminderStartHour";
    public static final String PREF_REMINDER_START_MINUTE = "ReminderStartMinute";
    public static final String PREF_AUTOSTART_SETTING_SET_BY_USER = "AutoStartSettings";

    public static final int GRID_SPACING = 20;
    public static final int GRID_LEFT_PADDING = 50;
    public static final int GRID_BOTTOM_PADDING = 10;
    public static final int GRID_TOP_PADDING = 30;
    public static final int GRID_RIGHT_PADDING = 50;
    public static final int REQUEST_CODE = 100;
    public static final int NOTIFICATION_ID = 0;
    public static final int REQUEST_CODE_NOTIFICATION = 0;
    public static final String XIAOMI = "Xiaomi";
    public static final String AUTOSTART_SETTING_PACKAGE = "com.miui.securitycenter";
    public static final String AUTOSTART_SETTING_ACTIVITY = "com.miui.permcenter.autostart.AutoStartManagementActivity";
    public static final String TEST_DEVICE_ID = "BAEFA564A6DF839A4CFA254BBC93ACAC";
    public static final int FULL_SCREEN_AD_INTERVAL = 5 * 60 * 1000;
    public static int GRID_SIZE = 100;

    public static int[] COLORS = {Color.parseColor("#F44336"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#FFA500"),
            Color.parseColor("#CDDC39"),
            Color.parseColor("#009688"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#9E9E9E"),
            Color.parseColor("#424242")
    };

    public static int[] THEMES = {R.style.AppTheme_Red,
            R.style.AppTheme_Green,
            R.style.AppTheme_Orange,
            R.style.AppTheme_Lime,
            R.style.AppTheme_Teal,
            R.style.AppTheme_Blue,
            R.style.AppTheme_Purple,
            R.style.AppTheme_Gray,
            R.style.AppTheme_Default
    };

    public static int[] TIME_PICKER_THEMES = {R.style.AppTheme_Dialog_Alert_Red,
            R.style.AppTheme_Dialog_Alert_Green,
            R.style.AppTheme_Dialog_Alert_Orange,
            R.style.AppTheme_Dialog_Alert_Lime,
            R.style.AppTheme_Dialog_Alert_Teal,
            R.style.AppTheme_Dialog_Alert_Blue,
            R.style.AppTheme_Dialog_Alert_Purple,
            R.style.AppTheme_Dialog_Alert_Gray,
            R.style.AppTheme_Dialog_Alert_Default
    };
}
