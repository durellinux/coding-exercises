package com.gianlucadurelli.coding.libraries.geometry;

import com.gianlucadurelli.coding.libraries.math.DoublePrecision;
import com.gianlucadurelli.coding.libraries.math.Fraction;

import java.math.BigInteger;
import java.util.Optional;

public class GeometryFractionLong implements Geometry<Fraction> {
    @Override
    public Point<Fraction> createPoint(long x, long y, long z) {
        return new Point<>(
                new Fraction(BigInteger.valueOf(x), BigInteger.ONE),
                new Fraction(BigInteger.valueOf(y), BigInteger.ONE),
                new Fraction(BigInteger.valueOf(z), BigInteger.ONE)
        );
    }

    @Override
    public Point<Fraction> createPoint(Fraction x, Fraction y, Fraction z) {
        return new Point<>(x, y, z);
    }

    @Override
    public Line2DMQ<Fraction> createLine(Fraction m, Point<Fraction> p) {
        Fraction q = p.y().subtract(m.multiplyBy(p.x()));
        return new Line2DMQ<>(m, q);
    }

    @Override
    public Optional<Point<Fraction>> intersect(Line2DMQ<Fraction> line1, Line2DMQ<Fraction> line2) {
        if (line1.m().equals(line2.m())) {
            return Optional.empty();
        }

        Fraction dq = line2.q().subtract(line1.q());
        Fraction dm = line1.m().subtract(line2.m());

        Fraction x = dq.divideBy(dm);
        Fraction y = line1.m().multiplyBy(x).add(line1.q());

        return Optional.of(new Point<>(x, y, Fraction.ZERO));
    }

    @Override
    public Optional<Point<Fraction>> intersect(PointWithVelocity<Fraction> ray1, PointWithVelocity<Fraction> ray2) {
        Optional<Point<Fraction>> intersection = this.intersect(ray1.trajectory(), ray2.trajectory());
        if (intersection.isEmpty()) {
            return Optional.empty();
        }

        Fraction thisTimeToPoint = timeToPoint(ray1, intersection.get());
        Fraction otherTimeToPoint = timeToPoint(ray2, intersection.get());

        if (thisTimeToPoint.asDouble() < 0 || otherTimeToPoint.asDouble() < 0) {
            return Optional.empty();
        }

        return intersection;
    }

    private Fraction timeToPoint(PointWithVelocity<Fraction> ray, Point<Fraction> dst) {
        Fraction dx = dst.x().subtract(ray.p().x());
        return dx.divideBy(ray.v().x());
    }
}
