use std::collections::{HashSet, VecDeque};
use crate::utils::matrix::valid_point;
use crate::utils::geometry::Point;

pub struct Warehouse {
    pub walls: HashSet<Point<i32>>,
    pub boxes: HashSet<Point<i32>>,
    pub box_width: i32,
    pub map_size: Point<i32>,
}

pub fn solve1(map: Vec<Vec<char>>, commands: Vec<char>) -> u64 {
    solve_warehouse(map, commands, false)
}


pub fn solve2(map: Vec<Vec<char>>, commands: Vec<char>) -> u64 {
    solve_warehouse(map, commands, true)
}

pub fn solve_warehouse(mut map: Vec<Vec<char>>, commands: Vec<char>, scale_up: bool) -> u64 {
    let robot_position = find_robot_position(&map);
    map[robot_position.x][robot_position.y] = '.';

    let mut warehouse = create_warehouse(&map, scale_up);

    let mut robot_position_i32 = if scale_up == false {Point{x: robot_position.x as i32, y: robot_position.y as i32}} else {Point{x: robot_position.x as i32, y: robot_position.y as i32 * 2}};

    for c in commands {
        let direction = get_direction(c);
        do_move_warehouse(&mut warehouse, &mut robot_position_i32, direction);
    }

    print_warehouse(&warehouse, scale_up, &robot_position_i32);
    evaluate_warehouse(&warehouse)
}



