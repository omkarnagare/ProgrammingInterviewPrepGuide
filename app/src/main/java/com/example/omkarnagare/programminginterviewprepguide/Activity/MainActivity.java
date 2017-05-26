package com.example.omkarnagare.programminginterviewprepguide.Activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.omkarnagare.programminginterviewprepguide.Application.ProgrammingInterviewPrepGuideApp;
import com.example.omkarnagare.programminginterviewprepguide.Model.ThemeItem;
import com.example.omkarnagare.programminginterviewprepguide.R;
import com.example.omkarnagare.programminginterviewprepguide.Utils.ColorPickerUtils;
import com.example.omkarnagare.programminginterviewprepguide.Utils.Constants;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar = null;
    private int mThemeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setThemeBasedOnPreferences();

        setContentView(R.layout.activity_main);

        setUpUIComponents();

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

    private void setThemeBasedOnPreferences() {

        SharedPreferences pref = ProgrammingInterviewPrepGuideApp.getSharedPreferences();
        if(pref.contains(Constants.PREF_THEME_ID)){
            mThemeId = pref.getInt(Constants.PREF_THEME_ID, 0);
            setTheme(mThemeId);
        }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            chooseThemeColor();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void chooseThemeColor() {

        // Get a GridView object from ColorPicker class
        GridView gv = (GridView) ColorPickerUtils.getColorPicker(MainActivity.this);

        // Initialize a new AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set the alert dialog content to GridView (color picker)
        builder.setView(gv);

        builder.setTitle("Choose Theme Color");

        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // Initialize a new AlertDialog object
        final AlertDialog dialog = builder.create();

        // Show the color picker window
        dialog.show();
        // Set an item click listener for GridView widget
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the pickedColor from AdapterView
                ThemeItem theme = (ThemeItem) parent.getItemAtPosition(position);
                mThemeId = theme.getThemeId();
                setTheme(mThemeId);

                SharedPreferences.Editor editor = ProgrammingInterviewPrepGuideApp.getSharedPreferencesEditor();
                editor.putInt(Constants.PREF_THEME_ID, mThemeId);
                editor.commit();

                // Set the layout background color as picked color
//                mToolbar.setBackgroundColor(mThemeId);
                MainActivity.this.recreate();

            }
        });

    }
}
