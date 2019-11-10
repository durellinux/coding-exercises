package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.linkedlist;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class Ex2_3Test {

    Ex2_3<Integer> solver = new Ex2_3<>();

    @Test
    public void removeElement() {
        Node<Integer> test1 = Node.fromList(List.of(1, 2, 3, 4, 5));

        solver.removeElement(Node.toNodeList(test1).get(2));

        Assertions.assertThat(Node.toList(test1)).isEqualTo(List.of(1, 2, 4, 5));
    }

}