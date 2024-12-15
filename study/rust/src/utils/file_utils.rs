use std::{fs};
use std::fmt::Debug;
use std::str::FromStr;
use num::Integer;
use crate::utils::string_parsing::{as_char_list, as_number_list};
use crate::utils::types::GenericError;

pub fn read_file(file_path: String) -> Result<String, GenericError> {
    let content = fs::read_to_string(file_path)?;
    Ok(content)
}

pub fn read_aoc_input(year: i32, name: &str) -> Result<String, GenericError> {
    let file_path = "resources/adventofcode/".to_owned() + year.to_string().as_str() + "/" + name + ".txt";
    read_file(file_path)
}

pub fn read_aoc_input_lines(year: i32, name: &str) -> Result<Vec<String>, GenericError> {
    let file_path = "resources/adventofcode/".to_owned() + year.to_string().as_str() + "/" + name + ".txt";
    let content = read_file(file_path)?;
    let lines: Vec<&str> = content.split("\n").collect::<Vec<_>>();
    let string_lines = lines.iter().map(|str| String::from_str(str).unwrap()).collect();
    Ok(string_lines)
}

pub fn read_aoc_input_mat_char(year: i32, name: &str) -> Result<Vec<Vec<char>>, GenericError> {
    let content = read_aoc_input(year, name)?;
    let mut mat: Vec<Vec<char>> = vec![];
    let lines = content.split("\n");
    for line in lines {
        mat.push(as_char_list(line));
    }

    Ok(mat)
}

pub fn read_aoc_input_mat_number<T: Integer + FromStr>(year: i32, name: &str) -> Result<Vec<Vec<T>>, GenericError> where <T as FromStr>::Err: Debug {
    let content = read_aoc_input(year, name)?;
    let mut mat: Vec<Vec<T>> = vec![];
    let lines = content.split("\n");
    for line in lines {
        mat.push(as_number_list(line));
    }

    Ok(mat)
}