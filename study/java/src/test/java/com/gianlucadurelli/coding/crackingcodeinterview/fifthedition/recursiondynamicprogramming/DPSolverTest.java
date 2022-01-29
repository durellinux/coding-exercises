package com.gianlucadurelli.coding.crackingcodeinterview.fifthedition.recursiondynamicprogramming;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DPSolverTest {

    private DPSolver solver = new DPSolver();

    @Test
    public void Ex9_1_childSteps() {
        int steps = 7;
        List<Integer> possibleHops = List.of(1, 2, 3);
        long expectedResult = 44;

        DPCounterSum recursive = solver.getClimbingWaysRecursive(steps, 0, possibleHops);
        DPCounterSum dp = solver.getClimbingWaysDP(steps, 0, possibleHops, new HashMap<>());

        Assertions.assertThat(recursive.getResult()).isEqualTo(expectedResult);
        Assertions.assertThat(dp.getResult()).isEqualTo(expectedResult);
        Assertions.assertThat(dp.getCount()).isLessThanOrEqualTo(recursive.getCount());

        System.out.println("Recursive steps: " + recursive.getCount());
        System.out.println("Dynamic programming steps: " + dp.getCount());
    }

    @Test
    public void Ex9_2_robotPaths() {
        int xEnd = 10;
        int yEnd = 10;

        DPCounterSum recursive = solver.getRobotPathsRecursive(0,0, xEnd, yEnd);
        DPCounterSum dp = solver.getRobotPathsDP(0,0, xEnd, yEnd, new HashMap<>());

        Assertions.assertThat(recursive.getResult()).isEqualTo(dp.getResult());
        Assertions.assertThat(dp.getCount()).isLessThanOrEqualTo(recursive.getCount());

        System.out.println("Recursive steps: " + recursive.getCount());
        System.out.println("Dynamic programming steps: " + dp.getCount());
    }

    @Test
    public void Ex9_2_robotPaths_followUp() {
        int xEnd = 3;
        int yEnd = 4;

        Set<Position2D> unallowed = Set.of(
                new Position2D(3, 3),
                new Position2D(1, 1)
        );

        DPCounterSum dpUnallowed = solver.getRobotPathsDPUnallowed(0, 0, xEnd, yEnd, new HashMap<>(), unallowed);
        List<Position2D> path = solver.getRobotPathWithUnallowed(new Position2D(0, 0), new Position2D(xEnd, yEnd), unallowed);

        if (dpUnallowed.getResult() > 0) {
            Assertions.assertThat(path).isNotEmpty();
            Assertions.assertThat(path.size()).isEqualTo(xEnd + yEnd + 1);
            for (Position2D unallowedPosition: unallowed) {
                Assertions.assertThat(path).doesNotContain(unallowedPosition);
            }
            System.out.println(path);
        } else {
            Assertions.assertThat(path).isEmpty();
        }
    }

    @Test
    public void Ex9_3_magicIndexPresent() {
        Integer[] values = new Integer[]{-1, 0, 2, 4, 6, 7, 12};

        Optional<Integer> magicBinarySearch = solver.getMagicIndexDistinct(values);
        Optional<Integer> magicIterative = solver.getMagicIndex(values);

        Assertions.assertThat(magicBinarySearch).isEqualTo(magicIterative);
        Assertions.assertThat(magicBinarySearch.isPresent()).isTrue();
        Assertions.assertThat(magicBinarySearch.get()).isEqualTo(2);
    }


    @Test
    public void Ex9_3_magicIndexNotPresent() {
        Integer[] values = new Integer[]{-1, 0, 3, 4, 6, 7, 12};

        Optional<Integer> magicBinarySearch = solver.getMagicIndexDistinct(values);
        Optional<Integer> magicIterative = solver.getMagicIndex(values);

        Assertions.assertThat(magicBinarySearch).isEqualTo(magicIterative);
        Assertions.assertThat(magicBinarySearch.isPresent()).isFalse();
    }

    @Test
    public void Ex9_3_magicIndexFollowUpPresent() {
        Integer[] values = new Integer[]{-1, 0, 3, 3, 6, 7, 12};

        Optional<Integer> magicIterative = solver.getMagicIndex(values);
        Assertions.assertThat(magicIterative.isPresent()).isTrue();
        Assertions.assertThat(magicIterative.get()).isEqualTo(3);
    }


    @Test
    public void Ex9_3_magicIndexFollowUpNotPresent() {
        Integer[] values = new Integer[]{-1, 0, 0, 0, 5, 6, 12};

        Optional<Integer> magicIterative = solver.getMagicIndex(values);
        Assertions.assertThat(magicIterative.isPresent()).isFalse();
    }

    @Test
    public void Ex9_4_allSubsets() {
        Set<Integer> set = Set.of(1, 2, 3);
        List<Set<Integer>> subsets = solver.getAllSubsets(set);

        System.out.println(subsets);
    }

    @Test
    public void Ex9_5_stringPermutationsUniqueCharacters() {
        assertPermutations("a");
        assertPermutations("ab");
        assertPermutations("abc");
        assertPermutations("abcd");
//        assertPermutations("abcde");
//        assertPermutations("abcdef");
//        assertPermutations("abcdefg");
//        assertPermutations("abcdefgh");
    }

    private void assertPermutations(String string) {
        long expectedPermutations = factorial(string.length());
        Set<String> permutations = solver.computePermutations(string);
        Assertions.assertThat(permutations.size()).isEqualTo(expectedPermutations);

        System.out.println(permutations.size() + ": " + permutations);
    }

    private long factorial(int n) {
        long fact = 1;
        for (int i=1; i<=n; i++) {
            fact *= i;
        }

        return fact;
    }

    @Test
    public void Ex9_5_stringPermutationsRepeatedCharacters() {
        assertPermutationsValues("aa", List.of("aa"));
        assertPermutationsValues("aab", List.of("aab", "aba", "baa"));
        assertPermutationsValues("aabb", List.of("aabb", "abab", "baab", "abba", "bbaa", "baba"));
    }

    private void assertPermutationsValues(String string, List<String> expectedPermutations) {
        Set<String> permutations = solver.computePermutations(string);
        Assertions.assertThat(permutations).containsExactlyInAnyOrderElementsOf(expectedPermutations);

        System.out.println(permutations.size() + ": " + permutations);
    }

    // TODO: Check which number it is
    @Test
    public void Ex9_XXX_parenthesisLanguage() {
        Assertions.assertThat(solver.generateValidParenthesesStrings(1)).containsExactlyInAnyOrder("()");
        Assertions.assertThat(solver.generateValidParenthesesStrings(2)).containsExactlyInAnyOrder("(())", "()()");
        Assertions.assertThat(solver.generateValidParenthesesStrings(3)).containsExactlyInAnyOrder("((()))", "()()()", "(())()", "()(())", "(()())");
    }

    // TODO: Check which number it is
    @Test
    public void Ex9_YYY_fillCanvas() {
        Canvas canvas = new Canvas(new RgbColor[][]{
                new RgbColor[] {RgbColor.WHITE, RgbColor.WHITE, RgbColor.RED, RgbColor.WHITE},
                new RgbColor[] {RgbColor.WHITE, RgbColor.WHITE, RgbColor.RED, RgbColor.WHITE},
                new RgbColor[] {RgbColor.WHITE, RgbColor.WHITE, RgbColor.BLACK, RgbColor.BLACK},
                new RgbColor[] {RgbColor.WHITE, RgbColor.WHITE, RgbColor.BLACK, RgbColor.WHITE},
        });

        solver.fillCanvas(canvas, 0, 1, RgbColor.RED);

        Canvas expectedCanvas = new Canvas(new RgbColor[][]{
                new RgbColor[] {RgbColor.RED, RgbColor.RED, RgbColor.RED, RgbColor.WHITE},
                new RgbColor[] {RgbColor.RED, RgbColor.RED, RgbColor.RED, RgbColor.WHITE},
                new RgbColor[] {RgbColor.RED, RgbColor.RED, RgbColor.BLACK, RgbColor.BLACK},
                new RgbColor[] {RgbColor.RED, RgbColor.RED, RgbColor.BLACK, RgbColor.WHITE},
        });

        Assertions.assertThat(canvas.getWidth()).isEqualTo(expectedCanvas.getWidth());
        Assertions.assertThat(canvas.getHeight()).isEqualTo(expectedCanvas.getHeight());

        for (int w=0; w<canvas.getWidth(); w++) {
            for (int h = 0; h<canvas.getHeight(); h++) {
                Assertions.assertThat(canvas.getPixel(h, w)).isEqualTo(expectedCanvas.getPixel(h, w));
            }
        }
    }

    // TODO: Check which number it is
    @Test
    public void Ex9_ZZZ_customMultiply() {
        Assertions.assertThat(solver.multiply(3, 2)).isEqualTo(6);
        Assertions.assertThat(solver.multiply(0, 2)).isEqualTo(0);
        Assertions.assertThat(solver.multiply(2, 0)).isEqualTo(0);
        Assertions.assertThat(solver.multiply(3, -4)).isEqualTo(-12);
    }

    // TODO: this should not be here but in medium/hard problems solver
    @Test
    public void xorTest() {
        int a = 23;
        int b = 35;

        System.out.println("A: " + a + " - B:" + b);

        a = a ^ b;
        b = a ^ b;
        a = b ^ a;


        System.out.println("A: " + a + " - B:" + b);
    }
}