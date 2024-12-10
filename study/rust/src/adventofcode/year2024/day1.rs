use std::collections::HashMap;

pub fn solve(mut list_1: Vec<i64>, mut  list_2: Vec<i64>) -> i64 {
    list_1.sort();
    list_2.sort();

    let mut result: i64 = 0;

    for i in 0..list_1.len() {
        result = result + (list_2.get(i).unwrap() - list_1.get(i).unwrap()).abs();
    }

    result
}

pub fn solve2(list_1: Vec<i64>, list_2: Vec<i64>) -> i64 {
    let mut map: HashMap<&i64, i64> = HashMap::new();

    list_2.iter().for_each(|num| { map.entry(num).and_modify(|v| *v += 1).or_insert(1); });

    let mut result: i64 = 0;

    for num in list_1 {
        if map.contains_key(&num) {
            result = result + num * map.get(&num).unwrap();
        }
    }

    result
}

#[cfg(test)]
mod tests {
    use std::io;
    use std::io::Error;
    use crate::adventofcode::year2024::day1::{solve, solve2};
    use crate::utils::file_utils::read_file;

    fn parse_input(content: String) -> Result<(Vec<i64>, Vec<i64>), Error> {
        let mut list_1: Vec<i64> = vec![];
        let mut list_2: Vec<i64> = vec![];
        let lines: Vec<&str> = content.split("\n").collect();
        lines.iter().for_each(|line| {
            let values: Vec<&str> = line.split("   ").collect();
            let v1: i64 = values.get(0).unwrap().parse().unwrap();
            let v2: i64 = values.get(1).unwrap().parse().unwrap();
            list_1.push(v1);
            list_2.push(v2);
        });

        Ok((list_1, list_2))
    }

    #[test]
    fn test_solution_1() -> Result<(), io::Error> {
        let content = read_file("resources/adventofcode/2024/day1.txt".parse().unwrap())?;
        let (list_1, list_2) = parse_input(content).unwrap();
        let result = solve(list_1, list_2);
        assert_eq!(result, 3569916);
        Ok(())
    }

    #[test]
    fn test_solution_2() -> Result<(), io::Error> {
        let content = read_file("resources/adventofcode/2024/day1.txt".parse().unwrap())?;
        let (list_1, list_2) = parse_input(content).unwrap();
        let result = solve2(list_1, list_2);
        assert_eq!(result, 26407426);
        Ok(())
    }
}
