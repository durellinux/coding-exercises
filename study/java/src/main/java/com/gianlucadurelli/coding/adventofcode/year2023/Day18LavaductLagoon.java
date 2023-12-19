package com.gianlucadurelli.coding.adventofcode.year2023;

import java.util.*;
import java.util.stream.Collectors;

public class Day18LavaductLagoon {

    private record DigPlan(char direction, int length, String color) {}
    private record Point(int row, int col) {}
    private record Segment(Set<Point> points, int rowMin, int rowMax) {}

    public long solve(List<DigPlan> plans) {
//        return solveWithConnectedComponents(plans);

        return solveWithPolygons(plans);
    }

    private long solveWithPolygons(List<DigPlan> plans) {
        Set<Point> thrench = new HashSet<>();
        Map<Integer, List<Integer>> pointsOnRows = new HashMap<>();
        Map<Integer, List<Integer>> pointsOnCols = new HashMap<>();
        List<Point> corners = new ArrayList<>();
        Set<Segment> segments = new HashSet<>();

        getStuffV1(plans, thrench, corners, segments, pointsOnRows, pointsOnCols);

        System.out.println(pointsOnRows.size());
        System.out.println(pointsOnCols.size());
        System.out.println(corners.size());

//        long area = slicker(corners);
//        return inversePrick(corners.size(), Math.abs(area));

        return pointInsidePolygon(thrench, segments, pointsOnRows, pointsOnCols, corners);
    }

    private long pointInsidePolygon(Set<Point> thrench, Set<Segment> segments, Map<Integer, List<Integer>> pointsOnRows, Map<Integer, List<Integer>> pointsOnCols, List<Point> corners) {
        int minR = thrench.stream().map(p -> p.row).min(Integer::compareTo).orElse(0);
        int maxR = thrench.stream().map(p -> p.row).max(Integer::compareTo).orElse(0);
        int minC = thrench.stream().map(p -> p.col).min(Integer::compareTo).orElse(0);
        int maxC = thrench.stream().map(p -> p.col).max(Integer::compareTo).orElse(0);

        Queue<Integer> sortedRows = new LinkedList<>(pointsOnRows.keySet().stream().sorted().toList());
        Set<Point> allDug = new HashSet<>();

        long dugPoints = computeOnDirection(minR, maxR, minC, maxC, sortedRows, allDug, segments, thrench, corners);

        for (Segment s: segments) {
            int minCol = s.points.stream().map(p -> p.col).min(Integer::compareTo).orElse(0);
            int maxCol = s.points.stream().map(p -> p.col).max(Integer::compareTo).orElse(0);

            dugPoints += ((s.rowMax - s.rowMin + 1) * (maxCol - minCol + 1));

            for (int r = s.rowMin; r <= s.rowMax; r++) {
                for(int c = minCol; c <= maxCol; c++) {
                    allDug.add(new Point(r, c));
                }
            }
        }

        printMatrix(thrench, allDug, minR, maxR, minC, maxC);

        return dugPoints - thrench.size();
    }

