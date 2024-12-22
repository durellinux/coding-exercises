use regex::{Regex};

pub fn solve1(input: &str) -> i32 {
    let mul_re = Regex::new(r"mul\((\d{1,3}),(\d{1,3})\)").unwrap(); // mul(1,123)
    let mut result = 0;

    for matched in mul_re.find_iter(input) {
        let caps = mul_re.captures(matched.as_str()).unwrap();
        let v1: i32 = *&caps[1].parse().unwrap();
        let v2: i32 = *&caps[2].parse().unwrap();
        // print!("Result {}: {} x {} = {}\n", matched.as_str(), v1, v2, result);
        result += v1 * v2;
    }

    result
}


pub fn solve2(input: &str) -> i32 {
    let all_patterns = Regex::new(r"(mul\((\d{1,3}),(\d{1,3})\))|do\(\)|don't\(\)").unwrap(); // mul(1,123)
    let mul_re = Regex::new(r"mul\((\d{1,3}),(\d{1,3})\)").unwrap(); // mul(1,123)
    let mut result = 0;

    let mut valid = true;

    for matched in all_patterns.find_iter(input) {
        let matched_str = matched.as_str();
        if matched_str.eq("do()") {
            valid = true
        } else if matched_str.eq("don't()") {
            valid = false
        } else if valid {
            let caps = mul_re.captures(matched_str).unwrap();
            let v1: i32 = *&caps[1].parse().unwrap();
            let v2: i32 = *&caps[2].parse().unwrap();
            // print!("Result {}: {} x {} = {}\n", matched.as_str(), v1, v2, result);
            result += v1 * v2;
        }
    }

    result
}


#[cfg(test)]
mod tests {
    use super::*;
    use crate::utils::file_utils::read_file;
    use crate::utils::geometry::GenericError;

    #[test]
    fn test_case_1() -> Result<(), GenericError> {
        let input = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
        let result = solve1(input);
        assert_eq!(result, 161);
        Ok(())
    }


    #[test]
    fn test_case_2() -> Result<(), GenericError> {
        let input = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
        let result = solve2(input);
        assert_eq!(result, 48);
        Ok(())
    }

    #[test]
    fn test_solution_1() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day3.txt".parse().unwrap())?;
        let result = solve1(content.as_str());
        assert_eq!(result, 153469856);
        Ok(())
    }

    #[test]
    fn test_solution_2() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day3.txt".parse().unwrap())?;
        let result = solve2(content.as_str());
        assert_eq!(result, 77055967);
        Ok(())
    }
}