use std::collections::{HashSet, VecDeque};
use crate::utils::matrix::{ valid_coordinates_i64, valid_point};
use crate::utils::types::PointI32;

pub fn solve1(mat: Vec<Vec<i32>>) -> i32 {
    let mut result = 0;

    for x in 0..mat.len() {
        for y in 0..mat[0].len() {
            if mat[x][y] == 0 {
                result += hike(&mat, PointI32 {x: x as isize, y: y as isize})
            }
        }
    }

    result
}

fn hike(trail: &Vec<Vec<i32>>, start: PointI32<isize>) -> i32 {
    let mut peaks: HashSet<PointI32<isize>> = HashSet::new();
    let directions: Vec<PointI32<isize>> = vec![
        PointI32{x: -1, y: 0},
        PointI32{x: 1, y: 0},
        PointI32{x: 0, y: -1},
        PointI32{x: 0, y: 1}
    ];

    let mut visited: HashSet<PointI32<isize>> = HashSet::new();
    let mut to_visit: VecDeque<PointI32<isize>> = VecDeque::new();

    to_visit.push_back(start);
    while !to_visit.is_empty() {
        let current = to_visit.pop_front().unwrap();
        visited.insert(current);
        let value = trail[current.x as usize][current.y as usize];

        if value == 9 {
            peaks.insert(current);
        }

        for direction in &directions {
            let new_point = PointI32 {x: current.x + direction.x, y: current.y + direction.y};
            if valid_coordinates_i64(new_point.x as i64, new_point.y as i64, &trail) {
                let new_value = trail[new_point.x as usize][new_point.y as usize];
                if new_value == value + 1 && !visited.contains(&new_point) {
                    to_visit.push_back(new_point);
                }
            }
        }
    }

    peaks.len() as i32
}


pub fn solve2(mat: Vec<Vec<i32>>) -> i32 {
    let mut result = 0;

    for x in 0..mat.len() {
        for y in 0..mat[0].len() {
            if mat[x][y] == 0 {
                result += hike_with_rating(&mat, PointI32 {x: x as isize, y: y as isize})
            }
        }
    }

    result
}


fn hike_with_rating(trail: &Vec<Vec<i32>>, start: PointI32<isize>) -> i32 {
    let mut peaks: Vec<PointI32<isize>> = vec![];
    let directions: Vec<PointI32<isize>> = vec![
        PointI32{x: -1, y: 0},
        PointI32{x: 1, y: 0},
        PointI32{x: 0, y: -1},
        PointI32{x: 0, y: 1}
    ];

    let mut to_visit: VecDeque<PointI32<isize>> = VecDeque::new();

    to_visit.push_back(start);
    while !to_visit.is_empty() {
        let current = to_visit.pop_front().unwrap();
        let value = trail[current.x as usize][current.y as usize];

        if value == 9 {
            peaks.push(current);
        }

        for direction in &directions {
            let new_point = PointI32 {x: current.x + direction.x, y: current.y + direction.y};
            if valid_coordinates_i64(new_point.x as i64, new_point.y as i64, &trail) {
                let new_value = trail[new_point.x as usize][new_point.y as usize];
                if new_value == value + 1 {
                    to_visit.push_back(new_point);
                }
            }
        }
    }

    peaks.len() as i32
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_aoc_input_mat_number;
    use crate::utils::types::GenericError;
    use super::*;

    #[test]
    pub fn test_case_1() -> Result<(), GenericError> {
        let mat: Vec<Vec<i32>> = read_aoc_input_mat_number(2024, "day10-test")?;
        let result = solve1(mat);
        assert_eq!(result, 36);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> Result<(), GenericError> {
        let mat: Vec<Vec<i32>> = read_aoc_input_mat_number(2024, "day10")?;
        let result = solve1(mat);
        assert_eq!(result, 786);
        Ok(())
    }

    #[test]
    pub fn test_case_2() -> Result<(), GenericError> {
        let mat: Vec<Vec<i32>> = read_aoc_input_mat_number(2024, "day10-test")?;
        let result = solve2(mat);
        assert_eq!(result, 81);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> Result<(), GenericError> {
        let mat: Vec<Vec<i32>> = read_aoc_input_mat_number(2024, "day10")?;
        let result = solve2(mat);
        assert_eq!(result, 1722);
        Ok(())
    }
}