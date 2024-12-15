use std::collections::{HashSet, VecDeque};
use std::ops::Deref;
use crate::utils::matrix::print_char_matrix;
use crate::utils::navigation_utils::{get_4_directions, get_bottom_direction, get_direction_in_matrix, get_left_direction, get_right_direction, get_top_direction, navigate_in_matrix};
use crate::utils::types::{Point, Segment};

pub fn solve1(mut garden: Vec<Vec<char>>) -> u64 {
    let mut price = 0;

    for x in 0..garden.len() {
        for y in 0..garden[0].len() {
            if garden[x][y] != '.' {
                // let crop = garden[x][y];
                let (area, perimeter, sides) = region_price(&mut garden, Point {x, y});
                // println!("Crop: {} - A: {}, P: {}, S: {}", crop, area, perimeter, sides);
                price += area * perimeter;
            }
        }
    }

    price
}

pub fn solve2(mut garden: Vec<Vec<char>>) -> u64 {
    let mut price = 0;
    for x in 0..garden.len() {
        for y in 0..garden[0].len() {
            if garden[x][y] != '.' {
                // let crop = garden[x][y];
                let (area, perimeter, sides) = region_price(&mut garden, Point {x, y});
                // println!("{}-{} - {} - A: {} S: {}", x, y, crop, area, sides);
                price += area * sides;
            }
        }
    }

    price
}

fn region_price(garden: &mut Vec<Vec<char>>, start: Point<usize>) -> (u64, u64, u64) {
    let mut area: u64 = 0;
    let mut perimeter: u64 = 0;
    let crop = garden[start.x][start.y];
    let directions = get_4_directions();

    let mut visited: HashSet<Point<usize>> = HashSet::new();
    let mut visited_perimeter: HashSet<Point<usize>> = HashSet::new();
    let mut to_visit: VecDeque<Point<usize>> = VecDeque::new();

    to_visit.push_back(start);
    visited.insert(start);
    while !to_visit.is_empty() {
        let current = to_visit.pop_front().unwrap();
        area += 1;

        let connected_same_crop = connected_with_same_crop(&current, &directions, garden, crop);
        perimeter += 4 - connected_same_crop;
        if perimeter > 0 {
            visited_perimeter.insert(current);
        }

        let nearby = navigate_in_matrix(&current, &directions, garden);
        let nearby_crops: Vec<&Point<usize>> = nearby.iter().filter(|p| garden[p.x][p.y] == crop).collect();
        for neighbor in nearby_crops {
            if !visited.contains(neighbor) {
                to_visit.push_back(*neighbor);
                visited.insert(*neighbor);
            }
        }
    }

    let mut sides = count_sides(&visited_perimeter, garden, crop);

    for p in visited {
        garden[p.x][p.y] = '.'
    }

    (area, perimeter, sides)
}

fn connected_with_same_crop(current: &Point<usize>, directions: &Vec<Point<isize>>, garden: &Vec<Vec<char>>, crop: char) -> u64 {
    let connected = navigate_in_matrix(&current, directions, garden);
    let connected_same_crop: Vec<&Point<usize>> = connected.iter().filter(|p| garden[p.x][p.y] == crop).collect();
    connected_same_crop.len() as u64
}

fn count_sides(perimeter_crops: &HashSet<Point<usize>>, garden: &Vec<Vec<char>>, crop: char) -> u64 {
    let mut sides = 0;
    let mut points: HashSet<Point<usize>> = HashSet::new();
    let mut vertical_segments_1: HashSet<Segment<usize>> = HashSet::new();
    let mut vertical_segments_2: HashSet<Segment<usize>> = HashSet::new();
    let mut horizontal_segments_1: HashSet<Segment<usize>> = HashSet::new();
    let mut horizontal_segments_2: HashSet<Segment<usize>> = HashSet::new();

    for p in perimeter_crops.iter() {
        let left = get_direction_in_matrix(p, &get_left_direction(), garden);
        let right = get_direction_in_matrix(p, &get_right_direction(), garden);
        let top = get_direction_in_matrix(p, &get_top_direction(), garden);
        let bottom = get_direction_in_matrix(p, &get_bottom_direction(), garden);

        if should_create_segment(&left, crop, garden) {
            let p1 = Point {x: p.x, y: p.y};
            let p2 = Point {x: p.x + 1, y: p.y};

            points.insert(p1);
            points.insert(p2);
            vertical_segments_1.insert(Segment {p1, p2});
        }

        if should_create_segment(&right, crop, garden) {
            let p1 = Point {x: p.x, y: p.y + 1};
            let p2 = Point {x: p.x + 1, y: p.y + 1};

            points.insert(p1);
            points.insert(p2);
            vertical_segments_2.insert(Segment {p1, p2});
        }

        if should_create_segment(&top, crop, garden) {
            let p1 = Point {x: p.x, y: p.y};
            let p2 = Point {x: p.x, y: p.y + 1};

            points.insert(p1);
            points.insert(p2);
            horizontal_segments_1.insert(Segment {p1, p2});
        }

        if should_create_segment(&bottom, crop, garden) {
            let p1 = Point {x: p.x + 1, y: p.y};
            let p2 = Point {x: p.x + 1, y: p.y + 1};

            points.insert(p1);
            points.insert(p2);
            horizontal_segments_2.insert(Segment {p1, p2});
        }
    }

    sides += get_vertical_segments(&mut vertical_segments_1, crop);
    sides += get_vertical_segments(&mut vertical_segments_2, crop);
    sides += get_horizontal_segments(&mut horizontal_segments_1, crop);
    sides += get_horizontal_segments(&mut horizontal_segments_2, crop);

    sides
}

