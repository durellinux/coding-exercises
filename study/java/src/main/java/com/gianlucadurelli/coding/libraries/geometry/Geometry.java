package com.gianlucadurelli.coding.libraries.geometry;

import com.gianlucadurelli.coding.libraries.math.Operations;

import java.util.Optional;

public interface Geometry<T>{
    Point<T> createPoint(long x, long y, long z);
    Point<T> createPoint(T x, T y, T z);
    Line2DMQ<T> createLine(T m, Point<T> point);




    Optional<Point<T>> intersect(Line2DMQ<T> line1, Line2DMQ<T> line2);
    Optional<Point<T>> intersect(PointWithVelocity<T> ray1, PointWithVelocity<T> ray2);
}
