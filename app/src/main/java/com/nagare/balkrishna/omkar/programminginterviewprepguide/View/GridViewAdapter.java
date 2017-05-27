package com.nagare.balkrishna.omkar.programminginterviewprepguide.View;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nagare.balkrishna.omkar.programminginterviewprepguide.Model.ThemeItem;
import com.nagare.balkrishna.omkar.programminginterviewprepguide.Utils.Constants;

import java.util.List;

/**
 * Created by OMKARNAGARE on 5/26/2017.
 */

public class GridViewAdapter extends ArrayAdapter<ThemeItem> {

    Context context;
    int layoutResourceId;
    List<ThemeItem> themes = null;

    public GridViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ThemeItem> themes) {
        super(context, resource, themes);
        this.layoutResourceId = resource;
        this.context = context;
        this.themes = themes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Cast the current view as a TextView
        TextView view = (TextView) super.getView(position, convertView, parent);

        // Get the current color from list
        int currentColor = themes.get(position).getColor();

        view.setText("");
        // Set the background color of TextView as current color
        view.setBackgroundColor(currentColor);

        // Set the layout parameters for TextView widget
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT
        );
        view.setLayoutParams(lp);

        // Get the TextView LayoutParams
        AbsListView.LayoutParams params = (AbsListView.LayoutParams) view.getLayoutParams();

        // Set the TextView width and height in pixels
        // Should be same as GridView column width
        params.width = Constants.GRID_SIZE; // pixels
        params.height = Constants.GRID_SIZE; // pixels

        // Set the TextView layout parameters
        view.setLayoutParams(params);
        view.requestLayout();

        // Return the TextView as current view
        return view;
    }
}
