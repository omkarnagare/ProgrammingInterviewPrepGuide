package com.nagare.balkrishna.omkar.programminginterviewprepguide.Model;

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
                '}';
    }
}
