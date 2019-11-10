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

}