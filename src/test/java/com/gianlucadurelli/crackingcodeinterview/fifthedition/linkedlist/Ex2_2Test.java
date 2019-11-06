package com.gianlucadurelli.crackingcodeinterview.fifthedition.linkedlist;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Ex2_2Test {

    Ex2_2<Integer> solver = new Ex2_2<>();

    @Test
    public void getKFromEnd() {
        Node<Integer> testNull = null;
        doTest(testNull, 0, null);

        Node<Integer> testSingle = new Node<>(1);
        doTest(testSingle, 0, 1);
        doTest(testSingle, 1, null);

        Node<Integer> testValues = Node.fromList(List.of(1, 2, 3, 4, 5));
        doTest(testValues, 0, 5);
        doTest(testValues, 1, 4);
        doTest(testValues, 2, 3);
        doTest(testValues, 3, 2);
        doTest(testValues, 4, 1);
        doTest(testValues, 5, null);
    }

    private void doTest(Node<Integer> list, int k,  Integer expected) {
        Integer result = solver.getKFromEnd(list, k);
        Assertions.assertThat(result).isEqualTo(expected);
    }
}