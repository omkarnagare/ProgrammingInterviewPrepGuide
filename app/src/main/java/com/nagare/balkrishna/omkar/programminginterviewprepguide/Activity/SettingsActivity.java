package com.nagare.balkrishna.omkar.programminginterviewprepguide.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.RingtonePreference;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Application.ProgrammingInterviewPrepGuideApp;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.BuildConfig;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.NightModeTimings;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.ReminderTiming;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils.Constants;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.View.AboutMeDialog;

import java.util.Calendar;
import java.util.List;

public class SettingsActivity extends AppCompatPreferenceActivity {

    private static final String TAG = "SettingsActivity";
    public static final String KEY_ENABLE_NIGHT_MODE = "enable_night_mode";
    public static final String KEY_ENABLE_REMINDER = "enable_reminder";
    public static final String KEY_REMINDER_TIMINGS = "reminder_timings";
    public static final String KEY_ENABLE_AUTO_NIGHT_MODE = "enable_auto_night_mode";
    public static final String KEY_AUTO_NIGHT_MODE_TIMINGS = "auto_night_mode_timings";
    public static final String NO_SUCH_KEY_FOR_STRING = "no_key_present";
    public static final int NO_SUCH_KEY_FOR_INT = -1;
    public static final boolean NO_SUCH_KEY_FOR_BOOLEAN = false;
    public static final String KEY_FEEDBACK = "send_us_feedback";
    public static final String KEY_RATE_APP = "rate_app";
    public static final String KEY_ABOUT_ME = "about_me";
    public static final String KEY_ENABLE_SOUND = "enable_notifications_sound";
    public static final String KEY_SOUND = "notification_ringtone";
    public static final String KEY_ENABLE_VIBRATION = "enable_vibration";

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProgrammingInterviewPrepGuideApp.setThemeBasedOnPreferences(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgrammingInterviewPrepGuideApp.changeDayNightModeFromPreferences(this);
        setupActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        private static final String HEADER_PREFS_GENERAL = "prefs_general";
        private static final String HEADER_PREFS_NOTIFICATIONS = "prefs_notification";
        private static final String HEADER_PREFS_ABOUT_ME = "prefs_about_me";

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int preferenceFile = -1;
            String settings = getArguments().getString("settings");
            if (HEADER_PREFS_GENERAL.equalsIgnoreCase(settings)) {
                // Load the preferences from an XML resource
                preferenceFile = R.xml.pref_general;
            } else if (HEADER_PREFS_NOTIFICATIONS.equalsIgnoreCase(settings)) {
                // Load the preferences from an XML resource
                preferenceFile = R.xml.pref_notification;
            } else if (HEADER_PREFS_ABOUT_ME.equals(settings)) {
                // Load the preferences from an XML resource
                preferenceFile = R.xml.pref_about_me;
            }

            addPreferencesFromResource(preferenceFile);

            switch (preferenceFile) {
                case R.xml.pref_about_me:
                    setUpOnClickActionsForAboutMePreferences();
                    break;
                case R.xml.pref_general:
                    setUpOnClickActionsForGeneralPreferences();
                    break;
                default:
            }

            updateSummaryFromPreferences();


        }

