package com.gianlucadurelli.coding.libraries.math;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumberFactoryTest {

    @Test
    public void createOfSameClass() {
        Number n1 = NumberFactory.create(1, Fraction.class);
        Number n2 = NumberFactory.create(1, n1.getClass());

        Assertions.assertThat(n2.getClass()).isEqualTo(Fraction.class);
    }

}