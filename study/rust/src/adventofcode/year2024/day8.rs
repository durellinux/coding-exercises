use std::collections::{HashMap, HashSet};
use crate::utils::matrix::valid_point;
use crate::utils::types::PointI32;

pub fn solve1(mat: Vec<Vec<char>>) -> i32 {
    let mut unique_positions: HashSet<PointI32> = HashSet::new();
    let mut frequency_position: HashMap<char, Vec<PointI32>> = HashMap::new();

    for i in 0..mat.len() {
        for j in 0..mat[0].len() {
            let value = mat[i][j];
            if value == '.' {
                continue;
            }

            let position = PointI32 {x: i as i32, y: j as i32};
            frequency_position.entry(value)
                .and_modify(|v| v.push(position))
                .or_insert(vec![position]);
        }
    }

    for (_, positions) in frequency_position.iter() {
        for i1 in 0..positions.len()-1 {
            for i2 in i1+1..positions.len() {
                let p1 = positions[i1];
                let p2 = positions[i2];
                let dx = p2.x - p1.x;
                let dy = p2.y - p1.y;
                let antinode1 = PointI32 {x: p1.x - dx, y: p1.y - dy};
                let antinode2 = PointI32 {x: p2.x + dx, y: p2.y + dy};
                unique_positions.insert(antinode1);
                unique_positions.insert(antinode2);
            }
        }
    }

    unique_positions.iter()
        .filter(|p| valid_point(*p, &mat))
        .count() as i32
}

pub fn solve2(mat: Vec<Vec<char>>) -> i32 {
    let mut unique_positions: HashSet<PointI32> = HashSet::new();
    let mut frequency_position: HashMap<char, Vec<PointI32>> = HashMap::new();

    for i in 0..mat.len() {
        for j in 0..mat[0].len() {
            let value = mat[i][j];
            if value == '.' {
                continue;
            }

            let position = PointI32 {x: i as i32, y: j as i32};
            frequency_position.entry(value)
                .and_modify(|v| v.push(position))
                .or_insert(vec![position]);
        }
    }

    for (_, positions) in frequency_position.iter() {
        for i1 in 0..positions.len() {
            for i2 in 0..positions.len() {
                if i1 == i2 {
                    continue
                }

                let p1 = positions[i1];
                let p2 = positions[i2];
                let dx = p2.x - p1.x;
                let dy = p2.y - p1.y;
                let mut antinode = PointI32 {x: p1.x + dx, y: p1.y + dy};

                while valid_point(&antinode, &mat) {
                    unique_positions.insert(antinode);
                    antinode.x += dx;
                    antinode.y += dy;
                }
            }
        }
    }

    unique_positions.iter()
        .filter(|p| valid_point(*p, &mat))
        .count() as i32
}

#[cfg(test)]
mod test {
    use super::*;
    use crate::utils::file_utils::read_aoc_input_mat_char;
    use crate::utils::types::GenericError;

    #[test]
    pub fn test_case_1() -> Result<(), GenericError> {
        let mat = read_aoc_input_mat_char(2024, "day8-test")?;
        let result = solve1(mat);
        assert_eq!(result, 14);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> Result<(), GenericError> {
        let mat = read_aoc_input_mat_char(2024, "day8")?;
        let result = solve1(mat);
        assert_eq!(result, 265);
        Ok(())
    }

    #[test]
    pub fn test_case_2() -> Result<(), GenericError> {
        let mat = read_aoc_input_mat_char(2024, "day8-test")?;
        let result = solve2(mat);
        assert_eq!(result, 34);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> Result<(), GenericError> {
        let mat = read_aoc_input_mat_char(2024, "day8")?;
        let result = solve2(mat);
        assert_eq!(result, 962);
        Ok(())
    }
}