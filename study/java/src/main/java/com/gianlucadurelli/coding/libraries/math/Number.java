package com.gianlucadurelli.coding.libraries.math;

public interface Number extends Comparable<Number> {
    default <T> T as() {
        return (T) this;
    }
    default <T> T as(Class<T> clazz) {
        return clazz.cast(this);
    }
    double asDouble();
    Number getZero();
    Number getOne();

    Number add(Number other);
    default Number add(long other) {
        Number otherNumber = NumberFactory.create(other, this.getClass());
        return add(otherNumber);
    }
    Number subtract(Number other);
    Number multiplyBy(Number other);
    Number divideBy(Number other);
}
