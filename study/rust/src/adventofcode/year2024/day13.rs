pub struct Case {
    btn_a: Vec<i64>,
    btn_b: Vec<i64>,
    prize: Vec<i64>
}

pub fn solve1(cases: Vec<Case>) -> i64 {
    let mut result = 0;

    for case in cases {
        result += solve_case(case);
    }

    result
}


pub fn solve2(cases: Vec<Case>) -> i64 {
    let mut result = 0;
    let constant = 10000000000000i64;

    for case in cases {
        let case2 = Case {btn_a: case.btn_a, btn_b: case.btn_b, prize: vec![case.prize[0] + constant, case.prize[1] + constant]};
        result += solve_case(case2);
    }

    result
}

fn solve_case(case: Case) -> i64 {

    let a1 = case.btn_a[0];
    let a2 = case.btn_a[1];
    let b1 = case.btn_b[0];
    let b2 = case.btn_b[1];
    let c1 = case.prize[0];
    let c2 = case.prize[1];

    let b = (a1*c2 - a2*c1) / (-a2*b1 + b2*a1);
    let a = (c1 - b1 * b) / a1;

    let test1 = a1 * a + b1 * b - c1;
    let test2 = a2 * a + b2 * b - c2;

    if test1 == 0 && test2 == 0 {
        3*a + b
    } else {
        0
    }
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::{read_aoc_input_lines};
    use super::*;
    use crate::utils::geometry::{GenericError, TestResult};

    fn parse_input(name: &str) -> Result<Vec<Case>, GenericError> {
        let mut cases: Vec<Case> = vec![];
        let lines = read_aoc_input_lines(2024, name)?;
        for i in (0..lines.len()).step_by(4) {
            let a_line = lines.get(i).unwrap();
            let b_line = lines.get(i+1).unwrap();
            let prize_line = lines.get(i+2).unwrap();

            cases.push(Case {btn_a: parse_line(a_line, "+")?, btn_b: parse_line(b_line, "+")?, prize: parse_line(prize_line, "=")?})
        }

        Ok(cases)
    }

    fn parse_line(line: &String, sep: &str) -> Result<Vec<i64>, GenericError> {
        let parts: Vec<&str> = line.split(":").collect();
        let values: Vec<&str> = parts[1].split(",").collect();
        let x: i64 = values[0].trim_start().split(sep).collect::<Vec<_>>()[1].parse()?;
        let y: i64 = values[1].trim_start().split(sep).collect::<Vec<_>>()[1].parse()?;

        Ok(vec![x, y])
    }

    #[test]
    pub fn test_case_1() -> TestResult {
        let cases = parse_input("day13-test")?;
        let result = solve1(cases);
        assert_eq!(result, 480);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let cases = parse_input("day13")?;
        let result = solve1(cases);
        assert_eq!(result, 26810);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let cases = parse_input("day13")?;
        let result = solve2(cases);
        assert_eq!(result, 108713182988244);
        Ok(())
    }

}