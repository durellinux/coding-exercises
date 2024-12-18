use std::collections::HashSet;
use crate::utils::matrix::valid_point;
use crate::utils::types::Point;

pub struct Warehouse {
    pub walls: HashSet<Point<i32>>,
    pub boxes: HashSet<Point<i32>>,
    pub box_width: i32,
    pub map_size: Point<i32>,
}

pub fn solve1(mut map: Vec<Vec<char>>, commands: Vec<char>) -> u64 {
    let mut robot_position = find_robot_position(&map);
    map[robot_position.x][robot_position.y] = '.';

    for c in commands {
        let direction = get_direction(c);
        do_move(&mut map, &mut robot_position, direction);
    }

    evaluate(&map)
}

fn get_direction(command: char) -> Point<i32> {
    if command == '<' {
        Point{x: 0, y: -1}
    } else if command == '^' {
        Point{x: -1, y: 0}
    } else if command == '>' {
        Point{x: 0, y: 1}
    } else {
        Point{x: 1, y: 0}
    }
}

fn evaluate(map: &Vec<Vec<char>>) -> u64 {
    let mut result = 0;

    for x in 0..map.len() {
        for y in 0..map[0].len() {
            if map[x][y] == 'O' {
                result += 100 * x as u64 + y as u64;
            }
        }
    }

    result
}

fn do_move(map: &mut Vec<Vec<char>>, robot: &mut Point<usize>, direction: Point<i32>) {
    let new_point = Point{x: robot.x as i32 + direction.x, y: robot.y as i32 + direction.y};
    if !valid_point(&new_point, map) {
        return
    }

    let new_cell = map[new_point.x as usize][new_point.y as usize];
    if new_cell == '#' {
        return
    }
    if new_cell == '.' {
        robot.x = new_point.x as usize;
        robot.y = new_point.y as usize;
        return
    }

    let first_free = find_first_free_in_direction(map, &new_point, &direction);
    if first_free.is_none() {
        return
    }

    let free_position = first_free.unwrap();
    map[free_position.x as usize][free_position.y as usize] = 'O';
    map[new_point.x as usize][new_point.y as usize] = '.';
    robot.x = new_point.x as usize;
    robot.y = new_point.y as usize;
}

fn find_first_free_in_direction(map: &Vec<Vec<char>>, start: &Point<i32>, direction: &Point<i32>) -> Option<Point<i32>> {
    let mut cur_x = start.x;
    let mut cur_y = start.y;
    while map[cur_x as usize][cur_y as usize] == 'O' {
        cur_x += direction.x;
        cur_y += direction.y;
    }

    if map[cur_x as usize][cur_y as usize] == '.' { Some(Point{x: cur_x, y: cur_y}) } else { None }
}

fn find_robot_position(map: &Vec<Vec<char>>) -> Point<usize> {
    for x in 0..map.len() {
        for y in 0..map[0].len() {
            if map[x][y] == '@' {
                return Point {x, y};
            }
        }
    }

    Point {x: map.len(), y: 0}
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::string_parsing::as_char_list;
    use crate::utils::types::{GenericError, TestResult};

    fn parse_input(name: &str) -> Result<(Vec<Vec<char>>, Vec<char>), GenericError> {
        let mut map: Vec<Vec<char>> = vec![];
        let mut commands: Vec<char> = vec![];

        let mut parse_map = true;
        let lines = read_aoc_input_lines(2024, name)?;
        for line in lines {
            if line.trim_end().is_empty() {
                parse_map = false;
                continue;
            }

            if parse_map {
                map.push(as_char_list(line.as_str()));
            } else {
                commands.extend(as_char_list(line.as_str()));
            }
        }

        Ok((map, commands))
    }

    #[test]
    pub fn test_case_1() -> TestResult {
        let (map, commands) = parse_input("day15-test")?;
        let result = solve1(map, commands);
        assert_eq!(result, 10092);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let (map, commands) = parse_input("day15")?;
        let result = solve1(map, commands);
        assert_eq!(result, 1492518);
        Ok(())
    }
}