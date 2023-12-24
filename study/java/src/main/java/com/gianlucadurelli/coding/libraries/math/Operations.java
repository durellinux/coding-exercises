package com.gianlucadurelli.coding.libraries.math;

public interface Operations<T extends Operations<T>> {
    double asDouble();
    T getZero();
    T getOne();

    T add(T other);
    T subtract(T other);
    T multiplyBy(T other);
    T divideBy(T other);
}
