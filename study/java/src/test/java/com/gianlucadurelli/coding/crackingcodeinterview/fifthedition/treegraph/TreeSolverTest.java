package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.treegraph;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TreeSolverTest {

    private ITreeSolver solver = new TreeSolver();

    @Test
    public void Ex4_1_balancedTree() {
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4, null, null);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3, null, null);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2, n4, null);
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1, n3, null);
        BinaryTreeNode<Integer> n0 = new BinaryTreeNode<>(0, n1, n2);

        boolean isBalanced = solver.isBalanced(n0);
        Assertions.assertThat(isBalanced).isTrue();
    }

    @Test
    public void Ex4_1_unbalancedTree() {
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4, null, null);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3, n4, null);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2, null, null);
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1, n3, null);
        BinaryTreeNode<Integer> n0 = new BinaryTreeNode<>(0, n1, n2);

        boolean isBalanced = solver.isBalanced(n0);
        Assertions.assertThat(isBalanced).isFalse();
    }


    @Test
    public void Ex4_3_buildBinarySearchTree() {
        Integer[] sortedValues = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        BinaryTreeNode<Integer> searchTree = solver.buildBinarySearchTree(sortedValues);

        Assertions.assertThat(searchTree).isNotNull();
        Assertions.assertThat(searchTree.isBinarySearch(Integer::compareTo)).isNotNull();
    }


    @Test
    public void Ex4_4_getListOfNodesPerLevel() {
        BinaryTreeNode<Integer> n4 = new BinaryTreeNode<>(4, null, null);
        BinaryTreeNode<Integer> n3 = new BinaryTreeNode<>(3, null, null);
        BinaryTreeNode<Integer> n2 = new BinaryTreeNode<>(2, n4, null);
        BinaryTreeNode<Integer> n1 = new BinaryTreeNode<>(1, n3, null);
        BinaryTreeNode<Integer> n0 = new BinaryTreeNode<>(0, n1, n2);

        List<List<BinaryTreeNode<Integer>>> expectedResult = List.of(
                List.of(n0),
                List.of(n1, n2),
                List.of(n3, n4)
        );

        List<List<BinaryTreeNode<Integer>>> actual = solver.getListOfNodesPerLevel(n0);

        Assertions.assertThat(actual.size()).isEqualTo(expectedResult.size());
        for (int i = 0; i<actual.size(); i++) {
            Assertions.assertThat(actual.get(i)).containsExactlyInAnyOrderElementsOf(expectedResult.get(i));
        }
    }

    @Test
    public void Ex4_5_getSuccessor() {
        Integer[] sortedValues = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        BinaryTreeNode<Integer> searchTree = solver.buildBinarySearchTree(sortedValues);
        List<List<BinaryTreeNode<Integer>>> lists = solver.getListOfNodesPerLevel(searchTree);
        List<BinaryTreeNode<Integer>> nodesList = new LinkedList<>();
        for (List<BinaryTreeNode<Integer>> nodes: lists) {
            nodesList.addAll(nodes);
        }

        for (BinaryTreeNode<Integer> node: nodesList) {
            BinaryTreeNode<Integer> expectedNextNode = nodesList.stream().filter(n -> n.getData().equals(node.getData() + 1)).findAny().orElse(null);
            BinaryTreeNode<Integer> actualNext = solver.nextBinarySearchTreeNode(node);
            Assertions.assertThat(actualNext).isEqualTo(expectedNextNode);
        }
    }

    @Test
    public void Ex4_9_getPathsSummingToNumber() {
        Integer[] sortedValues = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        BinaryTreeNode<Integer> searchTree = solver.buildBinarySearchTree(sortedValues);

        for (Integer i: sortedValues) {
            System.out.println("Paths summing to: " + i);
            Set<List<BinaryTreeNode<Integer>>> paths = solver.getPathsSummingToNumber(searchTree, i);
            Assertions.assertThat(paths.size()).isGreaterThanOrEqualTo(1);
            for (List<BinaryTreeNode<Integer>> path: paths) {
                Integer sum = 0;
                for (BinaryTreeNode<Integer> node: path) {
                    sum += node.data;
                }
                Assertions.assertThat(sum).isEqualTo(i);

                List<Integer> valuePaths = path.stream().map(n -> n.data).collect(Collectors.toList());
                System.out.println(valuePaths);
            }
        }
    }
}