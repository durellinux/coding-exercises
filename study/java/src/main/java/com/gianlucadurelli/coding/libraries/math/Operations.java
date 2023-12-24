package com.gianlucadurelli.coding.libraries.math;

public interface Operations<T> {
    T fromLong(long value);
    double asDouble();
    T getZero();
    T getOne();

    T add(T other);
    T subtract(T other);
    T multiplyBy(T other);
    T divideBy(T other);
}
