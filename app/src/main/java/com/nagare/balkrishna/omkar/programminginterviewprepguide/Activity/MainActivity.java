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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Application.ProgrammingInterviewPrepGuideApp;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils.ColorPickerUtils;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils.Constants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar = null;

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private ScheduledFuture showFullScreenAdsTimer = null;
    private InterstitialAd mInterstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ProgrammingInterviewPrepGuideApp.setThemeBasedOnPreferences(this);

        setContentView(R.layout.activity_main);

        ProgrammingInterviewPrepGuideApp.setUpAlarmBasedOnPreference();

        loadDefaultPreferenceValues();

        setUpUIComponents();

    }

    private void loadDefaultPreferenceValues() {

        PreferenceManager.setDefaultValues(this, R.xml.pref_about_me, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false);

    }

    private void setUpUIComponents() {

        setUpBannerAd();

        setUpFullScreenAd();

        startFullScreenAds();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

    }

    private void setUpFullScreenAd() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8786806562583765/7156673139");

        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
            }
        });

    }

    private void startFullScreenAds() {

        if(showFullScreenAdsTimer != null){
            showFullScreenAdsTimer.cancel(true);
            showFullScreenAdsTimer = null;
        }

        showFullScreenAdsTimer = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AdRequest adRequest = new AdRequest.Builder()
                                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                                .addTestDevice(Constants.TEST_DEVICE_ID)
                                .build();

                        if(mInterstitialAd != null) {
                            mInterstitialAd.loadAd(adRequest);
                        }
                    }
                });

            }
        },5 * 1000, Constants.FULL_SCREEN_AD_INTERVAL , TimeUnit.MILLISECONDS);

    }

    @Override
    protected void onDestroy() {

        if(showFullScreenAdsTimer != null){
            showFullScreenAdsTimer.cancel(true);
            showFullScreenAdsTimer = null;
        }

        mInterstitialAd = null;

        super.onDestroy();
    }

    private void setUpBannerAd() {

        AdView mAdView = (AdView) findViewById(R.id.mobile_add_main_activity);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(Constants.TEST_DEVICE_ID)
                .build();
        mAdView.loadAd(adRequest);


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
            case R.id.action_remove_ads:
                removeAdsByInAppPurchase();
                break;
            default:
                Log.d(TAG, "onOptionsItemSelected: No action defined");
        }

        return super.onOptionsItemSelected(item);
    }

    private void removeAdsByInAppPurchase() {

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
