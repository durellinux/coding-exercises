package com.gianlucadurelli.coding.libraries.math;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigInteger;
public class FractionTest {

    @Test
    public void sum() {
        Fraction f1 = new Fraction(BigInteger.valueOf(3), BigInteger.valueOf(4));
        Fraction f2 = new Fraction(BigInteger.valueOf(3), BigInteger.valueOf(4));

        Fraction sum = f1.add(f2).as();
        Assertions.assertThat(sum.numerator().longValue()).isEqualTo(3);
        Assertions.assertThat(sum.denominator().longValue()).isEqualTo(2);
    }

    @Test
    public void subtract() {
        Fraction f1 = new Fraction(BigInteger.valueOf(3), BigInteger.valueOf(4));
        Fraction f2 = new Fraction(BigInteger.valueOf(2), BigInteger.valueOf(8));

        Fraction diff = f1.subtract(f2);
        Assertions.assertThat(diff.numerator().longValue()).isEqualTo(1);
        Assertions.assertThat(diff.denominator().longValue()).isEqualTo(2);
    }
}