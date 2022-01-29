package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.recursiondynamicprogramming;

public class Canvas {
    private RgbColor[][] pixels;
    private int width;
    private int height;

    public Canvas(RgbColor[][] pixels) {
        if (pixels == null || pixels.length == 0) {
            this.pixels = pixels;
            this.width = 0;
            this.height = 0;
        } else {
            this.pixels = pixels;
            this.width = pixels.length;
            this.height = pixels[0].length;

        }
    }

    public Canvas(int width, int height, RgbColor defaultColor) {
        pixels = new RgbColor[width][height];
        this.width = width;
        this.height = height;

        for (int w=0; w<width; w++) {
            for (int h=0; h<height; h++) {
                pixels[w][h] = defaultColor;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public RgbColor getPixel(int height, int width) {
        if (height >= 0 && height < this.height && width >= 0 && width < this.width) {
            return pixels[width][height];
        }

        return null;
    }

    public void setPixel(int height, int width, RgbColor color) {
        if (height < this.height && width < this.width) {
            pixels[width][height] = color;
        }
    }
}
