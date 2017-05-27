package com.nagare.balkrishna.omkar.programminginterviewprepguide.Activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Application.ProgrammingInterviewPrepGuideApp;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProgrammingInterviewPrepGuideApp.setThemeBasedOnPreferences(this);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();


    }
}


