use std::collections::{HashMap, HashSet, VecDeque};
use crate::utils::navigation_utils::{get_4_directions, navigate_in_matrix};
use crate::utils::geometry::{cartesian_distance_usize, Point};

#[derive(Copy, Clone, Hash, Eq, PartialEq)]
pub struct Path {
    pub point: Point<usize>,
    pub time: u64,
}

pub fn solve1(mut map: Vec<Vec<char>>, min_save: i64) -> u64 {
    let track = find_track(&mut map);
    find_cheat(&map, &track, min_save)
}

pub fn solve2(mut map: Vec<Vec<char>>, min_save: i64) -> u64 {
    let track = find_track(&mut map);
    find_cheat_2(&map, &track, min_save, 20)
}

fn find_track(map: &mut Vec<Vec<char>>) -> HashMap<Point<usize>, u64> {
    let mut track: HashMap<Point<usize>, u64> = HashMap::new();
    let start = find_position(map, 'S');
    let end = find_position(map, 'E');

    map[start.x][start.y] = '.';
    map[end.x][end.y] = '.';

    let directions = get_4_directions();
    let mut visited: HashSet<Point<usize>> = HashSet::new();
    let mut to_visit: VecDeque<Path> = VecDeque::new();

    visited.insert(start);
    to_visit.push_back(Path{point: start, time: 0});

    while !to_visit.is_empty() {
        let current = to_visit.pop_front().unwrap();
        track.insert(current.point, current.time);

        if current.point == end {
            break;
        }

        let neighbors = navigate_in_matrix(&current.point, &directions, map);
        for point in neighbors {
            if !visited.contains(&point) && map[point.x][point.y] != '#' {
                visited.insert(point);
                to_visit.push_back(Path{point, time: current.time + 1});
            }
        }
    }

    track
}

fn find_cheat_2(map: &Vec<Vec<char>>, track: &HashMap<Point<usize>, u64>, min_save: i64, max_cheat_duration: i64) -> u64 {
    let mut good_cheats = 0;

    for position1 in track {
        for position2 in track {
            if position2.1 > position1.1 {
                let cheat_duration = cartesian_distance_usize(*position1.0, *position2.0);
                let saved_time = *position2.1 as i64 - *position1.1 as i64 - cheat_duration;
                if cheat_duration <= max_cheat_duration && saved_time >= min_save {
                    good_cheats += 1;
                }
            }
        }
    }

    good_cheats
}

fn find_cheat(map: &Vec<Vec<char>>, track: &HashMap<Point<usize>, u64>, min_save: i64) -> u64 {
    let mut good_cheats = 0;
    let directions = get_4_directions();

    for path in track {
        let neighbors = navigate_in_matrix(path.0, &directions, map);
        for point in neighbors {
            if map[point.x][point.y] == '#' {
                let cheats = navigate_in_matrix(&point, &directions, map);
                for cheat in cheats {
                    if map[cheat.x][cheat.y] == '.' {
                        let time_at_path = *path.1 as i64;
                        let time_at_cheat = *track.get(&cheat).unwrap() as i64;
                        let saved_time = time_at_cheat - time_at_path - 2;
                        if saved_time >= min_save {
                            good_cheats += 1;
                        }
                    }
                }
            }
        }
    }

    good_cheats
}

fn find_position(map: &Vec<Vec<char>>, value: char) -> Point<usize> {
    for x in 0..map.len() {
        for y in 0..map[0].len() {
            if map[x][y] == value {
                return Point{x, y};
            }
        }
    }

    Point{x: map.len(), y: map[0].len()}
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_aoc_input_mat_char;
    use crate::utils::geometry::TestResult;
    use super::*;

    #[test]
    pub fn test_case_1() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day20-test")?;
        let result = solve1(map, 10);
        assert_eq!(result, 10);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day20")?;
        let result = solve1(map, 100);
        assert_eq!(result, 1406);
        Ok(())
    }

    #[test]
    pub fn test_case_2() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day20-test")?;
        let result = solve2(map, 68);
        assert_eq!(result, 55);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day20")?;
        let result = solve2(map, 100);
        assert_eq!(result, 1006101);
        Ok(())
    }

}