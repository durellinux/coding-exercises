package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;
import java.util.stream.Collectors;

public class Day18LavaductLagoon {

    private record DigPlan(char direction, int length, String color) {}
    private record Point(int row, int col) {}
    private record Segment(Set<Point> points, int rowMin, int rowMax) {}
    private record RaycastFlipSegment(int startCol, int endCol) {}


    public long solve(List<DigPlan> plans) {
        return solveWithPolygonsRaycast(plans);
    }

    private long solveWithPolygonsRaycast(List<DigPlan> plans) {
        Set<Point> thrench = new HashSet<>();
        Map<Integer, List<Integer>> pointsOnRows = new HashMap<>();
        List<Point> corners = new ArrayList<>();
        Set<Segment> segments = new HashSet<>();

        getStuffV1(plans, thrench, corners, segments, pointsOnRows);

        return pointInsidePolygonByRaycast(thrench, segments, pointsOnRows, corners);
    }

    private long pointInsidePolygonByRaycast(Set<Point> thrench, Set<Segment> segments, Map<Integer, List<Integer>> pointsOnRows, List<Point> corners) {
        int minR = thrench.stream().map(p -> p.row).min(Integer::compareTo).orElse(0);

        Queue<Integer> sortedRows = new LinkedList<>(pointsOnRows.keySet().stream().sorted().toList());

        long dugPoints = pointInsidePolygonByRaycastOnRows(minR, sortedRows, segments, corners);

        for (Segment s: segments) {
            int minCol = s.points.stream().map(p -> p.col).min(Integer::compareTo).orElse(0);
            int maxCol = s.points.stream().map(p -> p.col).max(Integer::compareTo).orElse(0);

            dugPoints += ((long) (s.rowMax - s.rowMin + 1) * (maxCol - minCol + 1));
        }

        return dugPoints - thrench.size();
    }

    private long pointInsidePolygonByRaycastOnRows(int minR, Queue<Integer> sortedRows, Set<Segment> segments, List<Point> corners) {
        long dugPoints = 0;
        Set<Segment> verticalSegments = segments.stream().filter(s -> s.rowMin != s.rowMax).collect(Collectors.toSet());
        Set<Segment> horizontalSegment = segments.stream().filter(s -> s.rowMin == s.rowMax).collect(Collectors.toSet());

        int rowStart = minR;
        while(!sortedRows.isEmpty()) {
            int rowEnd = sortedRows.poll();

            dugPoints += countByRayCastOnRowRange(rowStart, rowStart, verticalSegments, horizontalSegment, corners);

            if (rowStart +1 <= rowEnd - 1) {
                dugPoints += countByRayCastOnRowRange(rowStart + 1, rowEnd - 1, verticalSegments, horizontalSegment, corners);
            }

            rowStart = rowEnd;
        }
        return dugPoints;
    }

    public long countByRayCastOnRowRange(int row, int endRow, Set<Segment> verticalSegments, Set<Segment> horizontalSegments, List<Point> corners) {
        Set<Point> cornersOnRow = corners.stream().filter(p -> p.row == row).collect(Collectors.toSet());
        Set<Segment> segmentsOnRow = horizontalSegments.stream().filter(s -> s.rowMin == row || s.rowMax == row).collect(Collectors.toSet());

        Set<Segment> traversedSegments = verticalSegments.stream().filter(s -> s.rowMin < row && s.rowMax > row).collect(Collectors.toSet());

        // All the points that delimit the row space,
        // either because they are corner of the polygon or because the ray intersect a vertical segment in the middle of it
        Set<Point> pointToAnalyzeOnRow = traversedSegments.stream().map(s -> {
            int col = s.points.stream().findFirst().orElseThrow().col;
            return new Point(row, col);
        }).collect(Collectors.toSet());
        pointToAnalyzeOnRow.addAll(cornersOnRow);


        // Col intervals for which the ray intersect a meaningful segment (the ones that needs to be count for raycasting)
        List<RaycastFlipSegment> flipSegments = getFlipSegments(row, verticalSegments, horizontalSegments);

        List<Point> sortedTraversedPoints = new ArrayList<>(pointToAnalyzeOnRow.stream().toList());
        sortedTraversedPoints.sort(Comparator.comparingInt(a -> a.col));

        System.out.println("Row: " + row + " - " + sortedTraversedPoints + " - " + flipSegments);

        long dugPoints = 0;
        int colStart = Integer.MIN_VALUE;
        for (Point p: sortedTraversedPoints) {
            int colEnd = p.col - 1;

            // Pick the first point in the interval as representative for the whole interval (other one are guarantee not to be on polygon segments)
            Point selectedPoint = new Point(row, colStart);

            long numberOfTraversedSegments = flipSegments.stream().filter(s -> selectedPoint.col < s.startCol).count();

            boolean pointBelongsToSegment = segmentsOnRow.stream().anyMatch(s -> {
                Point p1 = s.points.stream().findFirst().orElseThrow();
                Point p2 = s.points.stream().skip(1).findFirst().orElseThrow();
                int minCol = Math.min(p1.col, p2.col);
                int maxCol = Math.max(p1.col, p2.col);
                return minCol <= selectedPoint.col && maxCol >= selectedPoint.col;
            });

            if (!pointBelongsToSegment && numberOfTraversedSegments % 2 != 0) {
                dugPoints += (long) (colEnd - colStart + 1) * (endRow - row + 1);
            }

            colStart = p.col + 1;
        }

        return dugPoints;
    }

