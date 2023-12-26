package com.gianlucadurelli.coding.libraries.geometry;

import com.gianlucadurelli.coding.libraries.math.Number;
import com.gianlucadurelli.coding.libraries.math.NumberFactory;

import java.util.Optional;

public class GeometryLib {
    public Point createPoint(Number x, Number y, Number z) {
        return new Point(x, y, z);
    }

    public Line2DMQ createLine(Number m, Point p) {
        Number q = p.y().subtract(m.multiplyBy(p.x()));
        return new Line2DMQ(m, q);
    }

    public Optional<Point> intersect(Line2DMQ line1, Line2DMQ line2) {
        if (line1.m().equals(line2.m())) {
            return Optional.empty();
        }

        Number dq = line2.q().subtract(line1.q());
        Number dm = line1.m().subtract(line2.m());

        Number x = dq.divideBy(dm);
        Number y = line1.m().multiplyBy(x).add(line1.q());

        Number ZERO = NumberFactory.create(0, x.getClass());
        return Optional.of(new Point(x, y, ZERO));
    }

    public Optional<Point> intersect(PointWithVelocity ray1, PointWithVelocity ray2) {
        Optional<Point> intersection = this.intersect(ray1.trajectory(), ray2.trajectory());
        if (intersection.isEmpty()) {
            return Optional.empty();
        }

        Number thisTimeToPoint = timeToPoint(ray1, intersection.get());
        Number otherTimeToPoint = timeToPoint(ray2, intersection.get());

        if (thisTimeToPoint.asDouble() < 0 || otherTimeToPoint.asDouble() < 0) {
            return Optional.empty();
        }

        return intersection;
    }

    private Number timeToPoint(PointWithVelocity ray, Point dst) {
        Number dx = dst.x().subtract(ray.p().x());
        return dx.divideBy(ray.v().x());
    }
}