fn create_warehouse(map: &Vec<Vec<char>>, scale_up: bool) -> Warehouse {
    let box_width: i32 = if scale_up { 2 } else { 1 };
    let max_x: i32 = map.len() as i32;
    let max_y: i32 = map[0].len() as i32 * box_width;
    let mut warehouse: Warehouse = Warehouse {walls: HashSet::new(), boxes: HashSet::new(), box_width, map_size: Point {x: max_x, y: max_y}};

    for x in 0..map.len() {
        for y in 0..map[0].len() {
            let symbol = map[x][y];
            if symbol == '#' {
                if scale_up {
                    warehouse.walls.insert(Point{x: x as i32, y: y as i32 * 2});
                    warehouse.walls.insert(Point{x: x as i32, y: y as i32 * 2 + 1});
                } else {
                    warehouse.walls.insert(Point{x: x as i32, y: y as i32});
                }
            } else if symbol == 'O' {
                if scale_up {
                    warehouse.boxes.insert(Point{x: x as i32, y: y as i32 * 2});
                } else {
                    warehouse.boxes.insert(Point{x: x as i32, y: y as i32});
                }
            }
        }
    }

    warehouse
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

fn evaluate_warehouse(warehouse: &Warehouse) -> u64 {
    let mut result: u64 = 0;

    for box_position in warehouse.boxes.clone() {
        result += 100 * box_position.x as u64 + box_position.y as u64;
    }

    result
}


fn do_move_warehouse(warehouse: &mut Warehouse, robot: &mut Point<i32>, direction: Point<i32>) {
    let maybe_new_point = Point{x: robot.x as i32 + direction.x, y: robot.y as i32 + direction.y};
    if maybe_new_point.x < 0 || maybe_new_point.x >= warehouse.map_size.x || maybe_new_point.y < 0 || maybe_new_point.y >= warehouse.map_size.y {
        return
    }

    let new_point = Point {x: maybe_new_point.x, y: maybe_new_point.y};
    let mut new_cell = '.';

    if warehouse.walls.contains(&new_point) {
        new_cell = '#';
    } else if warehouse.boxes.contains(&new_point) || ( warehouse.box_width > 1 && new_point.y >= 1 && warehouse.boxes.contains(&Point {x: maybe_new_point.x, y: maybe_new_point.y - 1})) {
        new_cell = 'O';
    }

    if new_cell == '#' {
        return
    }
    if new_cell == '.' {
        robot.x = new_point.x;
        robot.y = new_point.y;
        return
    }

    let first_box = if warehouse.boxes.contains(&new_point) { new_point } else { Point {x: maybe_new_point.x, y: maybe_new_point.y - 1} };

    let movable_boxes: HashSet<Point<i32>> = find_movable_boxes(first_box, warehouse, direction);
    if movable_boxes.len() > 0 {
        let new_positions: Vec<Point<i32>> = movable_boxes.iter().map(|b| Point {x: b.x + direction.x, y: b.y + direction.y}).collect();

        for box_position in movable_boxes {
            warehouse.boxes.remove(&box_position);
        }

        for box_position in new_positions {
            warehouse.boxes.insert(box_position);
        }

        robot.x = new_point.x;
        robot.y = new_point.y;
    }

}

fn find_movable_boxes(initial: Point<i32>, warehouse: &mut Warehouse, direction: Point<i32>) -> HashSet<Point<i32>> {
    let mut boxes: HashSet<Point<i32>> = HashSet::new();
    let mut to_visit: VecDeque<Point<i32>> = VecDeque::new();
    boxes.insert(initial);

    to_visit.push_back(initial);
    while !to_visit.is_empty() {
        let current = to_visit.pop_front().unwrap();
        let mut possible_walls: Vec<Point<i32>> = vec![];
        let mut possible_boxes: Vec<Point<i32>> = vec![];

        if direction.x == 0 {
            if warehouse.box_width == 1 {
                possible_walls.push(Point{x: current.x, y: current.y + direction.y});
                possible_boxes.push(Point{x: current.x, y: current.y + direction.y});
            } else if warehouse.box_width == 2 {
                possible_walls.push(Point{x: current.x, y: current.y + direction.y});
                possible_walls.push(Point{x: current.x, y: current.y + direction.y + 1});
                possible_boxes.push(Point{x: current.x, y: current.y + direction.y * 2});
            }
        } else if direction.y == 0 {

            if warehouse.box_width == 1 {
                possible_walls.push(Point{x: current.x + direction.x, y: current.y});
                possible_boxes.push(Point{x: current.x + direction.x, y: current.y});
            } else if warehouse.box_width == 2 {
                possible_walls.push(Point{x: current.x + direction.x, y: current.y});
                possible_walls.push(Point{x: current.x + direction.x, y: current.y + 1});

                possible_boxes.push(Point{x: current.x + direction.x, y: current.y - 1});
                possible_boxes.push(Point{x: current.x + direction.x, y: current.y});
                possible_boxes.push(Point{x: current.x + direction.x, y: current.y + 1});
            }
        }

        for possible_wall in possible_walls {
            if warehouse.walls.contains(&possible_wall) {
                return HashSet::new();
            }
        }

        for possible_box in possible_boxes {
            if warehouse.boxes.contains(&possible_box) && !boxes.contains(&possible_box) {
                boxes.insert(possible_box);
                to_visit.push_back(possible_box);
            }
        }
    }

    boxes
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

fn print_warehouse(warehouse: &Warehouse, scale_up: bool, robot_position: &Point<i32>) {
    for x in 0..warehouse.map_size.x {
        for y in 0..warehouse.map_size.y {
            if robot_position.x == x && robot_position.y == y {
                print!("@");
            } else if warehouse.walls.contains(&Point{x, y}) {
                print!("#");
            } else if !scale_up && warehouse.boxes.contains(&Point{x, y}) {
                print!("O");
            } else if scale_up && warehouse.boxes.contains(&Point{x, y}) {
                print!("[");
            } else if scale_up &&  warehouse.boxes.contains(&Point{x, y: y - 1}) {
                print!("]");
            } else {
                print!(".");
            }
        }
        print!("\n");
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::string_parsing::as_char_list;
    use crate::utils::geometry::{GenericError, TestResult};

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

    #[test]
    pub fn test_case_2() -> TestResult {
        let (map, commands) = parse_input("day15-test")?;
        let result = solve2(map, commands);
        assert_eq!(result, 9021);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let (map, commands) = parse_input("day15")?;
        let result = solve2(map, commands);
        assert_eq!(result, 1512860);
        Ok(())
    }
}