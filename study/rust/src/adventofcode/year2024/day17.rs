use std::collections::HashMap;
use num::pow;

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
        assert_eq!(result, "4,6,3,5,6,3,5,2,1,0");
        Ok(())
    }
}