package com.gianlucadurelli.coding.libraries.geometry;

import java.util.Optional;

public class GeometryDouble implements Geometry<Double>{
    @Override
    public Point<Double> createPoint(long x, long y, long z) {
        return new Point<>(x*1.0, y*1.0, z*1.0);
    }

    @Override
    public Point<Double> createPoint(Double x, Double y, Double z) {
        return new Point<>(x, y, z);
    }

    @Override
    public Line2DMQ<Double> createLine(Double m, Point<Double> p) {
        double q = p.y() - m * p.x();
        return new Line2DMQ<>(m, q);
    }

    @Override
    public Optional<Point<Double>> intersect(Line2DMQ<Double> line1, Line2DMQ<Double> line2) {
        if (line1.m().equals(line2.m())) {
            return Optional.empty();
        }

        double dq = line2.q() - line1.q();
        double dm = line1.m() - line2.m();

        double x = dq / dm;
        double y = line1.m() * x + line1.q();

        return Optional.of(new Point<>(x, y, 0.0));
    }

    @Override
    public Optional<Point<Double>> intersect(PointWithVelocity<Double> ray1, PointWithVelocity<Double> ray2) {
        Optional<Point<Double>> intersection = this.intersect(ray1.trajectory(), ray2.trajectory());
        if (intersection.isEmpty()) {
            return Optional.empty();
        }

        double thisTimeToPoint = timeToPoint(ray1, intersection.get());
        double otherTimeToPoint = timeToPoint(ray2, intersection.get());

        if (thisTimeToPoint < 0 || otherTimeToPoint < 0) {
            return Optional.empty();
        }

        return intersection;
    }

    private double timeToPoint(PointWithVelocity<Double> ray, Point<Double> dst) {
        double dx = dst.x() - ray.p().x();

        return dx / ray.v().x();
    }
}
