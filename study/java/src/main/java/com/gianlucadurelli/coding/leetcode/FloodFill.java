package com.gianlucadurelli.coding.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// https://leetcode.com/explore/interview/card/amazon/78/trees-and-graphs/2987/
public class FloodFill {
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image.length == 0 || image[0].length == 0
                || sr < 0 || sr >= image.length
                || sc < 0 || sc >= image[0].length
                || newColor < 0) {
            return image;
        }

        int oldColor = image[sr][sc];

        if (oldColor == newColor) {
            return image;
        }

        Queue<List<Integer>> queue = new LinkedList<List<Integer>>();

        image[sr][sc] = newColor;
        queue.add(List.of(sr, sc));

        while(!queue.isEmpty()) {
            List<Integer> pixel = queue.remove();
            int r = pixel.get(0);
            int c = pixel.get(1);

            if (getPixelValue(image, r + 1, c) == oldColor) {
                image[r + 1][c] = newColor;
                queue.add(List.of(r + 1, c));
            }

            if (getPixelValue(image, r - 1, c) == oldColor) {
                image[r - 1][c] = newColor;
                queue.add(List.of(r - 1, c));
            }

            if (getPixelValue(image, r, c + 1) == oldColor) {
                image[r][c + 1] = newColor;
                queue.add(List.of(r, c + 1));
            }

            if (getPixelValue(image, r, c - 1) == oldColor) {
                image[r][c - 1] = newColor;
                queue.add(List.of(r, c - 1));
            }
        }

        return image;

    }

    private int getPixelValue(int[][] image, int r, int c) {
        if (r < 0 || r >= image.length || c < 0 || c >= image[0].length) {
            return -1;
        }

        return image[r][c];
    }
}