    private List<RaycastFlipSegment> getFlipSegments(int row, Set<Segment> verticalSegments, Set<Segment> horizontalSegments) {
        List<RaycastFlipSegment> segmentTraversed = new ArrayList<>(verticalSegments.stream().filter(s -> s.rowMin < row && s.rowMax > row).toList().stream().map(
                s -> new RaycastFlipSegment(s.points.stream().findFirst().orElseThrow().col, s.points.stream().findFirst().orElseThrow().col)
        ).toList());

        List<Segment> segmentsWithPointOnRow = horizontalSegments.stream().filter(s -> s.rowMin == row || s.rowMax == row).toList();
        for (Segment horizontalSegment: segmentsWithPointOnRow) {
            Point p1 = horizontalSegment.points.stream().findFirst().orElseThrow();
            Point p2 = horizontalSegment.points.stream().skip(1).findFirst().orElseThrow();
            List<Segment> correspondingVerticalSegments = verticalSegments.stream().filter(s -> s.points.stream().anyMatch(p -> p.equals(p1) || p.equals(p2))).toList();
            int minRow = Math.min(correspondingVerticalSegments.get(0).rowMin, correspondingVerticalSegments.get(1).rowMin);
            int maxRow = Math.max(correspondingVerticalSegments.get(0).rowMax, correspondingVerticalSegments.get(1).rowMax);
            if (minRow < row && maxRow > row) {
                segmentTraversed.add(new RaycastFlipSegment(Math.min(p1.col, p2.col), Math.max(p1.col, p2.col)));
            }
        }

        segmentTraversed.sort(Comparator.comparingInt(a -> a.startCol));

        return segmentTraversed;
    }

    private void getStuffV1(List<DigPlan> plans, Set<Point> thrench, List<Point> corners, Set<Segment> segments, Map<Integer, List<Integer>> pointsOnRows) {
        Point current = new Point(0, 0);
        addToMap(pointsOnRows, current.row, current.col);
        thrench.add(current);
        corners.add(current);

        for (DigPlan plan: plans) {
            int dr = 0;
            int dc = 0;
            switch (plan.direction) {
                case 'R' -> dc = 1;
                case 'L' -> dc = -1;
                case 'U' -> dr = -1;
                case 'D' -> dr = 1;
            }

            Point finalPoint = new Point(current.row + dr * plan.length, current.col + dc * plan.length);
            addToMap(pointsOnRows, finalPoint.row, finalPoint.col);
            thrench.add(finalPoint);

            corners.add(finalPoint);
            segments.add(new Segment(Set.of(current, finalPoint), Math.min(current.row, finalPoint.row), Math.max(current.row, finalPoint.row)));

            current = finalPoint;
        }
    }

    private void addToMap(Map<Integer, List<Integer>> map, int key, int value) {
        List<Integer> list = map.getOrDefault(key, new ArrayList<>());
        list.add(value);
        map.put(key, list);
    }

    public List<DigPlan> parseInput(List<String> input) {
        List<DigPlan> plans = new ArrayList<>();
        for (String value: input) {
            String[] data = value.split(" ");
            plans.add(new DigPlan(data[0].charAt(0), Integer.valueOf(data[1], 10), data[2]));
        }

        return plans;
    }

    public List<DigPlan> parseInput2(List<String> input) {
        List<DigPlan> plans = new ArrayList<>();
        for (String value: input) {
            String data = value.split(" ")[2];
            String hexString = "0" + data.substring(2, data.length() - 2);
            int directionInt = Integer.valueOf(data.substring(data.length() -2, data.length() - 1), 10);

            char[] directions = new char[]{'R', 'D', 'L', 'U'};

            plans.add(new DigPlan(directions[directionInt], Integer.valueOf(hexString, 16), data));
        }

        return plans;
    }

    private long inversePrick(long b, long A) {
        return A + 1 - b / 2;
    }

    private long slicker(List<Point> corners) {
        double total = 0;
        for (int i = 0; i < corners.size(); i++) {
            int j = (i + 1) % corners.size();
            Point pi = corners.get(i);
            Point pj = corners.get(j);

            total += (pi.col * pj.row)
                - (pj.col * pi.row);
        }
        total = total / 2;
        System.out.println(total);
        return Math.round(total);
    }

}