    private long computeOnDirection(int minR, int maxR, int minC, int maxC, Queue<Integer> sortedRows, Set<Point> allDug, Set<Segment> segments, Set<Point> thrench, List<Point> corners) {
        long dugPoints = 0;
        Set<Segment> verticalSegments = segments.stream().filter(s -> s.rowMin != s.rowMax).collect(Collectors.toSet());
        Set<Segment> horizontalSegment = segments.stream().filter(s -> s.rowMin == s.rowMax).collect(Collectors.toSet());

        long oldPoints = dugPoints;
        int rowStart = minR;
        while(!sortedRows.isEmpty()) {
            int rowEnd = sortedRows.poll();
            System.out.println("Analyzing: " + rowStart + " -> " + rowEnd);

            Set<Point> dug = new HashSet<>();
            // Analyze rowStart
            System.out.println("Running: " + rowStart + " -> " + rowStart);
            dugPoints += rowsInPolygon(rowStart, rowStart, minC, maxC, dug, verticalSegments, horizontalSegment, thrench, corners);
            allDug.addAll(dug);
            System.out.println("Points: " + (dugPoints - oldPoints) + " -> " + dugPoints);
//            printMatrix(thrench, allDug, minR, maxR, minC, maxC);

            if (rowStart +1 <= rowEnd - 1) {
                oldPoints = dugPoints;
                dug = new HashSet<>();
                // Analyze rowStart + 1
                System.out.println("Running: " + (rowStart + 1) + " -> " + (rowEnd - 1));
                dugPoints += rowsInPolygon(rowStart + 1, rowEnd - 1, minC, maxC, dug, verticalSegments, horizontalSegment, thrench, corners);
                allDug.addAll(dug);
                System.out.println("Points: " + (dugPoints - oldPoints) + " -> " + dugPoints);
//                printMatrix(thrench, allDug, minR, maxR, minC, maxC);
            }

            rowStart = rowEnd;
        }

//        System.out.println("Running: " + (rowStart) + " -> " + (rowStart));
//        oldPoints = dugPoints;
//        Set<Point> dug = new HashSet<>();
//        dugPoints += dugPointsOnRowFinal(rowStart, rowStart, pointsOnRows.get(rowStart), minC, dug, verticalSegments, horizontalSegment, thrench);
//        allDug.addAll(dug);
//        System.out.println("Points: " + (dugPoints - oldPoints) + " -> " + dugPoints);


//        System.out.println(allDug.size());
//        long verticalSegmentLenght = verticalSegments.stream().map(s -> s.rowMax - s.rowMin + 1).reduce(Integer::sum).orElse(0);
//        System.out.println(verticalSegmentLenght);
        return dugPoints;
    }

    record SmartSegment(Point start, Point middleStart, Point middleEnd, Point end) {}

