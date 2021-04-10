package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.linkedlist;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Ex2_1Test {
    private Ex2_1<Integer> solver = new Ex2_1<>();

    @Test
    public void removeDuplicatesWithSet() {
        LinkedList<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 1, 2, 3, 4));

        solver.removeDuplicatesWithSet(list);

        LinkedList<Integer> expected = new LinkedList<>( new HashSet<>(list) );
        Assertions.assertThat(list).isEqualTo(expected);
    }

    @Test
    public void removeInPlace() {
        LinkedList<Integer> list = new LinkedList<>(List.of(1, 2, 3, 4, 1, 2, 3, 4));
        Node<Integer> node = Node.fromList(list);

        solver.removeInPlace(node);

        LinkedList<Integer> expected = new LinkedList<>( new HashSet<>(list) );
        List<Integer> result = Node.toList(node);
        Assertions.assertThat(result).isEqualTo(expected);
    }
}