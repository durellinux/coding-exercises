package com.gianlucadurelli.coding.libraries.geometry;

import java.util.Optional;

public class TrueDouble {
    public record Point(double x, double y, double z) {
        public static Point fromLong(long x, long y, long z) {
            return new Point(x, y, z);
        }
    }
    public record Line2DMQ(double m, double q) {
        public static Line2DMQ from(double m, Point p) {
            double q = p.y - m * p.x;
            return new Line2DMQ(m, q);
        }

        public static Line2DMQ from(Point p1, Point p2) {
            double dx = p1.x - p2.x;
            double dy = p1.y - p2.y;
            double m = dy/dx;
            return from(m, p1);
        }

        public Optional<Point> intersect(Line2DMQ other) {
            if (this.m == other.m) {
                return Optional.empty();
            }

            double dq = other.q - this.q;
            double dm = this.m - other.m;

            double x = dq / dm;
            double y = this.m * x + this.q;

            return Optional.of(new Point(x, y, 0));
        }
    }
    public record PointWithVelocity(Point p, Point v, Line2DMQ trajectory) {
        public Optional<Point> intersect(PointWithVelocity other) {
            Optional<Point> intersection = this.trajectory.intersect(other.trajectory);

            System.out.println("Hailstone 1: " + this.p);
            System.out.println("Hailstone 2: " + other.p);


            if (intersection.isEmpty()) {
                System.out.println("Intersection: No Intersection");
                return Optional.empty();
            }

            Double thisTimeToPoint = timeToPoint(intersection.get());
            Double otherTimeToPoint = other.timeToPoint(intersection.get());


            if (thisTimeToPoint < 0 || otherTimeToPoint < 0) {
                System.out.println("Intersection in the past: " + intersection.map(i -> i.x + ", " + i.y).orElseThrow());
                return Optional.empty();
            }

            System.out.println("Intersection : " + intersection.map(i -> i.x + ", " + i.y).orElseThrow());
            return intersection;
        }

        private Double timeToPoint(Point dst) {
            double dx = dst.x - p.x;
            double tx = dx/v.x;

            return tx;
        }
    }
}
