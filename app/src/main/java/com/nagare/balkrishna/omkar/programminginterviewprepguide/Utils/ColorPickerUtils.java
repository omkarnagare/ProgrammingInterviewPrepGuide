package com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.ThemeItem;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.R;

import java.util.ArrayList;

/**
 * Created by OMKARNAGARE on 5/26/2017.
 */

public class ColorPickerUtils {

    private Context mContext;
    // GridView column width and TextView width, height in pixels
    private static int gridSize = Constants.GRID_SIZE;

    public ColorPickerUtils(Context context){
        this.mContext = context;
    }

    public static View getColorPicker(Context mContext){
        // Initialize a new GridView widget
        GridView gv = new GridView(mContext);

        // Specify the GridView layout parameters
        gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        // Define column width and number of columns
        gv.setNumColumns(GridView.AUTO_FIT);
        gv.setColumnWidth(gridSize); // Should be same as TextView width and height

        // Define addition settings of GridView for design purpose
        gv.setHorizontalSpacing(Constants.GRID_SPACING);
        gv.setVerticalSpacing(Constants.GRID_SPACING);
        gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gv.setBackgroundColor(Color.TRANSPARENT);
        gv.setPadding(Constants.GRID_PADDING, Constants.GRID_PADDING, Constants.GRID_PADDING, Constants.GRID_PADDING);
        gv.setGravity(Gravity.CENTER);
        // Get the ArrayList of HSV colors
//        final ArrayList themes = HSVColors();
        final ArrayList themes = Themes();

        // Create an ArrayAdapter using colors list
        ArrayAdapter<Integer> ad = new ArrayAdapter<Integer>(mContext, android.R.layout.simple_list_item_1, themes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the current view as a TextView
                TextView view = (TextView) super.getView(position, convertView, parent);

                // Get the current color from list
                int currentColor = ((ThemeItem) themes.get(position)).getColor();

                // Set the background color of TextView as current color
                view.setBackgroundColor(currentColor);

                // Set empty text in TextView
                view.setText("");

                // Set the layout parameters for TextView widget
                LayoutParams lp = new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
                );
                view.setLayoutParams(lp);

                // Get the TextView LayoutParams
                LayoutParams params = (LayoutParams) view.getLayoutParams();

                // Set the TextView width and height in pixels
                // Should be same as GridView column width
                params.width = gridSize; // pixels
                params.height = gridSize; // pixels

                // Set the TextView layout parameters
                view.setLayoutParams(params);
                view.requestLayout();

                // Return the TextView as current view
                return view;
            }
        };

        // Specify the GridView data source
        gv.setAdapter(ad);

        // return the GridView
        return gv;
    }

    private static ArrayList Themes() {

        ArrayList<ThemeItem> themes = new ArrayList<>();

        ThemeItem yellowTheme = new ThemeItem(Color.YELLOW, R.style.Yellow);
        themes.add(yellowTheme);

        ThemeItem greenTheme = new ThemeItem(Color.GREEN, R.style.Green);
        themes.add(greenTheme);

        return themes;
    }

    // Custom method to generate hsv colors list
    public static ArrayList HSVColors(){
        ArrayList<Integer> colors= new ArrayList<>();

        // Loop through hue channel, saturation and light full
        for(int h=0;h<=360;h+=20){
            colors.add(HSVColor(h, 1, 1));
        }

        // Loop through hue channel, different saturation and light full
//        for(int h=0;h<=360;h+=20){
//            colors.add(HSVColor(h, .25f, 1));
//            colors.add(HSVColor(h, .5f, 1));
//            colors.add(HSVColor(h, .75f, 1));
//        }

        // Loop through hue channel, saturation full and light different
//        for(int h=0;h<=360;h+=20){
//            //colors.add(createColor(h, 1, .25f));
//            colors.add(HSVColor(h, 1, .5f));
//            colors.add(HSVColor(h, 1, .75f));
//        }

        // Loop through the light channel, no hue no saturation
        // It will generate gray colors
//        for(float b=0;b<=1;b+=.10f){
//            colors.add(HSVColor(0, 0, b));
//        }
        return colors;
    }

    // Create HSV color from values
    public static int HSVColor(float hue, float saturation, float black){
        /*
            Hue is the variation of color
            Hue range 0 to 360

            Saturation is the depth of color
            Range is 0.0 to 1.0 float value
            1.0 is 100% solid color

            Value/Black is the lightness of color
            Range is 0.0 to 1.0 float value
            1.0 is 100% bright less of a color that means black
        */
        int color = Color.HSVToColor(255,new float[]{hue,saturation,black});
        return color;
    }

}
