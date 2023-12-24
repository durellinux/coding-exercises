package com.gianlucadurelli.coding.libraries.geometry;

import com.gianlucadurelli.coding.libraries.math.DoublePrecision;

import java.util.Optional;

public class GeometryDoublePrecision implements Geometry<DoublePrecision> {

    @Override
    public Point<DoublePrecision> createPoint(long x, long y, long z) {
        return new Point<>(new DoublePrecision(x), new DoublePrecision(y), new DoublePrecision(z));
    }

    @Override
    public Point<DoublePrecision> createPoint(DoublePrecision x, DoublePrecision y, DoublePrecision z) {
        return new Point<>(x, y, z);
    }

    @Override
    public Line2DMQ<DoublePrecision> createLine(DoublePrecision m, Point<DoublePrecision> p) {
        DoublePrecision q = p.y().subtract(m.multiplyBy(p.x()));
        return new Line2DMQ<>(m, q);
    }

    @Override
    public Optional<Point<DoublePrecision>> intersect(Line2DMQ<DoublePrecision> line1, Line2DMQ<DoublePrecision> line2) {
        if (line1.m().equals(line2.m())) {
            return Optional.empty();
        }

        DoublePrecision dq = line2.q().subtract(line1.q());
        DoublePrecision dm = line1.m().subtract(line2.m());

        DoublePrecision x = dq.divideBy(dm);
        DoublePrecision y = line1.m().multiplyBy(x).add(line1.q());

        return Optional.of(new Point<>(x, y, DoublePrecision.ZERO));
    }

    @Override
    public Optional<Point<DoublePrecision>> intersect(PointWithVelocity<DoublePrecision> ray1, PointWithVelocity<DoublePrecision> ray2) {
        Optional<Point<DoublePrecision>> intersection = this.intersect(ray1.trajectory(), ray2.trajectory());
        if (intersection.isEmpty()) {
            return Optional.empty();
        }

        DoublePrecision thisTimeToPoint = timeToPoint(ray1, intersection.get());
        DoublePrecision otherTimeToPoint = timeToPoint(ray2, intersection.get());

        if (thisTimeToPoint.asDouble() < 0 || otherTimeToPoint.asDouble() < 0) {
            return Optional.empty();
        }

        return intersection;
    }

    private DoublePrecision timeToPoint(PointWithVelocity<DoublePrecision> ray, Point<DoublePrecision> dst) {
        DoublePrecision dx = dst.x().subtract(ray.p().x());
        return dx.divideBy(ray.v().x());
    }
}
