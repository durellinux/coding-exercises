use std::collections::{HashMap, HashSet, VecDeque};
use crate::utils::navigation_utils::{get_4_directions, navigate_in_matrix, navigate_in_sparse_matrix};
use crate::utils::types::Point;

#[derive(Copy, Clone, Hash, Eq, PartialEq)]
pub struct Byte {
    location: Point<usize>,
    time: u64,
}

pub fn solve1(bytes: Vec<Byte>, size: Point<usize>, simulation_time: u64) -> u64 {
    let mut corrupted: HashMap<Point<usize>, u64> = HashMap::new();
    for byte in bytes {
        corrupted.insert(byte.location, byte.time);
    }

    navigate(Byte{location: Point{x: 0, y: 0}, time: simulation_time}, Point{x: size.x-1, y: size.y-1}, size, &corrupted)
}

pub fn solve2(bytes: Vec<Byte>, size: Point<usize>) -> Point<usize> {
    let mut corrupted: HashMap<Point<usize>, u64> = HashMap::new();
    for byte in bytes.iter() {
        corrupted.insert(byte.location, byte.time);
    }

    let mut a_time = 0;
    let mut b_time = bytes.len();

    while b_time - a_time > 1 {
        let mid_time = (b_time + a_time)  / 2;
        let result = navigate(Byte{location: Point{x: 0, y: 0}, time: mid_time as u64}, Point{x: size.x-1, y: size.y-1}, size, &corrupted);
        if result == 0 {
            b_time = mid_time;
        } else {
            a_time = mid_time;
        }
    }

    let blocking_byte = bytes.get(b_time).unwrap().location;
    Point{x: blocking_byte.y, y: blocking_byte.x}
}

fn navigate(start: Byte, end: Point<usize>, size: Point<usize>, corrupted: &HashMap<Point<usize>, u64>) -> u64 {
    let mut visited: HashSet<Point<usize>> = HashSet::new();
    let mut to_visit: VecDeque<Byte> = VecDeque::new();

    let directions = get_4_directions();

    to_visit.push_back(start);
    visited.insert(start.location);
    while !to_visit.is_empty() {
        let current = to_visit.pop_front().unwrap();

        if current.location == end {
            return current.time - start.time;
        }

        let next_points = navigate_in_sparse_matrix(&current.location, &directions, size);
        let time = current.time + 1;
        for point in next_points {
            if !visited.contains(&point) && (!corrupted.contains_key(&point) || *corrupted.get(&point).unwrap() > start.time) {
                to_visit.push_back(Byte{location: point, time});
                visited.insert(point);
            }
        }
    }

    0
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::types::{GenericError, TestResult};
    use super::*;

    fn parse_input(name: &str) -> Result<Vec<Byte>, GenericError> {
        let mut bytes: Vec<Byte> = vec![];

        let lines = read_aoc_input_lines(2024, name)?;
        for t in 0..lines.len() {
            let line = lines.get(t).unwrap();
            let values: Vec<usize> = line.split(",").map(|v| v.parse().unwrap()).collect();
            bytes.push(Byte{location: Point{x: values[1], y: values[0]}, time: t as u64});
        }

        Ok(bytes)
    }

    #[test]
    pub fn test_case_1() -> TestResult {
        let btyes: Vec<Byte> = parse_input("day18-test")?;
        let result = solve1(btyes, Point{x: 7, y: 7}, 12);
        assert_eq!(result, 22);
        Ok(())
    }


    #[test]
    pub fn test_solution_1() -> TestResult {
        let btyes: Vec<Byte> = parse_input("day18")?;
        let result = solve1(btyes, Point{x: 71, y: 71}, 1024);
        assert_eq!(result, 340);
        Ok(())
    }

    #[test]
    pub fn test_case_2() -> TestResult {
        let btyes: Vec<Byte> = parse_input("day18-test")?;
        let result = solve2(btyes, Point{x: 7, y: 7});
        assert_eq!(result, Point{x: 6, y: 1});
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let btyes: Vec<Byte> = parse_input("day18")?;
        let result = solve2(btyes, Point{x: 71, y: 71});
        assert_eq!(result, Point{x: 34, y: 32});
        Ok(())
    }
}