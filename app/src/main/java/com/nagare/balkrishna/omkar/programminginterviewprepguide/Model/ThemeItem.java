package com.nagare.balkrishna.omkar.programminginterviewprepguide.Model;

/**
 * Created by OMKARNAGARE on 5/26/2017.
 */

public class ThemeItem {

    public ThemeItem() {
    }

    public ThemeItem(int color, int themeId, int timePickerThemeId) {
        this.color = color;
        this.themeId = themeId;
        this.timePickerThemeId = timePickerThemeId;
    }

    private int color;

    private int themeId;

    private int timePickerThemeId;

    public int getTimePickerThemeId() {
        return timePickerThemeId;
    }

    public void setTimePickerThemeId(int timePickerThemeId) {
        this.timePickerThemeId = timePickerThemeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThemeItem themeItem = (ThemeItem) o;

        return themeId == themeItem.themeId;

    }

    @Override
    public int hashCode() {
        return themeId;
    }

    @Override
    public String toString() {
        return "ThemeItem{" +
                "color=" + color +
                ", themeId=" + themeId +
                ", timePickerThemeId=" + timePickerThemeId +
                '}';
    }
}