    public long rowsInPolygon(int row, int endRow, int minC, int maxC, Set<Point> dug, Set<Segment> verticalSegment, Set<Segment> horizontalSegment, Set<Point> thrench, List<Point> corners) {
        boolean inPolygon = false;
        Queue<Integer> sortedCols;

        List<Integer> intersectedColumnsSegments = verticalSegment.stream().filter(s -> s.rowMin == row || s.rowMax == endRow).map(s -> {
            Point p = s.points.stream().findFirst().orElseThrow();
            return p.col;
        }).sorted().toList();

        Set<Segment> intersectedSegments = verticalSegment.stream().filter(s -> s.rowMin < row && s.rowMax > endRow).collect(Collectors.toSet());

        Set<Segment> intersectedSegmentsFake = new HashSet<>();
        for (int i = 0; i < intersectedColumnsSegments.size() - 1; i+=2) {
            Point middleStart = new Point(row, intersectedColumnsSegments.get(i));
            Point middleEnd = new Point(row, intersectedColumnsSegments.get(i + 1));

            Point start = verticalSegment.stream().filter(s -> s.points.stream().anyMatch(p -> p.equals(middleStart))).findFirst().orElseThrow().points.stream().filter(p -> !p.equals(middleStart)).findFirst().orElseThrow();
            Point end = verticalSegment.stream().filter(s -> s.points.stream().anyMatch(p -> p.equals(middleEnd))).findFirst().orElseThrow().points.stream().filter(p -> !p.equals(middleEnd)).findFirst().orElseThrow();

            int minRow = Math.min(start.row, end.row);
            minRow = Math.min(minRow, row);

            int maxRow = Math.max(start.row, end.row);
            maxRow = Math.max(maxRow, row);

            Point p1 = new Point(minRow, middleStart.col);
            Point p2 = new Point(maxRow, middleStart.col);
            intersectedSegmentsFake.add(new Segment(Set.of(p1, p2), Math.min(p1.row, p2.row), Math.max(p1.row, p2.row)));
        }

        System.out.println("Intersected Segments: " + intersectedSegments);
        System.out.println("Intersected Segments Fake: " + intersectedSegmentsFake);

        Set<Segment> allIntersectedSegments = new HashSet<>(intersectedSegments);
        allIntersectedSegments.addAll(intersectedSegmentsFake);

        List<Integer> intersectedColumns = verticalSegment.stream().filter(s -> s.rowMin <= row && s.rowMax >= row).map(
            s -> s.points.stream().findFirst().orElseThrow().col
        ).sorted().toList();
        sortedCols = new LinkedList<>(new HashSet<>(intersectedColumns));

        long dugPoints = 0;
        System.out.println("Intersected cols: " + intersectedColumns);
        while(!sortedCols.isEmpty()) {
            int colStart = sortedCols.poll() + 1;
            int colEnd = sortedCols.peek() != null ? sortedCols.peek() - 1 : maxC + 2;

            boolean isOnHorizontalSegment = horizontalSegment.stream()
                .anyMatch(s -> {
                    int minCol = s.points.stream().map(p -> p.col).min(Integer::compareTo).orElseThrow();
                    int maxCol = s.points.stream().map(p -> p.col).max(Integer::compareTo).orElseThrow();
                    return s.rowMin == row && minCol < colStart && colStart < maxCol;
                });

            long intersectRight = allIntersectedSegments.stream().filter(s -> {
                Point p1 = s.points.stream().findFirst().orElseThrow();
                return s.rowMin < row && s.rowMax > endRow && p1.col >= colStart;
            }).count();

            System.out.println("Analyzing: " + new Point(row, colStart) + " -> intersecting: " + intersectRight + " / onSegment: " + isOnHorizontalSegment);

            if (intersectRight % 2 == 1 && !isOnHorizontalSegment) {
                dugPoints += (long) (colEnd - colStart + 1) * (endRow - row + 1);
                for (int c = colStart; c <= colEnd; c++) {
                    for (int r = row; r <= endRow; r++) {
                        Point p = new Point(r, c);
                        if (dug.contains(p)) {
                            System.out.println("Duplicated (for segment): " + p);
                        }
                        dug.add(p);
                    }
                }
            }

//            System.out.println("Analyzing: " + colStart + " -> " + colEnd + " = " + inPolygon);
//
//            Point startPoint = new Point(row, colStart);
//            Point endPoint = new Point(row, colEnd);
//
//            if (!thrench.contains(startPoint) && thrench.contains(endPoint)) {
//                startPoint = new Point(row, colEnd);
//                endPoint = new Point(row, sortedCols.peek());
//            }
//
//            Segment possibleSegment = new Segment(Set.of(startPoint, endPoint), row, row);
//            if (horizontalSegment.contains(possibleSegment)) {
//                int finalColStart = colStart;
//                Set<Segment> verticals = verticalSegment.stream().filter(s -> s.points.stream().anyMatch(p -> p.row == row && (p.col == finalColStart || p.col == colEnd))).collect(Collectors.toSet());
//                long countVerticalTop = verticals.stream().filter(s -> s.points.stream().anyMatch(p -> p.row < row)).count();
////                System.out.println(row + " - " + colStart + " - " + colEnd + " " + possibleSegment + " " + verticals + " " + countVerticalTop);
//                if (countVerticalTop == 1) {
//                    inPolygon = !inPolygon;
//                }
//            } else {
//                if (inPolygon) {
//                    dugPoints += (long) (colEnd - colStart - 1) * (endRow - row + 1);
//                    for (int c = colStart + 1; c <= colEnd - 1; c++) {
//                        for (int r = row; r <= endRow; r++) {
//                            Point p = new Point(r, c);
//                            if (dug.contains(p)) {
//                                System.out.println("Duplicated (for segment): " + p);
//                            }
//                            dug.add(p);
//                        }
//                    }
//                }
//
//                inPolygon = !inPolygon;
//            }
//
//            colStart = colEnd;
        }

        return dugPoints;
    }

