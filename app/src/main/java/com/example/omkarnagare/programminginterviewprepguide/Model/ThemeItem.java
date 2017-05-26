package com.example.omkarnagare.programminginterviewprepguide.Model;

/**
 * Created by OMKARNAGARE on 5/26/2017.
 */

public class ThemeItem {

    public ThemeItem() {
    }

    public ThemeItem(int color, int themeId) {
        this.color = color;
        this.themeId = themeId;
    }

    private int color;

    private int themeId;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }
}
