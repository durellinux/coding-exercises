package com.gianlucadurelli.coding.hackerrank.neurodiversity;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class AutismScreeningTest {

    @Test
    public void case1() {
        // 4
        //ABC
        //BCD
        //EFG
        //DCB

        List<String> inputs = List.of("ABC", "BCD", "EFG", "DCB");
        String result = AutismScreening.findOdd(inputs);

        Assertions.assertThat(result).isEqualTo("DCB");
    }

    @Test
    public void case2() {
        // 5
        //ADC
        //BED
        //CFE
        //DGF
        //FGH

        List<String> inputs = List.of("ADC", "BED", "CFE", "DGF", "FGH");
        String result = AutismScreening.findOdd(inputs);

        Assertions.assertThat(result).isEqualTo("FGH");
    }
}