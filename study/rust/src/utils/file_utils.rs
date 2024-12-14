use std::{fs};
use crate::utils::types::GenericError;

pub fn read_file(file_path: String) -> Result<String, GenericError> {
    let content = fs::read_to_string(file_path)?;
    Ok(content)
}

pub fn read_aoc_input(year: i32, name: &str) -> Result<String, GenericError> {
    let file_path = "resources/adventofcode/".to_owned() + year.to_string().as_str() + "/" + name + ".txt";
    read_file(file_path)
}

pub fn read_aoc_input_mat_char(year: i32, name: &str) -> Result<Vec<Vec<char>>, GenericError> {
    let content = read_aoc_input(year, name)?;
    let mut mat: Vec<Vec<char>> = vec![];
    let lines = content.split("\n");
    for line in lines {
        mat.push(line.chars().collect());
    }

    Ok(mat)
}