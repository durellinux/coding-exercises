use std::collections::HashMap;
use num::{pow, range};

#[derive(Copy, Clone, Eq, PartialEq, Hash)]
pub struct Instruction {
    pub opcode: u8,
    pub operand: u8,
}

#[derive(Clone, Eq, PartialEq)]
pub struct Device {
    pub registers: HashMap<char, i64>,
    pub instructions: Vec<Instruction>,
    pub instruction_pointer: usize,
    pub halted: bool,
}

pub fn solve1(device: &mut Device) -> String {
    let mut stdout: Vec<i64> = vec![];
    while !device.halted {
        run_instruction(device, &mut stdout)
    }

    let result = stdout.iter()
        .map(|x| x.to_string())
        .collect::<Vec<_>>()
        .join(",");

    result
}

fn run_instruction(device: &mut Device, stdout: &mut Vec<i64>) {
    let current_instruction = device.instructions[device.instruction_pointer];
    let opcode = current_instruction.opcode;
    let literal_operand = current_instruction.operand;
    let combo_operand = get_operand_value(literal_operand, device);

    if opcode == 0 {
        let numerator = device.registers[&'A'];
        let denominator = pow(2, combo_operand as usize);
        let result = numerator/denominator;
        store(result, 'A', device);
        device.instruction_pointer += 1;
    } else if opcode == 1 {
        let v1 = device.registers[&'B'];
        let v2 = literal_operand as i64;
        let result = v1 ^ v2;
        store(result, 'B', device);
        device.instruction_pointer += 1;
    } else if opcode == 2 {
        let result = combo_operand % 8;
        store(result, 'B', device);
        device.instruction_pointer += 1;
    } else if opcode == 3 {
        let reg_a = device.registers[&'A'];
        if reg_a == 0 {
            device.instruction_pointer += 1;
        } else {
            device.instruction_pointer = literal_operand as usize;
        }
    } else if opcode == 4 {
        let reg_b = device.registers[&'B'];
        let reg_c = device.registers[&'C'];
        let result = reg_b ^ reg_c;
        store(result, 'B', device);
        device.instruction_pointer += 1;
    } else if opcode == 5 {
        let result = combo_operand % 8;
        stdout.push(result);
        device.instruction_pointer += 1;
    } else if opcode == 6 {
        let numerator = device.registers[&'A'];
        let denominator = pow(2, combo_operand as usize);
        let result = numerator/denominator;
        store(result, 'B', device);
        device.instruction_pointer += 1;
    } else if opcode == 7 {
        let numerator = device.registers[&'A'];
        let denominator = pow(2, combo_operand as usize);
        let result = numerator/denominator;
        store(result, 'C', device);
        device.instruction_pointer += 1;
    }

    if device.instruction_pointer >= device.instructions.len() {
        device.halted = true;
    }
}

fn get_operand_value(operand: u8, device: &Device) -> i64 {
    if operand <= 3 {
        operand as i64
    } else if operand == 4 {
        device.registers[&'A']
    } else if operand == 5 {
        device.registers[&'B']
    } else if operand == 6 {
        device.registers[&'C']
    } else {
        println!("This should not be here");
        -1
    }
}

fn store(value: i64, register: char, device: &mut Device) {
    device.registers.entry(register).and_modify(|v| *v = value);
}


// This solution is custom to the input as it is based on the analysis of the program
pub fn solve2(device: &mut Device) -> i64 {
    let values = vec![2,4,1,5,7,5,1,6,0,3,4,6,5,5,3,0];
    let mut unknown_a: Vec<i64> = vec![];
    for _ in 0..64 {
        unknown_a.push(-1);
    }

    let mut results = solve_output(0, 0, 0, &values, 0);
    results.sort();

    results[0]
}

