pub fn solve1(mat: Vec<Vec<char>>) -> i32 {
    let mut result = 0;

    for x in 0..mat.len() {
        for y in 0..mat[0].len() {
            result += count1(&mat, x as i32, y as i32)
        }
    }

    result
}

fn count1(mat: &Vec<Vec<char>>, x: i32, y: i32) -> i32 {
    let mut result = 0;

    let deltas = [[1, 0], [-1, 0], [0, 1], [0, -1], [1, 1], [-1, -1], [1, -1], [-1, 1]];

    if mat[x as usize][y as usize] == 'X' {
        for delta in deltas {
            result += count_with_direction1(mat, x + delta[0], y + delta[1], delta[0], delta[1], 'M');
        }
    }

    result
}

fn count_with_direction1(mat: &Vec<Vec<char>>, x: i32, y: i32, dx: i32, dy: i32, look_for: char) -> i32 {
    if x < 0 || x >= mat.len() as i32 || y < 0 || y >= mat[0].len() as i32 {
        return 0
    }

    let val = mat[x as usize][y as usize];
    if val != look_for {
        return 0;
        // return count_with_direction(mat, x + dx, y + dy, dx, dy, look_for)
    }

    if look_for == 'S' {
        return 1
    }


    let mut next_look_for = 'A';

    if look_for == 'M' {
        next_look_for = 'A';
    } else if look_for == 'A' {
        next_look_for = 'S'
    }

    count_with_direction1(mat, x + dx, y + dy, dx, dy, next_look_for)
}

pub fn solve2(mat: Vec<Vec<char>>) -> i32 {
    let mut result = 0;

    for x in 0..mat.len() {
        for y in 0..mat[0].len() {
            result += count2(&mat, x as i32, y as i32)
        }
    }


    result
}

fn count2(mat: &Vec<Vec<char>>, x: i32, y: i32) -> i32 {
    if mat[x as usize][y as usize] == 'A' && x >= 1 && x < (mat.len() as i32 - 1) && y >= 1 && y < (mat[0].len() as i32 - 1) {
        let bottom = (x + 1) as usize;
        let top = (x - 1) as usize;
        let right = (y + 1) as usize;
        let left = (y - 1) as usize;

        let top_left = mat[top][left];
        let bottom_right = mat[bottom][right];
        let top_right = mat[top][right];
        let bottom_left = mat[bottom][left];

        if ((top_left == 'M' && bottom_right == 'S') || (top_left == 'S' && bottom_right == 'M')) &&
           ((top_right == 'M' && bottom_left == 'S') || (top_right == 'S' && bottom_left == 'M')) {
            return 1
        }
    }

    0
}



#[cfg(test)]
mod tests {
    use super::*;
    use std::io::Error;
    use crate::utils::file_utils::read_file;
    use crate::utils::geometry::GenericError;

    fn parse_input(content: String) -> Result<Vec<Vec<char>>, Error> {
        let mut mat: Vec<Vec<char>> = vec![];
        let lines: Vec<&str> = content.split("\n").collect();
        lines.iter().for_each(|line| {
            let values: Vec<char> = line.chars().collect();
            mat.push(values);
        });

        Ok(mat)
    }

    #[test]
    fn test_case_1() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day4-test.txt".parse().unwrap())?;
        let mat = parse_input(content)?;
        let result = solve1(mat);
        assert_eq!(result, 18);
        Ok(())
    }

    #[test]
    fn test_solution_1() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day4.txt".parse().unwrap())?;
        let mat = parse_input(content)?;
        let result = solve1(mat);
        assert_eq!(result, 2336);
        Ok(())
    }

    #[test]
    fn test_case_2() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day4-test.txt".parse().unwrap())?;
        let mat = parse_input(content)?;
        let result = solve2(mat);
        assert_eq!(result, 9);
        Ok(())
    }


    #[test]
    fn test_solution_2() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day4.txt".parse().unwrap())?;
        let mat = parse_input(content)?;
        let result = solve2(mat);
        assert_eq!(result, 1831);
        Ok(())
    }
}