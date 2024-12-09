package com.gianlucadurelli.coding.hackerrank.certifications;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaxArea {

    public static class Rectangle {
        int hl;
        int hh;
        int wl;
        int wh;

        public Rectangle(int hl, int hh, int wl, int wh) {
            this.hl = hl;
            this.hh = hh;
            this.wl = wl;
            this.wh = wh;
        }

        public Rectangle cutVertical(int v) {
            if (v <= wl || v >= wh) {
                return this;
            }

            if (v - wl >= wh - v) {
                return new Rectangle(hl, hh, wl, v);
            }

            return new Rectangle(hl, hh, v, wh);
        }

        public Rectangle cutHorizontal(int v) {
            if (v <= hl || v >= hh) {
                return this;
            }

            if (v - hl >= hh - v) {
                return new Rectangle(hl, v, wl, wh);
            }

            return new Rectangle(v, hh, wl, wh);
        }

        public long area() {
            return (long) (hh - hl) * (wh - wl);
        }
    }
    public static List<Long> getMaxArea(int w, int h, List<Boolean> isVertical, List<Integer> distance) {
        Rectangle r = new Rectangle(0, h, 0, w);
        List<Long> areas = new ArrayList<>();

        for (int i = 0; i < distance.size(); i++) {
            if (isVertical.get(i)) {
                r = r.cutVertical(distance.get(i));
            } else {
                r = r.cutHorizontal(distance.get(i));
            }

            areas.add(r.area());
        }

        return areas;
    }
}