pub fn solve_output(reg_a: i64, locked_a: i64, a_start: usize, values: &Vec<i64>, current_value: usize) -> Vec<i64> {
    if current_value == values.len() {
        return if is_zero(reg_a, locked_a, a_start) {
            vec![reg_a]
        } else {
            vec![]
        }
    }

    let a_mask = 0b111 << a_start;
    let a_0_3 = (reg_a & a_mask) >> a_start;
    let locked_a_0_3 = (locked_a & a_mask) >> a_start;

    let b_possibilities = generate_all_possibilities(a_0_3, locked_a_0_3, 0);

    let mut results = vec![];

    for b in b_possibilities.iter() {
        let constrained_a_with_b = reg_a | (b << a_start);
        let constrained_a_with_b_locked = locked_a | (0b111 << a_start);

        let b1 = *b as usize ^ 5;
        if a_start + b1 + 3 >= 64 {
            continue
        }

        let c_start = a_start+b1;
        let c_mask = 0b111 << c_start;
        let c_0_3 = (constrained_a_with_b & c_mask) >> c_start;
        let locked_c_0_3 = (constrained_a_with_b_locked & c_mask) >> c_start;
        let c_possibilities = generate_all_possibilities(c_0_3, locked_c_0_3, 0);
        let constrained_a_with_b_and_c_locked = constrained_a_with_b_locked | (0b111 << c_start);

        let b2 = b1 as i64 ^ 6;

        let value = values[current_value];
        for c in c_possibilities.iter() {
            if c ^ b2 == value {
                let constrained_a_with_b_and_c = constrained_a_with_b | (c << c_start);
                let next_results = solve_output(constrained_a_with_b_and_c, constrained_a_with_b_and_c_locked, a_start + 3, values, current_value + 1);
                results.extend(next_results);
            }
        }
    }

    results
}

fn is_zero(val: i64, locked_a: i64, from: usize) -> bool {
    for b in from..64 {
        let is_locked = (locked_a & (1 << b)) != 0;
        let value = val & (1 << b);

        if is_locked && value != 0 {
            return false;
        }
    }

    true
}

fn generate_all_possibilities(val: i64, locked: i64, position: usize) -> Vec<i64> {
    if position == 3 {
        let result = vec![val];
        return result;
    }

    let mut result: Vec<i64> = vec![];
    let is_locked = (locked & (0b1 << position)) != 0;

    if !is_locked {
        result.extend(generate_all_possibilities(val, locked, position + 1));
        let new_1 = val | (0b1 << position);
        result.extend(generate_all_possibilities(new_1, locked, position + 1));
    } else {
        result.extend(generate_all_possibilities(val, locked, position + 1));
    }

    result
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_aoc_input_lines;
    use super::*;
    use crate::utils::geometry::{GenericError, TestResult};

    fn parse_input(name: &str) -> Result<Device, GenericError> {
        let mut device = Device{registers: HashMap::new(), instructions: vec![], instruction_pointer: 0, halted: false};
        let lines = read_aoc_input_lines(2024, name)?;
        for line in lines {
            if line.starts_with("Register ") {
                let values: Vec<&str> = line.split(": ").collect();
                let register: &str = values[0].split(" ").collect::<Vec<_>>()[1];
                let value: i64 = values[1].parse()?;
                device.registers.insert(register.chars().collect::<Vec<_>>()[0], value);
            }

            if line.starts_with("Program: ") {
                let values: Vec<&str> = line.split(": ").collect();
                let program: Vec<&str> = values[1].split(",").collect();
                for i in (0..program.len()).step_by(2) {
                    let opcode: u8 = program[i].parse()?;
                    let operand: u8 = program[i + 1].parse()?;
                    device.instructions.push(Instruction{opcode, operand});
                }
            }
        }

        Ok(device)
    }

    #[test]
    pub fn test_case_1() -> TestResult {
        let mut device = parse_input("day17-test")?;
        let result = solve1(&mut device);
        assert_eq!(result, "4,6,3,5,6,3,5,2,1,0");
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let mut device = parse_input("day17")?;
        let result = solve1(&mut device);
        assert_eq!(result, "3,6,3,7,0,7,0,3,0");
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let mut device = parse_input("day17")?;
        let result = solve2(&mut device);
        assert_eq!(result, 136904920099226);
        Ok(())
    }
}