use std::collections::{HashMap, HashSet};
use num::abs;
use crate::utils::geometry::{Point};

pub fn solve1(codes: Vec<String>) -> u64 {
    solve_with_robots(codes, 3)
}

fn type_on_pad(code: &String, pad_navigation: &HashMap<Point<char>, Vec<String>>) -> String {
    let mut current_char = 'A';
    let mut code_to_insert = String::new();

    for char in code.chars() {
        // Assuming it is enough to get the first one (there is only one anyway due to the heuristic implemented below)
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

    // Magic heuristic I am not really understanding
    // One explanation is that it makes sure that in case of multiple movements the furthest one is selected, then it is a shortes path to go back to A
    // Not sure why ^ is preferred over > as they are all at distance 1 from A, but it was giving shorted paths on part 2
    if symbol2 == "<" {
        let left_movements: Vec<String> = valid_movements.iter().filter(|v| v.starts_with("<")).map(|v| v.clone()).collect();
        if !left_movements.is_empty() {
            return left_movements;
        }
    }

    if symbol1 == "v" {
        let down_movements: Vec<String> = valid_movements.iter().filter(|v| v.starts_with("v")).map(|v| v.clone()).collect();
        if !down_movements.is_empty() {
            return down_movements;
        }
    }

    if symbol1 == "^" {
        let up_movements: Vec<String> = valid_movements.iter().filter(|v| v.starts_with("^")).map(|v| v.clone()).collect();
        if !up_movements.is_empty() {
            return up_movements;
        }
    }


    vec![valid_movements[0].clone()]
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
    let mut movements: HashSet<String> = HashSet::new();
    movements.insert(c1.repeat(r1 as usize) + c2.repeat(r2 as usize).as_str());
    movements.insert(c2.repeat(r2 as usize) + c1.repeat(r1 as usize).as_str());
    movements.into_iter().collect()
}

pub fn solve2(codes: Vec<String>) -> u64 {
    solve_with_robots(codes, 26)
}

pub fn solve_with_robots(codes: Vec<String>, robots: usize) -> u64 {
    let door_pad_navigation = get_pad_navigation(&get_door_pad(), Point{x: 4, y: 3});
    let robot_pad_navigation = get_pad_navigation(&get_robot_pad(), Point{x:2, y: 3});

    let mut score = 0;

    let mut memoized: HashMap<Step, u64> = HashMap::new();
    for code in codes {
        let step = Step{code: code.clone(), robot: 0};
        let min_length = solve_recursive(step, robots, &door_pad_navigation, &robot_pad_navigation, &mut memoized);

        let mut door_numbers = code.clone();
        door_numbers.pop();
        let value: u64 = door_numbers.parse().unwrap();

        score += value * min_length
    }

    score
}

#[derive(Debug, Clone, PartialEq, Eq, Hash)]
struct Step {
    code: String,
    robot: usize,
}

fn solve_recursive(step: Step, max_robots: usize, door_pad_navigation: &HashMap<Point<char>, Vec<String>>, robot_pad_navigation: &HashMap<Point<char>, Vec<String>>, memoized: &mut HashMap<Step, u64>) -> u64 {
    if step.robot == max_robots {
        return step.code.len() as u64;
    }

    if memoized.contains_key(&step) {
        return *memoized.get(&step).unwrap();
    }

    let pad = if step.robot == 0 { door_pad_navigation } else { robot_pad_navigation };
    let mut new_code = type_on_pad(&step.code.clone(), pad);

    new_code = new_code.replace("A<", "A <");
    new_code = new_code.replace("A>", "A >");
    new_code = new_code.replace("A^", "A ^");
    new_code = new_code.replace("Av", "A v");

    let inner_codes = new_code.split(" ").collect::<Vec<&str>>();

    let mut result = 0;
    for code in inner_codes {
        // println!("Typing: {} on Robot: {}", code, step.robot + 1);
        let new_step = Step{code: String::from(code), robot: step.robot + 1};
        result += solve_recursive(new_step, max_robots, door_pad_navigation, robot_pad_navigation, memoized);
    }

    memoized.insert(step, result);

    result
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

    #[test]
    pub fn test_case_2() -> TestResult {
        let lines = read_aoc_input_lines(2024, "day21-test")?;
        let result = solve2(lines);
        assert_eq!(result, 154115708116294);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let lines = read_aoc_input_lines(2024, "day21")?;
        let result = solve1(lines);
        assert_eq!(result, 174124);
        Ok(())
    }


    #[test]
    pub fn test_solution_2() -> TestResult {
        let lines = read_aoc_input_lines(2024, "day21")?;
        let result = solve2(lines);
        assert_eq!(result, 216668579770346);
        Ok(())
    }
}