package com.gianlucadurelli.coding.libraries.math;

public record DoublePrecision(double value) implements Operations<DoublePrecision> {
    private static final DoublePrecision ZERO = new DoublePrecision(0);
    private static final DoublePrecision ONE = new DoublePrecision(1);

    @Override
    public DoublePrecision fromLong(long value) {
        return new DoublePrecision(value);
    }

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
    public DoublePrecision add(DoublePrecision other) {
        return new DoublePrecision(this.value + other.value);
    }

    @Override
    public DoublePrecision subtract(DoublePrecision other) {
        return new DoublePrecision(this.value - other.value);
    }

    @Override
    public DoublePrecision multiplyBy(DoublePrecision other) {
        return new DoublePrecision(this.value * other.value);
    }

    @Override
    public DoublePrecision divideBy(DoublePrecision other) {
        return new DoublePrecision(this.value / other.value);
    }
}
