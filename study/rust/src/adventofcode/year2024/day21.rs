use std::collections::{HashMap, HashSet, VecDeque};
use num::abs;
use crate::utils::geometry::{Point};
use crate::utils::navigation_utils::{get_4_directions, get_bottom_direction, get_left_direction, get_right_direction, get_top_direction, navigate_in_sparse_matrix};

pub fn solve1(codes: Vec<String>) -> u64 {
    let mut result = 0;
    let door_pad_navigation = get_pad_navigation(&get_door_pad(), Point{x: 4, y: 3});
    let robot_pad_navigation = get_pad_navigation(&get_robot_pad(), Point{x:2, y: 3});

    for code in codes {
        result += solve_code(code, &door_pad_navigation, &robot_pad_navigation);
    }

    result
}

fn solve_code(code: String, door_pad_navigation: &HashMap<Point<char>, Vec<String>>, robot_pad_navigation: &HashMap<Point<char>, Vec<String>>) -> u64 {
    let robot_1_code = type_on_pad(&code, door_pad_navigation);
    let robot_2_code = type_on_pad(&robot_1_code, robot_pad_navigation);
    let robot_3_code = type_on_pad(&robot_2_code, robot_pad_navigation);

    let mut door_numbers = code.clone();
    door_numbers.pop();
    let value: u64 = door_numbers.parse().unwrap();

    println!("{}", robot_3_code);
    println!("{}", robot_2_code);
    println!("{}", robot_1_code);
    println!("{}", code);

    println!("{}: {} -> {} -> {}", code, robot_3_code, robot_3_code.len(), value * robot_3_code.len() as u64);

    value * robot_3_code.len() as u64
}

fn type_on_pad(code: &String, pad_navigation: &HashMap<Point<char>, Vec<String>>) -> String {
    let mut current_char = 'A';
    let mut code_to_insert = String::new();

    for char in code.chars() {
        // Assuming it is enough to get the first one
        code_to_insert = code_to_insert + pad_navigation.get(&Point{x: current_char, y: char}).unwrap().get(0).unwrap();
        code_to_insert.push('A');
        current_char = char;
    }

    code_to_insert
}

fn get_door_pad() -> HashMap<char, Point<usize>> {
    let mut positions: HashMap<char, Point<usize>> = HashMap::new();
    positions.insert('7', Point{x: 0, y: 0});
    positions.insert('8', Point{x: 0, y: 1});
    positions.insert('9', Point{x: 0, y: 2});
    positions.insert('4', Point{x: 1, y: 0});
    positions.insert('5', Point{x: 1, y: 1});
    positions.insert('6', Point{x: 1, y: 2});
    positions.insert('1', Point{x: 2, y: 0});
    positions.insert('2', Point{x: 2, y: 1});
    positions.insert('3', Point{x: 2, y: 2});
    positions.insert('0', Point{x: 3, y: 1});
    positions.insert('A', Point{x: 3, y: 2});

    positions
}

fn get_robot_pad() -> HashMap<char, Point<usize>> {
    let mut positions: HashMap<char, Point<usize>> = HashMap::new();
    positions.insert('^', Point{x: 0, y: 1});
    positions.insert('A', Point{x: 0, y: 2});
    positions.insert('<', Point{x: 1, y: 0});
    positions.insert('v', Point{x: 1, y: 1});
    positions.insert('>', Point{x: 1, y: 2});

    positions
}

fn get_pad_navigation_all(pad: &HashMap<char, Point<usize>>, size: Point<usize>) -> HashMap<Point<char>, Vec<String>> {
    let mut navigation: HashMap<Point<char>, Vec<String>> = HashMap::new();
    let mut reverse_pad: HashMap<Point<usize>, char> = HashMap::new();

    for p in pad {
        reverse_pad.insert(*p.1, *p.0);
    }

    for p1 in pad.iter() {
        for p2 in pad.iter() {
            let start = pad.get(p1.0).unwrap();
            let end = pad.get(p2.0).unwrap();
            let path = navigate_in_pad_all(&reverse_pad, *start, *end, size);
            let point: Point<char> = Point{x: *p1.0, y: *p2.0};
            navigation.insert(point, path);
        }
    }

    navigation
}

