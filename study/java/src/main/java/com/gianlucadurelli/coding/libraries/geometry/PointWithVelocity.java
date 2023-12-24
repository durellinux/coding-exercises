package com.gianlucadurelli.coding.libraries.geometry;

import com.gianlucadurelli.coding.libraries.math.Operations;

import java.util.Optional;

public record PointWithVelocity<T>(Point<T> p, Point<T> v, Line2DMQ<T> trajectory) {

}