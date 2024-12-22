pub fn solve1(mat: Vec<Vec<i32>>) -> i32 {
    let mut result: i32 = 0;
    for row in mat {
        result += check_line(row)
    }

    result
}

pub fn solve2(mat: Vec<Vec<i32>>) -> i32 {
    let mut result: i32 = 0;
    for row in mat {
        for i in 0..row.len() {
            let mut new_vector = row.clone();
            new_vector.remove(i);

            if check_line(new_vector) == 1 {
                result += 1;
                break;
            }
        }
    }

    result
}




fn check_line(row: Vec<i32>) -> i32 {
    let diff = row.get(1).unwrap() - row.get(0).unwrap();
    if diff == 0 {
        return 0;
    }

    let mut local_row: Vec<i32> = row.iter().copied().collect();

    let increasing = diff > 0;
    if !increasing {
        local_row.reverse()
    }

    for i in 0..local_row.len()-1 {
        let current = local_row.get(i).unwrap();
        let next = local_row.get(i + 1).unwrap();

        let invalid = next <= current || (next - current > 3);

        if invalid {
            return 0;
        }
    }

    1
}


#[cfg(test)]
mod tests {
    use super::*;
    use crate::utils::file_utils::read_file;
    use crate::utils::geometry::GenericError;

    fn parse_input(content: String) -> Result<Vec<Vec<i32>>, GenericError> {
        let mut mat: Vec<Vec<i32>> = vec![];
        let lines: Vec<&str> = content.split("\n").collect();
        lines.iter().for_each(|line| {
            let values: Vec<&str> = line.split(" ").collect();
            let v1: Vec<i32> = values.iter().map(|str_val| str_val.parse().unwrap()).collect();
            mat.push(v1);
        });

        Ok(mat)
    }

    #[test]
    fn test_case_1() -> Result<(), GenericError> {
        let mat: Vec<Vec<i32>> = vec![
            vec![7, 6, 4, 2, 1],
            vec![1, 2, 7, 8, 9],
            vec![9, 7, 6, 2, 1],
            vec![1, 3, 2, 4, 5],
            vec![8, 6, 4, 4, 1],
            vec![1, 3, 6, 7, 9]
        ];
        let result = solve1(mat);
        assert_eq!(result, 2);
        Ok(())
    }

    #[test]
    fn test_solution_1() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day2.txt".parse().unwrap())?;
        let mat = parse_input(content)?;
        let result = solve1(mat);
        assert_eq!(result, 490);
        Ok(())
    }

    #[test]
    fn test_case_2() -> Result<(), GenericError> {
        let mat: Vec<Vec<i32>> = vec![
            vec![7, 6, 4, 2, 1],
            vec![1, 2, 7, 8, 9],
            vec![9, 7, 6, 2, 1],
            vec![1, 3, 2, 4, 5],
            vec![8, 6, 4, 4, 1],
            vec![1, 3, 6, 7, 9]
        ];
        let result = solve2(mat);
        assert_eq!(result, 4);
        Ok(())
    }


    #[test]
    fn test_solution_2() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day2.txt".parse().unwrap())?;
        let mat = parse_input(content)?;
        let result = solve2(mat);
        assert_eq!(result, 536);
        Ok(())
    }

}
