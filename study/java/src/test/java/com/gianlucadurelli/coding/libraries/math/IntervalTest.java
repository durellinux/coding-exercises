package com.gianlucadurelli.coding.libraries.math;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntervalTest {
    private static final Interval i0_5 = new Interval(new LongPrecision(0), new LongPrecision(5));
    private static final Interval i0_1 = new Interval(new LongPrecision(0), new LongPrecision(1));
    private static final Interval i1_3 = new Interval(new LongPrecision(1), new LongPrecision(3));
    private static final Interval i1_2 = new Interval(new LongPrecision(1), new LongPrecision(2));
    private static final Interval i2_2 = new Interval(new LongPrecision(2), new LongPrecision(2));
    private static final Interval i2_5 = new Interval(new LongPrecision(2), new LongPrecision(5));
    private static final Interval i4_5 = new Interval(new LongPrecision(4), new LongPrecision(5));

    @Test
    public void isBefore() {
        Assertions.assertThat(i1_3.isBefore(i4_5)).isTrue();
        Assertions.assertThat(i4_5.isBefore(i1_3)).isFalse();
        Assertions.assertThat(i1_3.isBefore(i2_5)).isFalse();
        Assertions.assertThat(i1_3.isBefore(i1_3)).isFalse();
    }

    @Test
    public void isAfter() {
        Assertions.assertThat(i1_3.isAfter(i4_5)).isFalse();
        Assertions.assertThat(i4_5.isAfter(i1_3)).isTrue();
        Assertions.assertThat(i1_3.isAfter(i2_5)).isFalse();
        Assertions.assertThat(i1_3.isAfter(i1_3)).isFalse();
    }

    @Test
    public void areOverlapping() {
        Assertions.assertThat(i1_3.areOverlapping(i4_5)).isFalse();
        Assertions.assertThat(i4_5.areOverlapping(i1_3)).isFalse();
        Assertions.assertThat(i1_3.areOverlapping(i2_5)).isTrue();
        Assertions.assertThat(i1_3.areOverlapping(i1_3)).isTrue();
    }

    @Test
    public void intersect() {
        Assertions.assertThat(i1_3.intersect(i4_5)).isEmpty();
        Assertions.assertThat(i4_5.intersect(i1_3)).isEmpty();
        Assertions.assertThat(i1_3.intersect(i1_2)).contains(i1_2);
        Assertions.assertThat(i1_3.intersect(i2_5)).contains(new Interval(new LongPrecision(2), new LongPrecision(3)));
        Assertions.assertThat(i1_3.intersect(i1_3)).contains(i1_3);
        Assertions.assertThat(i1_3.intersect(i0_5)).contains(i1_3);
        Assertions.assertThat(i1_3.intersect(i0_1)).contains(new Interval(new LongPrecision(1), new LongPrecision(1)));
        Assertions.assertThat(i1_3.intersect(i2_2)).contains(i2_2);
    }
}