pub struct Equation {
    value: u64,
    numbers: Vec<u64>,
}

pub fn solve1(equations: Vec<Equation>) -> u64 {
    let mut result = 0;

    for equation in equations {
        if solve1_recursive1(&equation, equation.numbers[0], 1) {
            result += equation.value
        }
    }

    result
}

fn solve1_recursive1(equation: &Equation, current_result: u64, index: usize) -> bool {
    if current_result > equation.value {
        return false
    }

    if index == equation.numbers.len() {
        if current_result == equation.value {
            return true
        } else {
            return false
        }
    }

    let next_number = equation.numbers[index];

    solve1_recursive1(equation, current_result + next_number, index + 1) ||
        solve1_recursive1(equation, current_result * next_number, index + 1)
}


pub fn solve2(equations: Vec<Equation>) -> u64 {
    let mut result = 0;

    for equation in equations {
        if solve1_recursive2(&equation, equation.numbers[0], 1) {
            result += equation.value
        }
    }

    result
}

fn solve1_recursive2(equation: &Equation, current_result: u64, index: usize) -> bool {
    if current_result > equation.value {
        return false
    }

    if index == equation.numbers.len() {
        if current_result == equation.value {
            return true
        } else {
            return false
        }
    }

    let next_number = equation.numbers[index];
    let digits = next_number.ilog10();
    let base: u64 = 10;
    let next_result_concatenation = current_result * (base.pow(digits + 1)) + next_number;

    solve1_recursive2(equation, current_result + next_number, index + 1) ||
        solve1_recursive2(equation, current_result * next_number, index + 1) ||
        solve1_recursive2(equation, next_result_concatenation, index + 1)
}


#[cfg(test)]
mod tests {
    use crate::utils::file_utils::{read_aoc_input};
    use crate::utils::types::GenericError;
    use super::*;

    fn parse_input(content: String) -> Result<Vec<Equation>, GenericError> {
        let mut equations: Vec<Equation> = vec![];
        let lines: Vec<&str> = content.split("\n").collect();
        for line in lines {
            let parts: Vec<&str> = line.split(":").collect();
            let value: u64 = parts[0].parse()?;
            let numbers_str: Vec<&str> = parts[1].trim_start().split(" ").collect();
            let numbers: Vec<u64> = numbers_str.iter().map(|str_val| str_val.parse().unwrap()).collect();
            let equation = Equation { value, numbers };
            equations.push(equation)
        }

        Ok(equations)
    }

    #[test]
    pub fn test_case_1() -> Result<(), GenericError> {
        let content = read_aoc_input(2024, "day7-test")?;
        let equations = parse_input(content)?;
        let result = solve1(equations);
        assert_eq!(result, 3749);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> Result<(), GenericError> {
        let content = read_aoc_input(2024, "day7")?;
        let equations = parse_input(content)?;
        let result = solve1(equations);
        assert_eq!(result, 1399219271639);
        Ok(())
    }


    #[test]
    pub fn test_case_2() -> Result<(), GenericError> {
        let content = read_aoc_input(2024, "day7-test")?;
        let equations = parse_input(content)?;
        let result = solve2(equations);
        assert_eq!(result, 11387);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> Result<(), GenericError> {
        let content = read_aoc_input(2024, "day7")?;
        let equations = parse_input(content)?;
        let result = solve2(equations);
        assert_eq!(result, 275791737999003);
        Ok(())
    }
}