package com.nagare.balkrishna.omkar.programminginterviewprepguide.Activity;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Application.ProgrammingInterviewPrepGuideApp;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.NightModeTimings;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.ReminderTiming;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;

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
                    ProgrammingInterviewPrepGuideApp.getTimePickerThemeBasedOnPreferences(),
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
                    ProgrammingInterviewPrepGuideApp.getTimePickerThemeBasedOnPreferences(),
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
                                    ProgrammingInterviewPrepGuideApp.getTimePickerThemeBasedOnPreferences(),
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
                    emailIntent.setType("message/rfc822");
                    startActivity(Intent.createChooser(emailIntent, "Send Feedback Via.."));

                    return true;
                }
            });

            Preference rateAppPreference = (Preference) findPreference(SettingsActivity.KEY_RATE_APP);
            rateAppPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    return true;
                }
            });

            Preference aboutMePreference = (Preference) findPreference(SettingsActivity.KEY_ABOUT_ME);
            aboutMePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    return true;
                }
            });
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
                        } else {
                            checkBoxPreference.setSummary("Remind me everyday to read questions");
                        }

                        break;
                    default:

                }
                return;
            } else if (preference instanceof EditTextPreference) {
                EditTextPreference editTextPreference = (EditTextPreference) preference;
                preference.setSummary(editTextPreference.getText());
            } else {

                switch (key) {

                    case KEY_AUTO_NIGHT_MODE_TIMINGS:

                        NightModeTimings nightModeTimings = ProgrammingInterviewPrepGuideApp.getNightModeTimings();

                        int startHour = nightModeTimings.getStartHour() % 12;
                        int startMinute = nightModeTimings.getStartMinute();
                        String startAMPM = (nightModeTimings.getStartHour() / 12) > 0 ? "PM" : "AM";

                        int endHour = nightModeTimings.getEndHour() % 12;
                        int endMinute = nightModeTimings.getEndMinute();
                        String endAMPM = (nightModeTimings.getEndHour() / 12) > 0 ? "PM" : "AM";

                        preference.setSummary("From " + startHour + ":" + startMinute + " " + startAMPM
                                + " To " + endHour + ":" + endMinute + " " + endAMPM);
                        ProgrammingInterviewPrepGuideApp.changeDayNightModeFromPreferences(getActivity());
                        break;

                    case KEY_REMINDER_TIMINGS:

                        ReminderTiming reminderTiming = ProgrammingInterviewPrepGuideApp.getReminderTiming();

                        int hour = reminderTiming.getHour() % 12;
                        int minute = reminderTiming.getMinute();
                        String AMPM = (reminderTiming.getHour() / 12) > 0 ? "PM" : "AM";

                        preference.setSummary("Remind me everyday at " + hour + ":" + minute + " " + AMPM);

                        break;
                    default:

                }

            }
        }


    }


}


