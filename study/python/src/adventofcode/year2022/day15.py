import re
from collections import deque
from typing import Optional

from src.adventofcode.year2022.utils2022 import Coordinate

type Sensor = tuple[Coordinate, int]
type Beacons = set[Coordinate]
type Sensors = set[Sensor]

def solve_day15_part1(data: str, target_line: int) -> int:
    beacons, sensors, col_range, row_range = parse_day15(data)

    merged_intervals = solve_day15(sensors, target_line)

    impossible_positions = 0
    for interval in merged_intervals:
        impossible_positions += interval[1] - interval[0] + 1

    for beacon in beacons:
        if beacon[1] == target_line:
            beacon_range = (beacon[0], beacon[0])
            for interval in merged_intervals:
                if do_overlap(interval, beacon_range):
                    impossible_positions -= 1
                    break

    for sensor in sensors:
        if sensor[0][1] == target_line:
            sensor_range = (sensor[0][0], sensor[0][0])
            for interval in merged_intervals:
                if do_overlap(interval, sensor_range):
                    impossible_positions -= 1
                    break

    return impossible_positions

def solve_day15_part2(data: str, max_coord: int) -> int:
    beacons, sensors, col_range, row_range = parse_day15(data)
    col_range = (0, max_coord)

    occupied_positions: set[Coordinate] = set()
    for beacon in beacons:
        occupied_positions.add(beacon)

    for sensor in sensors:
        occupied_positions.add(sensor[0])

    impossible_intervals: dict[int, deque[tuple[int, int]]] = dict()

    for sensor in sensors:
        sensor_position = sensor[0]
        sensor_range = sensor[1]
        min_row_covered = sensor_position[1] - sensor_range
        max_row_covered = sensor_position[1] + sensor_range
        min_row = max(0, min_row_covered)
        max_row = min(max_coord, max_row_covered)
        for row in range(min_row, max_row + 1):
            covered_range: Optional[tuple[int, int]] = covered_range_at_line(sensor, row)
            if covered_range is not None:
                if row not in impossible_intervals:
                    impossible_intervals[row] = deque()
                impossible_intervals[row].append(covered_range)

    for row in impossible_intervals.keys():
        intervals = impossible_intervals[row]
        merged_intervals = merge_intervals(list(intervals))
        if len(merged_intervals) == 2:
            position = (merged_intervals[0][1] + 1, row)
            if position not in occupied_positions:
                return position[0] * 4000000 + position[1]

    return 0

def merge_intervals(intervals: list[tuple[int, int]]) -> list[tuple[int, int]]:
    merged_intervals: list[tuple[int, int]] = []
    intervals.sort(key=lambda interval: interval[0])

    for interval in intervals:
        if len(merged_intervals) == 0:
            merged_intervals.append(interval)
            continue

        last_interval = merged_intervals[-1]
        if not do_overlap(last_interval, interval):
            merged_intervals.append(interval)
        else:
            merged_intervals[-1] = (last_interval[0], max(last_interval[1], interval[1]))

    return merged_intervals

def solve_day15(sensors: Sensors, target_line: int) -> list[tuple[int, int]]:
    impossible_intervals: list[tuple[int, int]] = []

    for sensor in sensors:
        covered_range: Optional[tuple[int, int]] = covered_range_at_line(sensor, target_line)
        if covered_range is not None:
            impossible_intervals.append(covered_range)

    impossible_intervals.sort(key=lambda interval: interval[0])
    merged_intervals: list[tuple[int, int]] = []

    for interval in impossible_intervals:
        if len(merged_intervals) == 0:
            merged_intervals.append(interval)
            continue

        last_interval = merged_intervals[-1]
        if not do_overlap(last_interval, interval):
            merged_intervals.append(interval)
        else:
            merged_intervals[-1] = (last_interval[0], max(last_interval[1], interval[1]))

    return merged_intervals

def do_overlap(range1: tuple[int, int], range2: tuple[int, int]) -> bool:
    return range1[0] <= range2[1] and range1[1] >= range2[0]


def covered_range_at_line(sensor: Sensor, target_line: int) -> Optional[tuple[int, int]]:
    sensor_position = sensor[0]
    sensor_range = sensor[1]
    intercept_position = (sensor_position[0], target_line)
    distance_at_intercept = manhattan_distance(sensor_position, intercept_position)

    if distance_at_intercept > sensor_range:
        return None

    lateral_cover = sensor_range - distance_at_intercept

    return sensor_position[0] - lateral_cover, sensor_position[0] + lateral_cover


def sensor_range_intersects_line(sensor: Sensor, target_line: int) -> bool:
    sensor_position = sensor[0]
    sensor_range = sensor[1]
    intercept_position = (sensor_position[0], target_line)

    return manhattan_distance(sensor_position, intercept_position) <= sensor_range

def parse_day15(data: str) -> tuple[Beacons, Sensors, Coordinate, Coordinate]:
    regex = re.compile(r'Sensor at x=(-{0,1}\d+), y=(-{0,1}\d+): closest beacon is at x=(-{0,1}\d+), y=(-{0,1}\d+)')
    lines = data.splitlines()

    beacons: Beacons = set()
    sensors: Sensors = set()
    min_r, min_c, max_r, max_c = None, None, None, None

    for line in lines:
        match = regex.match(line)
        if match:
            sensor: Coordinate = (int(match.group(1)), int(match.group(2)))
            beacon: Coordinate = (int(match.group(3)), int(match.group(4)))
            radius: int = manhattan_distance(beacon, sensor)

            beacons.add(beacon)
            sensors.add((sensor, radius))

            c_min = min(sensor[0], beacon[0])
            c_max = max(sensor[0], beacon[0])
            r_min = min(sensor[1], beacon[1])
            r_max = max(sensor[1], beacon[1])

            if min_c is None or c_min < min_c:
                min_c = c_min
            if max_c is None or c_max > max_c:
                max_c = c_max
            if min_r is None or r_min < min_r:
                min_r = r_min
            if max_r is None or r_max > max_r:
                max_r = r_max

    return beacons, sensors, (min_c, max_c), (min_r, max_r)

def manhattan_distance(p1: Coordinate, p2: Coordinate) -> int:
    return abs(p1[0] - p2[0]) + abs(p1[1] - p2[1])