use std::collections::HashMap;

#[derive(Debug, Copy, Clone, PartialEq, Hash, Eq)]
struct Step {
    value: u64,
    blink: u64,
}

pub fn solve1(input: Vec<u64>) -> u64 {
    solve_with_max_blinks(input, 25)
}

pub fn solve2(input: Vec<u64>) -> u64 {
    solve_with_max_blinks(input, 75)
}

fn solve_with_max_blinks(input: Vec<u64>, max_blinks: u64) -> u64 {
    let mut count = 0;
    let mut memoized: HashMap<Step, u64> = HashMap::new();

    for value in input {
        let stone = Step {value, blink: 0};
        count += solve_recursive(&stone, max_blinks, &mut memoized);
    }

    // print!("\n");

    count
}

fn solve_recursive(stone: &Step, max_blinks: u64, memoized: &mut HashMap<Step, u64>) -> u64 {
    if memoized.contains_key(stone) {
        return memoized[stone]
    }

    if stone.blink == max_blinks {
        // print!("{} ", stone.value);
        return 1
    }

    let value = stone.value;
    if value == 0 {
        let result = solve_recursive(&Step{value: 1, blink: stone.blink + 1}, max_blinks, memoized);
        memoized.entry(*stone).insert_entry(result);
        return result;
    }

    let digits = value.ilog10() + 1;
    // print!("Value: {} -> {} digits\n", stone.value, digits);
    if digits % 2 == 0 {
        let half_digits = digits / 2;
        let base: u64 = 10;
        let power_ten = base.pow(half_digits);
        let high_half = value / power_ten;
        let low_half = value % power_ten;

        let stone1 = Step{value: high_half, blink: stone.blink + 1};
        let stone2 = Step{value: low_half, blink: stone.blink + 1};

        let result = solve_recursive(&stone1, max_blinks, memoized) + solve_recursive(&stone2, max_blinks, memoized);
        memoized.entry(*stone).insert_entry(result);
        return result;
    } else {
        let result = solve_recursive(&Step{value: value * 2024, blink: stone.blink + 1}, max_blinks, memoized);
        memoized.entry(*stone).insert_entry(result);
        return result;
    }
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::{read_aoc_input};
    use crate::utils::string_parsing::{as_number_list_with_split};
    use crate::utils::geometry::{GenericError, TestResult};
    use super::*;

    fn parse_input(name: &str) -> Result<Vec<u64>, GenericError> {
        let line = read_aoc_input(2024, name)?;
        Ok(as_number_list_with_split(line.as_str(), " "))
    }

    #[test]
    pub fn test_case_1() -> TestResult {
        let input = parse_input("day11-test")?;
        let result = solve1(input);
        assert_eq!(result, 55312);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let input = parse_input("day11")?;
        let result = solve1(input);
        assert_eq!(result, 186996);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let input = parse_input("day11")?;
        let result = solve2(input);
        assert_eq!(result, 221683913164898);
        Ok(())
    }
}