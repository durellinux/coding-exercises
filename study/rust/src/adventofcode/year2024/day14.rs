use crate::utils::geometry::Point;

pub struct Robot {
    p: Point<i32>,
    v: Point<i32>,
}

pub fn solve1(robots: Vec<Robot>, size: Point<i32>, steps: u32) -> i32 {
    let mut final_robots: Vec<Robot> = vec![];
    for robot in robots {
        let x = robot.p.x;
        let y = robot.p.y;
        let vx = robot.v.x;
        let vy = robot.v.y;

        let new_position = move_robot(x, y, vx, vy, size, steps);

        final_robots.push(Robot {p: new_position, v: robot.v});
    }

    evaluate(final_robots, size)
}

fn evaluate(robots: Vec<Robot>, size: Point<i32>) -> i32 {
    let mut q1 = 0;
    let mut q2 = 0;
    let mut q3 = 0;
    let mut q4 = 0;

    let xh = size.x / 2;
    let yh = size.y / 2;

    for robot in robots {
        let x = robot.p.x;
        let y = robot.p.y;

        let dx = x / xh;

        let dy = y / yh;

        // println!("({},{}) - {},{} - {},{} ", x, y, dx, dy, rx, ry);

        if x != xh && y != yh {
            if dx == 0 && dy == 0 {
                q1 += 1;
            } else if dx >= 1 && dy == 0 {
                q2 += 1;
            } else if dx == 0 && dy >= 1 {
                q3 += 1;
            } else if dx >= 1 && dy >= 1 {
                q4 += 1;
            }
        }
    }

    q1 * q2 * q3 * q4
}

fn move_robot(x: i32, y: i32, vx: i32, vy: i32, size: Point<i32>, steps: u32) -> Point<i32> {
    let mut xf = (x + vx * steps as i32) % size.x;
    xf = (xf + size.x) % size.x;

    let mut yf = (y + vy * steps as i32) % size.y;
    yf = (yf + size.y) % size.y;

    Point{x: xf, y: yf}
}

// TODO: Write proper algorithm, as this one I have no idea why it works
pub fn solve2(robots: Vec<Robot>, size: Point<i32>) -> u32 {
    let loop_lenght = 10403;
    let mut max_heuristic = 1e30 as i32;
    let mut max_heuristic_at = 0;

    for i in 0..=loop_lenght {
        let mut final_robots = vec![];

        for robot in &robots {
            let new_position = move_robot(robot.p.x, robot.p.y, robot.v.x, robot.v.y, size, i);
            final_robots.push(Robot {p: new_position, v: robot.v});
        }

        let heuristic = evaluate(final_robots, size);
        if heuristic < max_heuristic {
            max_heuristic = heuristic;
            max_heuristic_at = i;
        }
    }


    max_heuristic_at
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::string_parsing::as_number_list_with_split;
    use crate::utils::geometry::{GenericError, TestResult};
    use super::*;

    fn parse_input(name: &str) -> Result<Vec<Robot>, GenericError> {
        let mut robots: Vec<Robot> = vec![];
        let lines = read_aoc_input_lines(2024, name)?;
        for line in lines {
            let parts: Vec<&str> = line.split(" ").collect();
            let p: Vec<i32> = as_number_list_with_split(parts[0].split("=").collect::<Vec<_>>()[1], ",");
            let v: Vec<i32> = as_number_list_with_split(parts[1].split("=").collect::<Vec<_>>()[1], ",");
            robots.push(Robot{p: Point{x: p[0], y:p[1]}, v: Point{x: v[0], y:v[1]}});
        }

        Ok(robots)
    }

    #[test]
    pub fn test_case_1() -> TestResult {
        let robots = parse_input("day14-test")?;
        let result = solve1(robots, Point{x: 11, y: 7}, 100);
        assert_eq!(result, 12);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let robots = parse_input("day14")?;
        let result = solve1(robots, Point{x: 101, y: 103}, 100);
        assert_eq!(result, 221616000);
        Ok(())
    }


    #[test]
    pub fn test_solution_2() -> TestResult {
        let robots = parse_input("day14")?;
        let result = solve2(robots, Point{x: 101, y: 103});
        assert_eq!(result, 7572);
        Ok(())
    }
}