        private void setUpOnClickActionsForGeneralPreferences() {

            final Preference nightModeTimingPreference = (Preference) findPreference(SettingsActivity.KEY_AUTO_NIGHT_MODE_TIMINGS);
            nightModeTimingPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    showTimePickerForNightModeTimings();

                    return true;
                }
            });


            final Preference reminderTimingPreference = (Preference) findPreference(SettingsActivity.KEY_REMINDER_TIMINGS);
            reminderTimingPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    showTimePickerForReminderTimings();

                    return true;
                }
            });

        }

        private void showTimePickerForReminderTimings() {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);


            // Launch Time Picker Dialog
            TimePickerDialog timePickerTillDialog = new TimePickerDialog(getActivity(),
                    ProgrammingInterviewPrepGuideApp.getAlertDialogueThemeBasedOnPreferences(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            ReminderTiming reminderTiming = new ReminderTiming();
                            reminderTiming.setHour(hourOfDay);
                            reminderTiming.setMinute(minute);
                            ProgrammingInterviewPrepGuideApp.setReminderTimings(reminderTiming);
                            updateSummaryFromPreferences();


                        }
                    }, mHour, mMinute, false);
            timePickerTillDialog.setCancelable(false);
            timePickerTillDialog.show();


        }

        private void showTimePickerForNightModeTimings() {

            // Get Current Time
            final Calendar calender = Calendar.getInstance();
            int mHour = calender.get(Calendar.HOUR_OF_DAY);
            int mMinute = calender.get(Calendar.MINUTE);

            final TextView titleFrom = new TextView(getActivity());
            // You Can Customise your Title here
            titleFrom.setText("Activate Night Mode From ...");
            titleFrom.setBackgroundColor(Color.WHITE);
            titleFrom.setTextColor(Color.BLACK);
            titleFrom.setPadding(10, 20, 10, 10);
            titleFrom.setGravity(Gravity.CENTER);
            titleFrom.setTextSize(20);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerFromDialog = new TimePickerDialog(getActivity(),
                    ProgrammingInterviewPrepGuideApp.getAlertDialogueThemeBasedOnPreferences(),
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, final int startHourOfDay,
                                              final int startMinute) {

                            final TextView titleTill = new TextView(getActivity());
                            // You Can Customise your Title here
                            titleTill.setText("Persist Night Mode Till ...");
                            titleTill.setBackgroundColor(Color.WHITE);
                            titleTill.setTextColor(Color.BLACK);
                            titleTill.setPadding(10, 20, 10, 10);
                            titleTill.setGravity(Gravity.CENTER);
                            titleTill.setTextSize(20);

                            // Launch Time Picker Dialog
                            TimePickerDialog timePickerTillDialog = new TimePickerDialog(getActivity(),
                                    ProgrammingInterviewPrepGuideApp.getAlertDialogueThemeBasedOnPreferences(),
                                    new TimePickerDialog.OnTimeSetListener() {

                                        @Override
                                        public void onTimeSet(TimePicker view, int endHourOfDay,
                                                              int endMinute) {

                                            if (startHourOfDay == endHourOfDay && startMinute == endMinute) {

                                                ProgrammingInterviewPrepGuideApp.showToast("Start date and End date can't be same");

                                            } else {
                                                NightModeTimings nightModeTimings = new NightModeTimings();
                                                nightModeTimings.setStartHour(startHourOfDay);
                                                nightModeTimings.setStartMinute(startMinute);
                                                nightModeTimings.setEndHour(endHourOfDay);
                                                nightModeTimings.setEndMinute(endMinute);

                                                ProgrammingInterviewPrepGuideApp.setNightModeTimings(nightModeTimings);
                                                updateSummaryFromPreferences();

                                            }

                                        }
                                    }, startHourOfDay, startMinute, false);
                            timePickerTillDialog.setCancelable(false);
                            timePickerTillDialog.setCustomTitle(titleTill);
                            timePickerTillDialog.show();


                        }
                    }, mHour, mMinute, false);
            timePickerFromDialog.setCancelable(false);
            timePickerFromDialog.setCustomTitle(titleFrom);
            timePickerFromDialog.show();

        }

        private void setUpOnClickActionsForAboutMePreferences() {

            Preference feedbackPreference = (Preference) findPreference(SettingsActivity.KEY_FEEDBACK);
            feedbackPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Programming Interview Prep Guide");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"omtechnologies.apps@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "\n\n----------------------------------\n Device OS: Android \n Device OS version: " +
                            Build.VERSION.RELEASE + "\n App Version: " + BuildConfig.VERSION_NAME+"-"+ BuildConfig.VERSION_CODE + "\n Device Brand: " + Build.BRAND +
                            "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER);
                    emailIntent.setType("message/rfc822");
                    startActivity(Intent.createChooser(emailIntent, "Send Feedback Email Via.."));
//                    startActivity(emailIntent);

                    return true;
                }
            });

            Preference rateAppPreference = (Preference) findPreference(SettingsActivity.KEY_RATE_APP);
            rateAppPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    launchGooglePlayPageForApp();
                    return true;
                }
            });

            Preference aboutMePreference = (Preference) findPreference(SettingsActivity.KEY_ABOUT_ME);
            aboutMePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    AboutMeDialog aboutMeDialog = new AboutMeDialog(getActivity());
                    aboutMeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    aboutMeDialog.show();
                    return true;
                }
            });
        }

        private void launchGooglePlayPageForApp() {
            Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getActivity(), " Unable to open Google Play page", Toast.LENGTH_LONG).show();
            }
        }

        private void updateSummaryFromPreferences() {

            for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
                Preference preference = getPreferenceScreen().getPreference(i);
                if (preference instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                    for (int j = 0; j < preferenceGroup.getPreferenceCount(); ++j) {
                        Preference singlePref = preferenceGroup.getPreference(j);
                        updatePreference(singlePref, singlePref.getKey());
                    }
                } else {
                    updatePreference(preference, preference.getKey());
                }
            }

        }

        @Override
        public void onResume() {
            super.onResume();

            // Set up a listener whenever a key changes
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);

        }

        @Override
        public void onPause() {
            // Unregister the listener whenever a key changes
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            updatePreference(findPreference(key), key);
        }

        private void updatePreference(Preference preference, String key) {
            if (preference == null) return;
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                listPreference.setSummary(listPreference.getEntry());
                return;
            } else if (preference instanceof CheckBoxPreference) {

                CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                switch (key) {

                    case KEY_ENABLE_NIGHT_MODE:

                        if (checkBoxPreference.isChecked()) {
                            checkBoxPreference.setSummary("Night Mode Activated");
                        } else {
                            checkBoxPreference.setSummary("Enable Night Mode");
                        }
                        ProgrammingInterviewPrepGuideApp.changeDayNightModeFromPreferences(getActivity());
                        break;

                    case KEY_ENABLE_AUTO_NIGHT_MODE:

                        if (checkBoxPreference.isChecked()) {
                            checkBoxPreference.setSummary("Auto Night Mode Activated");
                        } else {
                            checkBoxPreference.setSummary("Enable Auto Night Mode");
                        }
                        ProgrammingInterviewPrepGuideApp.changeDayNightModeFromPreferences(getActivity());
                        break;

                    case KEY_ENABLE_REMINDER:

                        if (checkBoxPreference.isChecked()) {
                            checkBoxPreference.setSummary("Reminder Activated");

                            if(!ProgrammingInterviewPrepGuideApp.isAutoStartRedirectDialogueShown()) {
                                redirectToSettingsPage();
                            }

                        } else {
                            checkBoxPreference.setSummary("Remind me everyday to read questions");
                            ProgrammingInterviewPrepGuideApp.setAutoStartRedirectDialogueShown(false);
                        }
                        ProgrammingInterviewPrepGuideApp.setUpAlarmBasedOnPreference();
                        break;
                    case KEY_ENABLE_SOUND:

                        if (checkBoxPreference.isChecked()) {
                            checkBoxPreference.setSummary("Sound Enabled for Notification");
                        } else {
                            checkBoxPreference.setSummary("Enable Sound for Notification");
                        }
                        break;

                    case KEY_ENABLE_VIBRATION:

                        if (checkBoxPreference.isChecked()) {
                            checkBoxPreference.setSummary("Vibration Activated for Notification");
                        } else {
                            checkBoxPreference.setSummary("Enable Vibration for Notification");
                        }
                        break;
                    default:

                }
                return;
            /*} else if (preference instanceof EditTextPreference) {

                EditTextPreference editTextPreference = (EditTextPreference) preference;
              */
            } else if(preference instanceof RingtonePreference){

                RingtonePreference ringtonePreference = (RingtonePreference)preference;

            } else {

                switch (key) {

                    case KEY_AUTO_NIGHT_MODE_TIMINGS:

                        NightModeTimings nightModeTimings = ProgrammingInterviewPrepGuideApp.getNightModeTimings();

                        preference.setSummary("From " + printTime(nightModeTimings.getStartHour(), nightModeTimings.getStartMinute())
                                + " To " + printTime(nightModeTimings.getEndHour(), nightModeTimings.getEndMinute()));
                        ProgrammingInterviewPrepGuideApp.changeDayNightModeFromPreferences(getActivity());
                        break;

                    case KEY_REMINDER_TIMINGS:

                        ReminderTiming reminderTiming = ProgrammingInterviewPrepGuideApp.getReminderTiming();

                        preference.setSummary("Remind me everyday at " + printTime(reminderTiming.getHour(), reminderTiming.getMinute()));
                        ProgrammingInterviewPrepGuideApp.setUpAlarmBasedOnPreference();
                        break;
                    default:

                }

            }
        }

        public String printTime(int hour, int minute){
            String AMPM = (hour / 12) > 0 ? "PM" : "AM";
            return String.format("%02d", hour%12)+":"+String.format("%02d", minute)+" "+AMPM;
        }

        private void redirectToSettingsPage() {

            if (android.os.Build.MANUFACTURER.equalsIgnoreCase(Constants.XIAOMI)) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getActivity(),
                            ProgrammingInterviewPrepGuideApp.getAlertDialogueThemeBasedOnPreferences());
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                }
                builder.setTitle("Change Notification Settings")
                        .setCancelable(false)
                        .setMessage("Please enable the Autostart setting for " +
                                "Programming Interview Prep Guide app.\n" +
                                "Note that notifications might not work properly if Autostart setting is disabled.\n"+
                                "Ignore if you have already enabled Autostart settings")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //this will open auto start screen where user can enable permission for your app
                                Intent intent = new Intent();
                                intent.setComponent(new ComponentName(Constants.AUTOSTART_SETTING_PACKAGE,
                                        Constants.AUTOSTART_SETTING_ACTIVITY));
                                startActivity(intent);
                                ProgrammingInterviewPrepGuideApp.setAutoStartRedirectDialogueShown(true);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ProgrammingInterviewPrepGuideApp.setAutoStartRedirectDialogueShown(true);
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }

        }


    }


}


