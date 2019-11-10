package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.stackqueue;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SetOfStacksTest {

    // TODO: Rename test with exercise name
    @Test
    public void testClass() throws Exception {
        int start = 1;
        int end = 1000;
        int threshold = 17;

        SetOfStacks<Integer> setOfStacks = new SetOfStacks<>(threshold);

        for (int i=start; i<=end; i++) {
            setOfStacks.push(i);
        }

        Assertions.assertThat(setOfStacks.getNumberOfStacks()).isEqualTo(getCeil(end, threshold));

        for (int i=end; i>=start; i--) {
            Integer v = setOfStacks.pop();
            Assertions.assertThat(v).isEqualTo(i);
        }

        Assertions.assertThat(setOfStacks.getNumberOfStacks()).isEqualTo(0);
    }

    private int getCeil(int i, int v) {
        if (i < 0) {
            return 0;
        }
        return (i/v) + (i%v == 0 ? 0 : 1);
    }
}