    /**
     * Return true if the given point is contained inside the boundary.
     * See: http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
     * @param test The point to check
     * @return true if the point is inside the boundary, false otherwise
     *
     */
    public boolean contains(Point test, List<Point> corners) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = corners.size() - 1; i < corners.size(); j = i++) {
            if ((corners.get(i).row > test.row) != (corners.get(j).row > test.row) &&
                (test.col < (corners.get(j).col - corners.get(i).col) * (test.row - corners.get(i).col) / (corners.get(j).col - corners.get(i).col) + corners.get(i).row)) {
                result = !result;
            }
        }
        return result;
    }

    private void getStuffV1(List<DigPlan> plans, Set<Point> thrench, List<Point> corners, Set<Segment> segments, Map<Integer, List<Integer>> pointsOnRows, Map<Integer, List<Integer>> pointsOnCols) {
        Point current = new Point(0, 0);
        addToMap(pointsOnRows, current.row, current.col);
        addToMap(pointsOnCols, current.col, current.row);
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
            addToMap(pointsOnCols, finalPoint.col, finalPoint.row);
            thrench.add(finalPoint);

            corners.add(finalPoint);
            segments.add(new Segment(Set.of(current, finalPoint), Math.min(current.row, finalPoint.row), Math.max(current.row, finalPoint.row)));

            current = finalPoint;
        }

