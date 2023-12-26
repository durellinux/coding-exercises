package com.gianlucadurelli.coding.libraries.math;

public record LongPrecision(long value) implements Number {
    @Override
    public double asDouble() {
        return value;
    }

    public static LongPrecision valueOf(String string, int base) {
        return new LongPrecision(Long.valueOf(string, base));
    }

    @Override
    public LongPrecision getZero() {
        return new LongPrecision(0);
    }

    @Override
    public LongPrecision getOne() {
        return new LongPrecision(1);
    }

    @Override
    public LongPrecision add(Number other) {
        return new LongPrecision(this.value + other.as(LongPrecision.class).value);
    }

    @Override
    public Number subtract(Number other) {
        return new LongPrecision(this.value - other.as(LongPrecision.class).value);
    }

    @Override
    public LongPrecision multiplyBy(Number other) {
        return new LongPrecision(this.value * other.as(LongPrecision.class).value);
    }

    @Override
    public LongPrecision divideBy(Number other) {
        return new LongPrecision(this.value / other.as(LongPrecision.class).value);
    }

    @Override
    public int compareTo(Number o) {
        LongPrecision other = o.as();
        if (this.equals(other)) {
            return 0;
        }

        return Long.compare(this.value, other.value);
    }
}
