package com.gianlucadurelli.coding.libraries.geometry;

import com.gianlucadurelli.coding.libraries.math.Fraction;
import com.gianlucadurelli.coding.libraries.math.Operations;

import java.util.Optional;

public class GenericOperation<T extends Operations<T>> {
    public record Point<T extends Operations<T>>(T x, T y, T z) {
        public String debug() {
            return "x = " + x.asDouble() + ", y = " + y.asDouble() + ", z = " + z.asDouble();
        }
    }
    public record Line2DMQ<T extends Operations<T>>(T m, T q) {
        public static <T extends Operations<T>> Line2DMQ<T> from(T m, Point<T> p) {
            T q = p.y.subtract(m.multiplyBy(p.x));
            return new Line2DMQ<>(m, q);
        }

        public static <T extends Operations<T>> Line2DMQ<T> from(Point<T> p1, Point<T> p2) {
            T dx = p1.x.subtract(p2.x);
            T dy = p1.y.subtract(p2.y);
            T m = dy.divideBy(dx);
            return from(m, p1);
        }

        public Optional<Point<T>> intersect(Line2DMQ<T> other) {
            if (this.m.equals(other.m)) {
                return Optional.empty();
            }

            T dq = other.q.subtract(this.q);
            T dm = this.m.subtract(other.m);

            T x = dq.divideBy(dm);
            T y = this.m.multiplyBy(x).add(this.q);

            return Optional.of(new Point<>(x, y, x.getZero()));
        }
    }
    public record PointWithVelocity<T extends Operations<T>>(Point<T> p, Point<T> v, Line2DMQ<T> trajectory) {
        public Optional<Point<T>> intersect(PointWithVelocity<T> other) {
            Optional<Point<T>> intersection = this.trajectory.intersect(other.trajectory);
//
//            System.out.println("Hailstone 1: " + this.p.debug());
//            System.out.println("Hailstone 2: " + other.p.debug());
//

            if (intersection.isEmpty()) {
//                System.out.println("Intersection: No Intersection");
                return Optional.empty();
            }

            Optional<T> thisTimeToPoint = timeToPoint(intersection.get());
            Optional<T> otherTimeToPoint = other.timeToPoint(intersection.get());

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

        public Optional<T> timeToPoint(Point<T> dst) {
            T dx = dst.x.subtract(p.x);
            T tx = dx.divideBy(v.x);

            T dy = dst.y.subtract(p.y);
            T ty = dy.divideBy(v.y);

            if (tx.equals(ty)) {
                return Optional.of(tx);
            }

            return Optional.empty();
        }
    }
}
