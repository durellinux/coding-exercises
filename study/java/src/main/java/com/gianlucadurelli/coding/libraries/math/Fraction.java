package com.gianlucadurelli.coding.libraries.math;

import java.math.BigDecimal;
import java.math.BigInteger;

public record Fraction(BigInteger numerator, BigInteger denominator) implements Operations<Fraction> {
    public static Fraction ZERO = Fraction.simplified(BigInteger.ZERO, BigInteger.ONE);
    public static Fraction MINUS_ONE = Fraction.simplified(BigInteger.ONE.negate(), BigInteger.ONE);

    public static Fraction ONE = Fraction.simplified(BigInteger.ONE, BigInteger.ONE);

    @Override
    public Fraction getZero() {
        return ZERO;
    }

    @Override
    public Fraction getOne() {
        return ONE;
    }

    public Fraction add(Fraction other) {
        BigInteger denominator = this.denominator.multiply(other.denominator);
        BigInteger numerator = this.numerator.multiply(other.denominator).add(other.numerator.multiply(this.denominator));
        return Fraction.simplified(numerator, denominator);
    }

    public Fraction subtract(Fraction other) {
        return this.add(other.multiplyBy(MINUS_ONE));
    }

    public Fraction multiplyBy(Fraction other) {
        return Fraction.simplified(this.numerator.multiply(other.numerator), this.denominator.multiply(other.denominator));
    }

    public Fraction divideBy(Fraction other) {
        Fraction inverse = Fraction.simplified(other.denominator, other.numerator);
        return this.multiplyBy(inverse);
    }

    public double asDouble() {
        return this.numerator.doubleValue() / this.denominator.doubleValue();
    }

    private static Fraction simplified(BigInteger numerator, BigInteger denominator) {
        if (denominator.longValue() == 0) {
            throw new RuntimeException("Dividing by 0");
        }

        boolean isPositive = numerator.multiply(denominator).signum() != -1;

        BigInteger positiveNumerator = numerator.abs();
        BigInteger positiveDenominator = denominator.abs();
        BigInteger divisor = positiveNumerator.gcd(positiveDenominator);
        long signMultiplier = isPositive ? 1L : -1L;
        return new Fraction(BigInteger.valueOf(signMultiplier).multiply(positiveNumerator).divide(divisor), positiveDenominator.divide(divisor));
    }
}