fn get_pad_navigation(pad: &HashMap<char, Point<usize>>, size: Point<usize>) -> HashMap<Point<char>, Vec<String>> {
    let mut navigation: HashMap<Point<char>, Vec<String>> = HashMap::new();
    let mut reverse_pad: HashMap<Point<usize>, char> = HashMap::new();

    for p in pad {
        reverse_pad.insert(*p.1, *p.0);
    }

    for p1 in pad.iter() {
        for p2 in pad.iter() {
            let start = pad.get(p1.0).unwrap();
            let end = pad.get(p2.0).unwrap();
            let path = navigate_in_pad(&reverse_pad, *start, *end, size);
            let point: Point<char> = Point{x: *p1.0, y: *p2.0};
            navigation.insert(point, path);
        }
    }

    navigation
}

fn navigate_in_pad_all(reverse_pad: &HashMap<Point<usize>, char>, from: Point<usize>, to: Point<usize>, size: Point<usize>) -> Vec<String> {
    struct Step {
        point: Point<usize>,
        value: String,
    }

    let dx = to.x as isize - from.x as isize;
    let dy = to.y as isize - from.y as isize;
    let mut directions: Vec<Point<isize>> = vec![];

    if dx == 0 && dy == 1 {
        directions.push(get_right_direction());
    } else {
        directions.push(get_left_direction());
    }
    if dx == 1 && dy == 0 {
        directions.push(get_bottom_direction());
    } else {
        directions.push(get_top_direction());
    }

    let mut solutions: Vec<String> = vec![];

    let mut to_visit: VecDeque<Step> = VecDeque::new();
    to_visit.push_back(Step{point: from, value: String::from("")});

    while !to_visit.is_empty() {
        let current = to_visit.pop_front().unwrap();
        let current_point = current.point;

        if current_point == to {
            solutions.push(current.value);
            continue;
        }

        let neighbor = navigate_in_sparse_matrix(&current_point, &directions, size);
        for point in neighbor {
            if reverse_pad.contains_key(&point) {
                let dx = point.x as isize - current_point.x as isize;
                let dy = point.y as isize - current_point.y as isize;
                let mut new_value = current.value.clone();

                if dx == 0 && dy == 1 {
                    new_value.push('>')
                } else if dx == 0 && dy == -1 {
                    new_value.push('<')
                } else if dx == 1 && dy == 0 {
                    new_value.push('v')
                } else if dx == -1 && dy == 0 {
                    new_value.push('^')
                }

                to_visit.push_back(Step { point, value: new_value });
            }
        }
    }

    solutions
}

fn navigate_in_pad(reverse_pad: &HashMap<Point<usize>, char>, from: Point<usize>, to: Point<usize>, size: Point<usize>) -> Vec<String> {
    let dx = to.x as isize - from.x as isize;
    let dy = to.y as isize - from.y as isize;

    let symbol1 = if dx >= 0 { "v" } else { "^" };
    let symbol2 = if dy >= 0 { ">" } else { "<" };

    let movements: Vec<String> = generate_movements(symbol1, abs(dx), symbol2, abs(dy));

    let mut valid_movements: Vec<String> = vec![];
    for movement in movements {
        if is_valid_movement(from, movement.clone(), reverse_pad) {
            valid_movements.push(movement);
        }
    }

    valid_movements
}

fn is_valid_movement(from: Point<usize>, movement: String, reverse_pad: &HashMap<Point<usize>, char>) -> bool {
    let mut current_position = from;
    let steps = movement.chars();
    for s in steps {
        if s == '>' {
            current_position = Point{x: current_position.x, y: current_position.y + 1};
        } else if s == '<' {
            current_position = Point{x: current_position.x, y: current_position.y - 1};
        } else if s == 'v' {
            current_position = Point{x: current_position.x + 1, y: current_position.y};
        } else {
            current_position = Point{x: current_position.x - 1, y: current_position.y};
        }

        if !reverse_pad.contains_key(&current_position) {
            return false;
        }
    }

    true
}

fn generate_movements(c1: &str, r1: isize, c2: &str, r2: isize) -> Vec<String> {
    vec![
        c1.repeat(r1 as usize) + c2.repeat(r2 as usize).as_str(),
        c2.repeat(r2 as usize) + c1.repeat(r1 as usize).as_str()
    ]
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::geometry::TestResult;
    use super::*;

    #[test]
    pub fn test_case_1() -> TestResult {
        let lines = read_aoc_input_lines(2024, "day21-test")?;
        let result = solve1(lines);
        assert_eq!(result, 126384);
        Ok(())
    }
}