fn get_vertical_segments(vertical_segments: &mut HashSet<Segment<usize>>, crop: char) -> u64 {
    let mut sides = 0;

    while !vertical_segments.is_empty() {
        let cur_s = vertical_segments.iter().next().unwrap().clone();
        let mut to_remove: Vec<Segment<usize>> = vec![];
        {
            let mut segments_at_level: Vec<Segment<usize>> = vertical_segments.iter().filter(|p| p.p1.y == cur_s.p1.y).map(|s| *s).collect();
            segments_at_level.sort_by(|s1, s2| s1.p1.x.cmp(&s2.p1.x));

            let mut segments = 1;
            let mut current = segments_at_level[0];
            for p in segments_at_level.iter().skip(1) {
                if p.p1.x != current.p2.x {
                    segments += 1
                }

                current = *p
            }
            sides += segments;

            // println!("Crop {} y: {}: {}", crop, cur_p.y, segments);

            for p in segments_at_level {
                to_remove.push(p);
            }
        }

        for p in to_remove {
            vertical_segments.remove(&p);
        }
    }

    sides
}

fn get_horizontal_segments(horizontal_segments: &mut HashSet<Segment<usize>>, crop: char) -> u64 {
    let mut sides = 0;

    while !horizontal_segments.is_empty() {
        let cur_s = horizontal_segments.iter().next().unwrap().clone();
        let mut to_remove: Vec<Segment<usize>> = vec![];
        {
            let mut segments_at_level: Vec<Segment<usize>> = horizontal_segments.iter().filter(|p| p.p1.x == cur_s.p1.x).map(|s| *s).collect();
            segments_at_level.sort_by(|s1, s2| s1.p1.y.cmp(&s2.p1.y));

            let mut segments = 1;
            let mut current = segments_at_level[0];
            for p in segments_at_level.iter().skip(1) {
                if p.p1.y != current.p2.y {
                    segments += 1
                }

                current = *p
            }
            sides += segments;

            // println!("Crop {} y: {}: {}", crop, cur_p.y, segments);

            for p in segments_at_level {
                to_remove.push(p);
            }
        }

        for p in to_remove {
            horizontal_segments.remove(&p);
        }
    }

    sides
}

fn should_create_segment(neighbor: &Option<Point<usize>>, crop: char, garden: &Vec<Vec<char>>) -> bool {
    neighbor.is_none() || (garden[neighbor.unwrap().x][neighbor.unwrap().y] != crop)
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::utils::file_utils::read_aoc_input_mat_char;
    use crate::utils::types::TestResult;

    #[test]
    pub fn test_case_1() -> TestResult {
        let garden = read_aoc_input_mat_char(2024, "day12-test")?;
        let result = solve1(garden);
        assert_eq!(result, 1930);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let garden = read_aoc_input_mat_char(2024, "day12")?;
        let result = solve1(garden);
        assert_eq!(result, 1446042);
        Ok(())
    }

    #[test]
    pub fn test_case_2() -> TestResult {
        let garden = read_aoc_input_mat_char(2024, "day12-test")?;
        let result = solve2(garden);
        assert_eq!(result, 1206);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let garden = read_aoc_input_mat_char(2024, "day12")?;
        let result = solve2(garden);
        assert_eq!(result, 902742);
        Ok(())
    }

}