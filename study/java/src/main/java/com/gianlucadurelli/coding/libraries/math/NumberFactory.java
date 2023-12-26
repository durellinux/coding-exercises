package com.gianlucadurelli.coding.libraries.math;

import java.math.BigInteger;

public class NumberFactory {
    public static <T extends Number> Number create(long value, Class<T> clazz) {
        if (clazz.equals(DoublePrecision.class)) {
            return createDoublePrecision(value);
        }

        if (clazz.equals(LongPrecision.class)) {
            return createLongPrecision(value);
        }

        if (clazz.equals(Fraction.class)) {
            return createFraction(value);
        }


        throw new IllegalArgumentException("Unsupported type: " + clazz);
    }

    private static DoublePrecision createDoublePrecision(long value) {
        return new DoublePrecision(value);
    }
    private static LongPrecision createLongPrecision(long value) {
        return new LongPrecision(value);
    }

    private static Fraction createFraction(long value) {
        return new Fraction(BigInteger.valueOf(value), BigInteger.ONE);
    }
}
