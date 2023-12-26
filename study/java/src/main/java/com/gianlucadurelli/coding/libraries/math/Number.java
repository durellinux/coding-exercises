package com.gianlucadurelli.coding.libraries.math;

public interface Number {
    default <T> T as() {
        return (T) this;
    }
    double asDouble();
    Number getZero();
    Number getOne();

    Number add(Number other);
    Number subtract(Number other);
    Number multiplyBy(Number other);
    Number divideBy(Number other);
}
