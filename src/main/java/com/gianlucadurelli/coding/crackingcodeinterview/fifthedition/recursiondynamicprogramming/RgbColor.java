package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.recursiondynamicprogramming;

import java.util.Objects;

public class RgbColor {

    public static RgbColor WHITE = new RgbColor(255, 255, 255);
    public static RgbColor BLACK = new RgbColor(0, 0, 0);
    public static RgbColor RED = new RgbColor(255, 0, 0);

    private int red;
    private int green;
    private int blue;

    public RgbColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RgbColor)) return false;
        RgbColor rgbColor = (RgbColor) o;
        return getRed() == rgbColor.getRed() &&
                getGreen() == rgbColor.getGreen() &&
                getBlue() == rgbColor.getBlue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRed(), getGreen(), getBlue());
    }
}
