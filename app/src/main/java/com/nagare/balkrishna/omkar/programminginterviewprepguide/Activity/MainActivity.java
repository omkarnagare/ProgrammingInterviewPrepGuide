package com.nagare.balkrishna.omkar.programminginterviewprepguide.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Application.ProgrammingInterviewPrepGuideApp;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils.ColorPickerUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ProgrammingInterviewPrepGuideApp.setThemeBasedOnPreferences(this);

        setContentView(R.layout.activity_main);

        loadDefaultPreferenceValues();

        setUpUIComponents();

    }

    private void loadDefaultPreferenceValues() {

        PreferenceManager.setDefaultValues(this, R.xml.pref_about_me, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);

    }

    private void setUpUIComponents() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgrammingInterviewPrepGuideApp.changeDayNightModeFromPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_settings:
                startSettingsActivity();
                break;
            case R.id.action_themes:
                chooseThemeColor();
                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: No action defined");
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSettingsActivity() {

        Intent modifySettings = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(modifySettings);

    }

    private void chooseThemeColor() {

        // Get a GridView object from ColorPicker class
        final GridView gridView = (GridView) ColorPickerUtils.getColorPicker(MainActivity.this);

        TextView title = new TextView(this);
        // You Can Customise your Title here
        title.setText("Choose Theme Color");
        title.setBackgroundColor(Color.TRANSPARENT);
        title.setTextColor(Color.BLACK);
        title.setPadding(10, 20, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);

        // Initialize a new AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setView(gridView)
                .setCustomTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        // Initialize a new AlertDialog object
        final AlertDialog dialog = builder.create();
        // Show the color picker window
        dialog.show();

    }

}
