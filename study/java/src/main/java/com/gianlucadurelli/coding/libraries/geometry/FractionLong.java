package com.gianlucadurelli.coding.libraries.geometry;

import com.gianlucadurelli.coding.libraries.math.Fraction;
import java.util.Optional;

public class FractionLong {
    public record Point(Fraction x, Fraction y, Fraction z) {
        public static Point fromLong(long x, long y, long z) {
            return new Point(
                    new Fraction(x, 1),
                    new Fraction(y, 1),
                    new Fraction(z, 1)
            );
        }
        public String debug() {
            return "x = " + x.asDouble() + ", y = " + y.asDouble() + ", z = " + z.asDouble();
        }
    }
    public record Line2DMQ(Fraction m, Fraction q) {
        public static Line2DMQ from(Fraction m, Point p) {
            Fraction q = p.y.subtract(m.multiplyBy(p.x));
            return new Line2DMQ(m, q);
        }

        public static Line2DMQ from(Point p1, Point p2) {
            Fraction dx = p1.x.subtract(p2.x);
            Fraction dy = p1.y.subtract(p2.y);
            Fraction m = dy.divideBy(dx);
            return from(m, p1);
        }

        public Optional<Point> intersect(Line2DMQ other) {
            if (this.m.equals(other.m)) {
                return Optional.empty();
            }

            Fraction dq = other.q.subtract(this.q);
            Fraction dm = this.m.subtract(other.m);

            Fraction x = dq.divideBy(dm);
            Fraction y = this.m.multiplyBy(x).add(this.q);

            return Optional.of(new Point(x, y, Fraction.ZERO));
        }
    }
    public record PointWithVelocity(Point p, Point v, Line2DMQ trajectory) {
        public Optional<Point> intersect(PointWithVelocity other) {
            Optional<Point> intersection = this.trajectory.intersect(other.trajectory);
//
//            System.out.println("Hailstone 1: " + this.p.debug());
//            System.out.println("Hailstone 2: " + other.p.debug());
//

            if (intersection.isEmpty()) {
//                System.out.println("Intersection: No Intersection");
                return Optional.empty();
            }

            Optional<Fraction> thisTimeToPoint = timeToPoint(intersection.get());
            Optional<Fraction> otherTimeToPoint = other.timeToPoint(intersection.get());

            if (thisTimeToPoint.isEmpty() || otherTimeToPoint.isEmpty()) {
//                System.out.println("Intersection in the past (really???): " + intersection.map(i -> i.x.asDouble() + ", " + i.y.asDouble()).orElseThrow());
                return Optional.empty();
            }

            if (thisTimeToPoint.get().asDouble() < 0 || otherTimeToPoint.get().asDouble() < 0) {
//                System.out.println("Intersection in the past: " + intersection.map(i -> i.x.asDouble() + ", " + i.y.asDouble()).orElseThrow());
                return Optional.empty();
            }

//            System.out.println("Intersection : " + intersection.map(i -> i.x.asDouble() + ", " + i.y.asDouble()).orElseThrow());
            return intersection;
        }

        public Optional<Fraction> timeToPoint(Point dst) {
            Fraction dx = dst.x.subtract(p.x);
            Fraction tx = dx.divideBy(v.x);

            Fraction dy = dst.y.subtract(p.y);
            Fraction ty = dy.divideBy(v.y);

            if (tx.equals(ty)) {
                return Optional.of(tx);
            }

            return Optional.empty();
        }
    }
}
