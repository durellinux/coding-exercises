use std::io::{self, Write};
use std::cmp::Ordering;
use std::collections::{BinaryHeap, HashMap, HashSet, VecDeque};
use crate::utils::matrix::valid_point;
use crate::utils::navigation_utils::{get_cardinal_point_direction};
use crate::utils::types::Point;

#[derive(Copy, Clone, Eq, PartialEq, Hash)]
pub struct Position {
    x: isize,
    y: isize,
    direction: char,
}

#[derive(Copy, Clone, Eq, PartialEq)]
pub struct ScoredPosition {
    position: Position,
    cost: u64,
}

impl Ord for Position {
    fn cmp(&self, other: &Self) -> Ordering {
        self.x.cmp(&other.x)
            .then_with(|| self.y.cmp(&other.y))
            .then_with(|| self.direction.cmp(&other.direction))
    }
}

impl PartialOrd for Position {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl Ord for ScoredPosition {
    fn cmp(&self, other: &Self) -> Ordering {
        other.cost.cmp(&self.cost)
            .then_with(|| self.position.x.cmp(&other.position.x))
    }
}

// `PartialOrd` needs to be implemented as well.
impl PartialOrd for ScoredPosition {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

pub fn solve1(map: Vec<Vec<char>>) -> u64 {
    let (shortest_score, cache) = dijkstra_to_end(&map, None);
    shortest_score
}

fn dijkstra_to_end(map: &Vec<Vec<char>>, max_score: Option<u64>) -> (u64, HashMap<Position, u64>) {
    let start_position = find_position(map, 'S');

    let mut cache: HashMap<Position, u64> = HashMap::new();
    let mut heap: BinaryHeap<ScoredPosition> = BinaryHeap::new();
    cache.entry(start_position).insert_entry(0);
    heap.push(ScoredPosition {position: start_position, cost: 0});

    let mut attempts = 0;

    while !heap.is_empty() {
        let current = heap.pop().unwrap();

        // if attempts > 100 {
        //     return 0;
        // }
        // attempts += 1;
        // println!("Checking {},{} - Cost: {}", current.position.x, current.position.y, current.cost);

        // Stopping conditions
        if max_score.is_some() && current.cost > max_score.unwrap() {
            return (max_score.unwrap(), cache);
        }
        if max_score.is_none() && map[current.position.x as usize][current.position.y as usize] == 'E' {
            return (current.cost, cache);
        }

        // Rotations
        let rotations = generate_rotations(&current);
        for rotation in rotations {
            if !cache.contains_key(&rotation.position) || *cache.get(&rotation.position).unwrap() > rotation.cost {
                cache.insert(rotation.position, rotation.cost);
                heap.push(rotation);
            }
        }

        // Movement forward
        let forward: ScoredPosition = generate_move_forward(&current);
        if valid_point(&Point{x: forward.position.x as i32, y: forward.position.y as i32}, &map) &&
            map[forward.position.x as usize][forward.position.y as usize] != '#' {
            if !cache.contains_key(&forward.position) || *cache.get(&forward.position).unwrap() > forward.cost {
                cache.insert(forward.position, forward.cost);
                heap.push(forward);
            }
        }
    }

    (max_score.or_else(|| Some(0)).unwrap(), cache)
}

fn generate_rotations(current: &ScoredPosition) -> Vec<ScoredPosition> {
    let mut rotations: Vec<ScoredPosition> = vec![];
    let x = current.position.x;
    let y = current.position.y;
    let cost = current.cost;

    if current.position.direction == 'E' {
        rotations.push(ScoredPosition{ cost: cost + 1000, position: Position{x, y, direction: 'S'}});
        rotations.push(ScoredPosition{ cost: cost + 1000, position: Position{x, y, direction: 'N'}});
        // rotations.push(ScoredPosition{ cost: cost + 2000, position: Position{x, y, direction: 'W'}});
    } else if  current.position.direction == 'S' {
        rotations.push(ScoredPosition{ cost: cost + 1000, position: Position{x, y, direction: 'W'}});
        rotations.push(ScoredPosition{ cost: cost + 1000, position: Position{x, y, direction: 'E'}});
        // rotations.push(ScoredPosition{ cost: cost + 2000, position: Position{x, y, direction: 'N'}});
    } else if  current.position.direction == 'W' {
        rotations.push(ScoredPosition{ cost: cost + 1000, position: Position{x, y, direction: 'S'}});
        rotations.push(ScoredPosition{ cost: cost + 1000, position: Position{x, y, direction: 'N'}});
        // rotations.push(ScoredPosition{ cost: cost + 2000, position: Position{x, y, direction: 'E'}});
    } else {
        rotations.push(ScoredPosition{ cost: cost + 1000, position: Position{x, y, direction: 'W'}});
        rotations.push(ScoredPosition{ cost: cost + 1000, position: Position{x, y, direction: 'E'}});
        // rotations.push(ScoredPosition{ cost: cost + 2000, position: Position{x, y, direction: 'S'}});
    }

    rotations
}

fn generate_move_forward(current: &ScoredPosition) -> ScoredPosition {
    let direction = get_cardinal_point_direction(current.position.direction);
    let new_position: Position = Position{x: current.position.x + direction.x, y: current.position.y + direction.y, direction: current.position.direction};

    ScoredPosition{position: new_position, cost: current.cost + 1}
}

fn find_position(map: &Vec<Vec<char>>, value: char) -> Position {
    for x in 0..map.len() {
        for y in 0..map[0].len() {
            if map[x][y] == value {
                return Position{x: x as isize, y: y as isize, direction: 'E'}
            }
        }
    }

    Position{x: map.len() as isize, y: 0, direction: '+'}
}


pub fn solve2(map: Vec<Vec<char>>) -> u64 {
    let (shortest_score, cache) = dijkstra_to_end(&map, None);
    let start = find_position(&map, 'S');
    let end = find_position(&map, 'E');
    let (_, cache2) = dijkstra_to_end(&map, Some(shortest_score));

    let mut best_spots: HashSet<Point<isize>> = HashSet::new();
    let mut to_visit: VecDeque<ScoredPosition> = VecDeque::new();
    let mut visited: HashSet<Position> = HashSet::new();

    let cardinal_directions: Vec<char> = vec!['N', 'S', 'W', 'E'];
    for direction in cardinal_directions.iter() {
        let end_direction = *cache2.get(&Position{x: end.x, y: end.y, direction: *direction}).or_else(|| Some(&0)).unwrap();
        if end_direction == shortest_score {
            to_visit.push_back(ScoredPosition{position: Position{x: end.x, y: end.y, direction: *direction}, cost: shortest_score});
            visited.insert(Position{x: end.x, y: end.y, direction: *direction});
        }
    }

    best_spots.insert(Point{x: end.x, y: end.y});

    while !to_visit.is_empty() {
        let current = to_visit.pop_front().unwrap();
        best_spots.insert(Point{x: current.position.x, y: current.position.y});

        let mut neighbors: Vec<ScoredPosition> = vec![];

        if current.position.direction == 'N' {
            if current.cost >= 1 {
                neighbors.push(ScoredPosition{cost: current.cost - 1, position: Position{x: current.position.x + 1, y: current.position.y, direction: 'N'}});
            }
            if current.cost >= 1000 {
                neighbors.push(ScoredPosition{cost: current.cost - 1000, position: Position{x: current.position.x, y: current.position.y, direction: 'E'}});
                neighbors.push(ScoredPosition{cost: current.cost - 1000, position: Position{x: current.position.x, y: current.position.y, direction: 'W'}});
            }
        } else if current.position.direction == 'S' {
            if current.cost >= 1 {
                neighbors.push(ScoredPosition{cost: current.cost - 1, position: Position{x: current.position.x - 1, y: current.position.y, direction: 'S'}});
            }
            if current.cost >= 1000 {
                neighbors.push(ScoredPosition{cost: current.cost - 1000, position: Position{x: current.position.x, y: current.position.y, direction: 'E'}});
                neighbors.push(ScoredPosition{cost: current.cost - 1000, position: Position{x: current.position.x, y: current.position.y, direction: 'W'}});
            }
        } else if current.position.direction == 'W' {
            if current.cost >= 1 {
                neighbors.push(ScoredPosition{cost: current.cost - 1, position: Position{x: current.position.x, y: current.position.y + 1, direction: 'W'}});
            }
            if current.cost >= 1000 {
                neighbors.push(ScoredPosition{cost: current.cost - 1000, position: Position{x: current.position.x, y: current.position.y, direction: 'N'}});
                neighbors.push(ScoredPosition{cost: current.cost - 1000, position: Position{x: current.position.x, y: current.position.y, direction: 'S'}});
            }
        } else {
            if current.cost >= 1 {
                neighbors.push(ScoredPosition{cost: current.cost - 1, position: Position{x: current.position.x, y: current.position.y - 1, direction: 'E'}});
            }
            if current.cost >= 1000 {
                neighbors.push(ScoredPosition{cost: current.cost - 1000, position: Position{x: current.position.x, y: current.position.y, direction: 'N'}});
                neighbors.push(ScoredPosition{cost: current.cost - 1000, position: Position{x: current.position.x, y: current.position.y, direction: 'S'}});
            }
        }

        for neighbor in neighbors {
            if cache2.contains_key(&neighbor.position) && *cache2.get(&neighbor.position).unwrap() == neighbor.cost && !visited.contains(&neighbor.position) {
                to_visit.push_back(neighbor);
                visited.insert(neighbor.position);
            }
        }
    }

    for x in 0..map.len() {
        for y in 0..map[0].len() {
            if best_spots.contains(&Point{x: x as isize, y: y as isize}) {
                print!("O");
            } else {
                print!("{}", map[x][y])
            }
        }
        print!("\n");
    }


    best_spots.len() as u64
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_aoc_input_mat_char;
    use crate::utils::types::TestResult;
    use super::*;

    #[test]
    pub fn test_case_1_1() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day16-test-1")?;
        let result = solve1(map);
        assert_eq!(result, 7036);
        Ok(())
    }

    #[test]
    pub fn test_case_1_2() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day16-test-2")?;
        let result = solve1(map);
        assert_eq!(result, 11048);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day16")?;
        let result = solve1(map);
        assert_eq!(result, 102488);
        Ok(())
    }

    #[test]
    pub fn test_case_2_1() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day16-test-1")?;
        let result = solve2(map);
        assert_eq!(result, 45);
        Ok(())
    }

    #[test]
    pub fn test_case_2_2() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day16-test-2")?;
        let result = solve2(map);
        assert_eq!(result, 64);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let map = read_aoc_input_mat_char(2024, "day16")?;
        let result = solve2(map);
        assert_eq!(result, 559);
        Ok(())
    }
}