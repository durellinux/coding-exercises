use std::collections::HashMap;

pub fn solve1(signals: &mut HashMap<String, i32>, gates: &mut Vec<Vec<String>>) -> u64 {
    loop {
        let mut updated = false;

        for gate in gates.iter() {
            let i1 = gate[0].as_str();
            let i2 = gate[2].as_str();
            let operator = gate[1].as_str();
            let output = gate[3].as_str();

            if signals.contains_key(output) {
                continue;
            }

            if signals.contains_key(i1) && signals.contains_key(i2) {
                let value1 = signals.get(i1).unwrap();
                let value2 = signals.get(i2).unwrap();
                let result = match operator {
                    "AND" => value1 & value2,
                    "OR" => value1 | value2,
                    "XOR" => value1 ^ value2,
                    _ => panic!("Unknown operator")
                };
                signals.insert(output.to_string(), result);
                updated = true;
            }
        }

        if !updated {
            break;
        }
    }

    let sorted_z_signals = signals.iter().filter(|(key, _)| key.starts_with("z")).collect::<Vec<_>>();
    let mut result: u64 = 0;
    for (key, value) in sorted_z_signals {
        let bit = key.replace("z", "").parse::<i32>().unwrap();
        result += (*value as u64) << bit;
    }

    result
}

pub fn solve2() -> String {
    // Solved by hand looking at the Full Adder circuit diagram
    let mut swapped: Vec<&str> = vec!["z23", "frt", "z11", "sps", "z05", "tst", "cgh", "pmd"];
    swapped.sort();
    swapped.join(",")
}

#[cfg(test)]
mod tests {
    use std::collections::HashMap;
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::geometry::{GenericError, TestResult};
    use super::*;

    fn parse_input(name: &str) -> Result<(HashMap<String, i32>, Vec<Vec<String>>), GenericError> {
        let mut signals = HashMap::new();
        let mut gates: Vec<Vec<String>> = vec![];
        let lines = read_aoc_input_lines(2024, name)?;
        for line in lines {
            if line.contains(": ") {
                let mut parts = line.split(": ");
                let signal = parts.next().unwrap().to_string();
                let value = parts.next().unwrap().parse::<i32>().unwrap();
                signals.insert(signal, value);
            }

            if line.contains("->") {
                let mut parts = line.split(" -> ");
                let gate = parts.next().unwrap().to_string();
                let output = parts.next().unwrap().to_string();
                let mut inputs = vec![];
                for input in gate.split(" ") {
                    inputs.push(input.to_string());
                }
                inputs.push(output);
                gates.push(inputs);
            }
        }

        Ok((signals, gates))
    }

    #[test]
    fn test_case_1() -> TestResult {
        let (mut signals, mut gates) = parse_input("day24-test")?;
        let result = solve1(&mut signals, &mut gates);
        assert_eq!(result, 2024);
        Ok(())
    }

    #[test]
    fn test_solution_1() -> TestResult {
        let (mut signals, mut gates) = parse_input("day24")?;
        let result = solve1(&mut signals, &mut gates);
        assert_eq!(result, 60714423975686);
        Ok(())
    }

    #[test]
    fn test_solution_2() -> TestResult {
        let (mut signals, mut gates) = parse_input("day24")?;
        let result = solve2();
        assert_eq!(result, "cgh,frt,pmd,sps,tst,z05,z11,z23");
        Ok(())
    }
}