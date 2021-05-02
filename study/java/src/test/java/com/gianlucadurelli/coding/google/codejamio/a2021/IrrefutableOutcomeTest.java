package com.gianlucadurelli.coding.google.codejamio.a2021;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class IrrefutableOutcomeTest {

    //5
    //IOIOOOII
    //OIIIIO
    //IO
    //IOIOIOI
    //IOIOIOOIO

    //Case #1: I 8
    //Case #2: O 7
    //Case #3: O 1
    //Case #4: I 1
    //Case #5: O 6

    @Test
    public void sampleTest() {
        HashMap<String, Integer> t1 = IrrefutableOutcome.solve("IOIOOOII");
        Assertions.assertThat(t1.containsKey("I")).isTrue();
        Assertions.assertThat(t1.get("I")).isEqualTo(8);

        HashMap<String, Integer> t2 = IrrefutableOutcome.solve("OIIIIO");
        Assertions.assertThat(t2.containsKey("O")).isTrue();
        Assertions.assertThat(t2.get("O")).isEqualTo(7);

        HashMap<String, Integer> t3 = IrrefutableOutcome.solve("IO");
        Assertions.assertThat(t3.containsKey("O")).isTrue();
        Assertions.assertThat(t3.get("O")).isEqualTo(1);

        HashMap<String, Integer> t4 = IrrefutableOutcome.solve("IOIOIOI");
        Assertions.assertThat(t4.containsKey("I")).isTrue();
        Assertions.assertThat(t4.get("I")).isEqualTo(1);

        HashMap<String, Integer> t5 = IrrefutableOutcome.solve("IOIOIOOIO");
        Assertions.assertThat(t5.containsKey("O")).isTrue();
        Assertions.assertThat(t5.get("O")).isEqualTo(6);
    }
}