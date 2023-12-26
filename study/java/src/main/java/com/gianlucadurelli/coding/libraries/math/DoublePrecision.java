package com.gianlucadurelli.coding.libraries.math;

public record DoublePrecision(double value) implements Number {
    public static final DoublePrecision ZERO = new DoublePrecision(0);
    public static final DoublePrecision ONE = new DoublePrecision(1);

    @Override
    public double asDouble() {
        return value;
    }

    @Override
    public DoublePrecision getZero() {
        return ZERO;
    }

    @Override
    public DoublePrecision getOne() {
        return ONE;
    }

    @Override
    public DoublePrecision add(Number otherNumber) {
        DoublePrecision other = otherNumber.as();
        return new DoublePrecision(this.value + other.value);
    }

    @Override
    public DoublePrecision subtract(Number otherNumber) {
        DoublePrecision other = otherNumber.as();
        return new DoublePrecision(this.value - other.value);
    }

    @Override
    public DoublePrecision multiplyBy(Number otherNumber) {
        DoublePrecision other = otherNumber.as();
        return new DoublePrecision(this.value * other.value);
    }

    @Override
    public DoublePrecision divideBy(Number otherNumber) {
        DoublePrecision other = otherNumber.as();
        return new DoublePrecision(this.value / other.value);
    }
}
