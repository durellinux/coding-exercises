package com.gianlucadurelli.coding.libraries.math;

import static com.gianlucadurelli.coding.libraries.math.MathLib.gcd;

public record Fraction(long numerator, long denominator) implements Operations<Fraction> {
    public static Fraction ZERO = Fraction.simplified(0, 1);
    public static Fraction MINUS_ONE = Fraction.simplified(-1, 1);

    public static Fraction ONE = Fraction.simplified(1, 1);

    @Override
    public Fraction fromLong(long value) {
        return new Fraction(value, 1);
    }

    @Override
    public Fraction getZero() {
        return ZERO;
    }

    @Override
    public Fraction getOne() {
        return ONE;
    }

    public Fraction add(Fraction other) {
        long denominator = this.denominator * other.denominator;
        long numerator = this.numerator * other.denominator + other.numerator * this.denominator;
        return Fraction.simplified(numerator, denominator);
    }

    public Fraction subtract(Fraction other) {
        return this.add(other.multiplyBy(MINUS_ONE));
    }

    public Fraction multiplyBy(Fraction other) {
        return Fraction.simplified(this.numerator * other.numerator, this.denominator * other.denominator);
    }

    public Fraction divideBy(Fraction other) {
        Fraction inverse = Fraction.simplified(other.denominator, other.numerator);
        return this.multiplyBy(inverse);
    }

    public double asDouble() {
        return this.numerator * 1.0 / this.denominator;
    }

    private static Fraction simplified(long numerator, long denominator) {
        if (denominator == 0) {
            throw new RuntimeException("Dividing by 0");
        }
        boolean isPositive = numerator/denominator > 0;
        long positiveNumerator = Math.abs(numerator);
        long positiveDenominator = Math.abs(denominator);
        long divisor = gcd(positiveNumerator, positiveDenominator);
        long signMultiplier = isPositive ? 1L : -1L;
        return new Fraction(signMultiplier * positiveNumerator / divisor, positiveDenominator / divisor);
    }
}
