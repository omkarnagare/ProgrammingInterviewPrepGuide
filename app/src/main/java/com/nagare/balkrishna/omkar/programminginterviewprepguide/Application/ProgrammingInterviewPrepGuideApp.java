package com.nagare.balkrishna.omkar.programminginterviewprepguide.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils.Constants;

/**
 * Created by OMKARNAGARE on 5/26/2017.
 */

public class ProgrammingInterviewPrepGuideApp extends Application {

    private static Context mContext = null;
    private static SharedPreferences mSharedPreferences = null;
    private static final String TAG = "ProgrammingInterviewPrepGuideApp";

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mSharedPreferences = mContext.getSharedPreferences(Constants.PROGRAMMING_INTERVIEW_PREP_GUIDE_APP_PREF, MODE_PRIVATE);

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

        SharedPreferences pref = ProgrammingInterviewPrepGuideApp.getSharedPreferences();
        if (pref.contains(Constants.PREF_THEME_ID)) {
            int mThemeId = pref.getInt(Constants.PREF_THEME_ID, 0);
            activity.setTheme(mThemeId);
        }else{
            activity.setTheme(R.style.AppTheme);
        }

    }

}