//        segments.add(new Segment(Set.of(current, corners.get(0)), Math.min(current.row, corners.get(0).row), Math.max(current.row, corners.get(0).row)));
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

    private void printMatrix(Set<Point> thrench, Set<Point> allDug, int minR, int maxR, int minC, int maxC) {
//        System.out.println(allDug.size());
//        System.out.println(minR);
//        System.out.println(minC);

        char[][] matrix = new char[maxR + Math.abs(minR) + 1][maxC + Math.abs(minC) + 1];
        for (int r = minR; r <= maxR; r++) {
            for (int c = minC; c <= maxC; c++) {
                Point p = new Point(r, c);
                char v = '.';
                if (thrench.contains(p) && !allDug.contains(p)) {
                    v = '!';
                } else if (thrench.contains(p) && allDug.contains(p)) {
                    v = '#';
                } else if (allDug.contains(p)) {
                    v = 'O';
                }

                matrix[r + Math.abs(minR)][c + Math.abs(minC)] = v;
            }
        }

        for (int r = minR; r <= maxR; r++) {
            for (int c = minC; c <= maxC; c++) {
                System.out.print(matrix[r + Math.abs(minR)][c + Math.abs(minC)]);
            }
            System.out.println();
        }
    }

    public long dugPointsOnRow(int row, int endRow, List<Integer> columnsOnRow, int minC, Set<Point> dug, Set<Segment> verticalSegment, Set<Segment> horizontalSegment) {
        boolean inPolygon = false;
        Queue<Integer> sortedCols;

        List<Integer> intersectedColumns = verticalSegment.stream().filter(s -> s.rowMin <= row && s.rowMax > endRow).map(s -> {
            Point p = s.points.stream().findFirst().orElseThrow();
            return p.col;
        }).sorted().toList();
        sortedCols = new LinkedList<>(new HashSet<>(intersectedColumns));

        long dugPoints = 0;
        Integer colStart = null;
        inPolygon = true;
        while(!sortedCols.isEmpty()) {
            int colEnd;
            if (colStart == null) {
                colStart = sortedCols.poll();
                if (sortedCols.isEmpty()) {
                    System.out.println("Exit for abnormal condition");
                    break;
                }
            }
            colEnd = sortedCols.poll();

            if (inPolygon) {
                dugPoints += (long) (colEnd - colStart - 1) * (endRow - row + 1);
                for (int c = colStart + 1; c <= colEnd - 1; c++) {
                    for(int r = row; r <= endRow; r++) {
                        Point p = new Point(r, c);
                        if (dug.contains(p)) {
                            System.out.println("Duplicated (for segment): " + p);
                        }
                        dug.add(p);
                    }
                }
            }

            inPolygon = !inPolygon;
            colStart = colEnd;
        }

        return dugPoints;
    }

    public long dugPointsOnRowWithSegments(int row, int endRow, List<Integer> columnsOnRow, int minC, Set<Point> dug, Set<Segment> verticalSegment, Set<Segment> horizontalSegment) {
        boolean inPolygon = false;
        Queue<Integer> sortedCols;

        List<Integer> intersectedColumns = verticalSegment.stream().filter(s -> s.rowMin <= row && s.rowMax >= endRow).map(s -> {
            Point p = s.points.stream().findFirst().orElseThrow();
            return p.col;
        }).sorted().toList();
        sortedCols = new LinkedList<>(new HashSet<>(intersectedColumns));

        long dugPoints = 0;
        Integer colStart = null;
        while(!sortedCols.isEmpty()) {
            int colEnd;
            if (colStart == null) {
                colStart = sortedCols.poll();
                if (sortedCols.isEmpty()) {
                    System.out.println("Exit for abnormal condition");
                    break;
                }
            }
            colEnd = sortedCols.poll();

            Segment possibleSegment = new Segment(Set.of(new Point(row, colStart), new Point(row, colEnd)), row, row);
            System.out.println(possibleSegment);
            if (horizontalSegment.contains(possibleSegment)) {
                Integer finalColStart = colStart;
                Set<Segment> verticals = verticalSegment.stream().filter(s -> s.points.stream().anyMatch(p -> p.row == row && (p.col == finalColStart || p.col == colEnd))).collect(Collectors.toSet());
                long countVerticalTop = verticals.stream().filter(s -> s.points.stream().anyMatch(p -> p.row < row)).count();
//                System.out.println(row + " - " + colStart + " - " + colEnd + " " + possibleSegment + " " + verticals + " " + countVerticalTop);
                if (countVerticalTop == 1) {
                    inPolygon = !inPolygon;
                }
//
//                inPolygon = !inPolygon;
//                dugPoints += colEnd - colStart - 1;
//                for (int c = colStart + 1; c <= colEnd - 1; c++) {
//                    Point p = new Point(row, c);
//                    if (dug.contains(p)) {
//                        System.out.println("Duplicated (for segment): " + p);
//                    }
//                    dug.add(p);
//                }
            } else if (inPolygon) {
                dugPoints += (long) (colEnd - colStart - 1) * (endRow - row + 1);
                for (int c = colStart + 1; c <= colEnd - 1; c++) {
                    for(int r = row; r <= endRow; r++) {
                        Point p = new Point(r, c);
                        if (dug.contains(p)) {
                            System.out.println("Duplicated (for segment): " + p);
                        }
                        dug.add(p);
                    }
                }
            }

            colStart = colEnd;
        }

        return dugPoints;
    }

    private long solveWithConnectedComponents(List<DigPlan> plans) {
        Point current = new Point(0, 0);
        Set<Point> thrench = new HashSet<>();
        thrench.add(current);
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
            int minR = Math.min(current.row, finalPoint.row);
            int maxR = Math.max(current.row, finalPoint.row);
            int minC = Math.min(current.col, finalPoint.col);
            int maxC = Math.max(current.col, finalPoint.col);

            for (int r = minR; r <= maxR; r++) {
                for (int c = minC; c <= maxC; c++) {
                    thrench.add(new Point(r, c));
                }
            }

            current = finalPoint;
        }

        int minR = thrench.stream().map(p -> p.row).min(Integer::compareTo).orElse(0);
        int maxR = thrench.stream().map(p -> p.row).max(Integer::compareTo).orElse(0);
        int minC = thrench.stream().map(p -> p.col).min(Integer::compareTo).orElse(0);
        int maxC = thrench.stream().map(p -> p.col).max(Integer::compareTo).orElse(0);

        Set<Point> innerPoints = new HashSet<>();
        Set<Point> outerPoints = new HashSet<>();
        for (int r = minR; r <= maxR; r++) {
            for (int c = minC; c <= maxC; c++) {
                Point p = new Point(r, c);
                if (!thrench.contains(p) && !innerPoints.contains(p) && !outerPoints.contains(p)) {
                    Set<Point> connectedPoints = new HashSet<>();
                    boolean isInside = tryDig(p, thrench, minR, maxR, minC, maxC, connectedPoints);
                    if (isInside) {
                        innerPoints.addAll(connectedPoints);
                    } else {
                        outerPoints.addAll(connectedPoints);
                    }
                }
            }
        }

        Set<Point> allPoints = new HashSet<>();
        allPoints.addAll(thrench);
        allPoints.addAll(innerPoints);
        return allPoints.size();
    }

    private boolean tryDig(Point start, Set<Point> thrench, int minR, int maxR, int minC, int maxC, Set<Point> connectedPoints) {
        boolean isInside = true;

        Queue<Point> toVisit = new LinkedList<>();
        toVisit.add(start);
        connectedPoints.add(start);

        while(!toVisit.isEmpty()) {
            Point p = toVisit.poll();

            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int r = p.row + dr;
                    int c = p.col + dc;

                    if (r < minR || r > maxR || c < minC || c > maxC) {
                        isInside = false;
                    } else {
                        Point newPoint = new Point(r, c);
                        if (!thrench.contains(newPoint) && !connectedPoints.contains(newPoint)) {
                            connectedPoints.add(newPoint);
                            toVisit.add(newPoint);
                        }
                    }
                }
            }
        }

        return isInside;
    }

    private void getStuffV2(List<DigPlan> plans, Set<Point> thrench, List<Point> corners, Set<Segment> segments, Map<Integer, List<Integer>> pointsOnRows, Map<Integer, List<Integer>> pointsOnCols) {
        Point current = new Point(0, 0);
        addToMap(pointsOnRows, current.row, current.col);
        addToMap(pointsOnCols, current.col, current.row);
        thrench.add(current);
        corners.add(current);

        int offsetRow = 0;
        int offsetCol = 0;
        int prevOffsetRow = 0;
        int prevOffsetCol = 0;
        char prevPlan = plans.get(0).direction;
        for (DigPlan plan: plans) {
            int dr = 0;
            int dc = 0;
            switch (plan.direction) {
                case 'R' -> dc = 1;
                case 'L' -> dc = -1;
                case 'U' -> dr = -1;
                case 'D' -> dr = 1;
            }

            prevOffsetRow = offsetRow;
            prevOffsetCol = offsetCol;

            if (prevPlan == 'L' && plan.direction == 'U') {
                offsetRow = 0;
                offsetCol = -1;
            } else if (prevPlan == 'L' && plan.direction == 'D') {
                offsetRow = 0;
                offsetCol = -1;
            } else if (prevPlan == 'R' && plan.direction == 'U') {
                offsetRow = 0;
                offsetCol = 1;
            } else if (prevPlan == 'R' && plan.direction == 'D') {
                offsetRow = 0;
                offsetCol = 1;
            }

            if (prevPlan == 'U' && plan.direction == 'L') {
                offsetRow = -1;
                offsetCol = 0;
            } else if (prevPlan == 'U' && plan.direction == 'R') {
                offsetRow = -1;
                offsetCol = 0;
            } else if (prevPlan == 'D' && plan.direction == 'L') {
                offsetRow = 1;
                offsetCol = 0;
            } else if (prevPlan == 'D' && plan.direction == 'R') {
                offsetRow = 1;
                offsetCol = 0;
            }

            Point finalPoint = new Point(current.row + dr * plan.length + offsetRow + prevOffsetRow, current.col + dc * plan.length + offsetCol + prevOffsetCol);
            addToMap(pointsOnRows, finalPoint.row, finalPoint.col);
            addToMap(pointsOnCols, finalPoint.col, finalPoint.row);
            thrench.add(finalPoint);

            corners.add(finalPoint);
            segments.add(new Segment(Set.of(current, finalPoint), Math.min(current.row, finalPoint.row), Math.max(current.row, finalPoint.row)));

            prevPlan = plan.direction;
            current = finalPoint;
        }
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
