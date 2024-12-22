use std::collections::{HashMap, HashSet, VecDeque};

pub fn solve1(towels: Vec<String>, patterns: Vec<String>) -> u32 {
    let mut feasible = 0;

    for pattern in patterns {
        if can_make_pattern(pattern, &towels) {
            feasible += 1;
        }
    }

    feasible
}

pub fn solve2(towels: Vec<String>, patterns: Vec<String>) -> u64 {
    let mut ways = 0;
    let mut memoized: HashMap<String, u64> = HashMap::new();

    for pattern in patterns {
        ways += make_patterns(pattern, &towels, &mut memoized);
    }

    ways
}

fn can_make_pattern(pattern: String, towels: &Vec<String>) -> bool {
    let mut memoized: HashMap<String, u64> = HashMap::new();
    let count = make_patterns(pattern, towels, &mut memoized);
    count > 0
}

fn make_patterns(pattern: String, towels: &Vec<String>, memoized: &mut HashMap<String, u64>) -> u64 {
    if pattern.len() == 0 {
        memoized.insert(pattern, 1);
        return 1
    }

    if memoized.contains_key(&pattern) {
        return *memoized.get(&pattern).unwrap();
    }

    let mut count = 0;

    for towel in towels.iter() {
        if pattern.starts_with(towel) {
            let remaining = String::from(&pattern[towel.len()..]);
            count += make_patterns(remaining, towels, memoized);
        }
    }

    memoized.insert(pattern, count);
    count
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::types::{GenericError, TestResult};
    use super::*;

    fn parse_input(name: &str) -> Result<(Vec<String>, Vec<String>), GenericError> {
        let mut towels: Vec<String> = vec![];
        let mut patterns: Vec<String> = vec![];

        let lines = read_aoc_input_lines(2024, name)?;
        for i in 0..lines.len() {
            if i == 0 {
                towels.extend(lines.get(i).unwrap().split(", ").map(|v| String::from(v)).collect::<Vec<_>>());
            }

            if i > 1 {
                patterns.push(String::from(lines.get(i).unwrap()));
            }
        }

        Ok((towels, patterns))
    }

    #[test]
    pub fn test_case_1() -> TestResult {
        let (towels, patterns) = parse_input("day19-test")?;
        let result = solve1(towels, patterns);
        assert_eq!(result, 6);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let (towels, patterns) = parse_input("day19")?;
        let result = solve1(towels, patterns);
        assert_eq!(result, 267);
        Ok(())
    }

    #[test]
    pub fn test_case_2() -> TestResult {
        let (towels, patterns) = parse_input("day19-test")?;
        let result = solve2(towels, patterns);
        assert_eq!(result, 16);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let (towels, patterns) = parse_input("day19")?;
        let result = solve2(towels, patterns);
        assert_eq!(result, 796449099271652);
        Ok(())
    }
}