package com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Application.ProgrammingInterviewPrepGuideApp;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.ThemeItem;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.View.GridViewAdapter;

import java.util.ArrayList;

/**
 * Created by OMKARNAGARE on 5/26/2017.
 */

public class ColorPickerUtils {

    private static final String TAG = "ColorPickerUtils";
    private static GridViewAdapter gridViewAdapter;
    private static GridView gridView;
    private static ArrayList themes = getMaterialDesignThemeItems();;

    public static View getColorPicker(final Activity activity){
        // Initialize a new GridView widget
        gridView = new GridView(activity);

        // Specify the GridView layout parameters
        gridView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // Define column width and number of columns
        gridView.setNumColumns(GridView.AUTO_FIT);
        gridView.setColumnWidth(Constants.GRID_SIZE); // Should be same as TextView width and height

        // Define addition settings of GridView for design purpose
        gridView.setHorizontalSpacing(Constants.GRID_SPACING);
        gridView.setVerticalSpacing(Constants.GRID_SPACING);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setBackgroundColor(Color.TRANSPARENT);
        gridView.setPadding(Constants.GRID_LEFT_PADDING,
                Constants.GRID_TOP_PADDING,
                Constants.GRID_RIGHT_PADDING,
                Constants.GRID_BOTTOM_PADDING);
        gridView.setGravity(Gravity.CENTER);

        gridViewAdapter = new GridViewAdapter(activity, android.R.layout.simple_list_item_1, themes);

        // Specify the GridView data source
        gridView.setAdapter(gridViewAdapter);

        // Set an item click listener for GridView widget
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the pickedColor from AdapterView
                ThemeItem theme = (ThemeItem) parent.getItemAtPosition(position);

                SharedPreferences.Editor editor = ProgrammingInterviewPrepGuideApp.getSharedPreferencesEditor();
                editor.putInt(Constants.PREF_THEME_ID, theme.getThemeId());
                editor.commit();

                ProgrammingInterviewPrepGuideApp.setThemeBasedOnPreferences(activity);
                activity.recreate();

            }
        });

        // return the GridView
        return gridView;
    }

    private static ArrayList getMaterialDesignThemeItems() {

        ArrayList<ThemeItem> themes = new ArrayList<>();

        int numberOfThemes = Constants.COLORS.length;

        for (int i = 0; i < numberOfThemes; i++){
            ThemeItem themeItem = new ThemeItem(Constants.COLORS[i], Constants.THEMES[i]);
            themes.add(themeItem);
        }

        return themes;
    }
}
