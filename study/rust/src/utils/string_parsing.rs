use std::fmt::Debug;
use std::str::FromStr;
use num::Integer;

pub fn as_char_list(string: &str) -> Vec<char> {
    string.chars().collect()
}


pub fn as_string_list(string: &str, split_at: &str) -> Vec<String> {
    let lines: Vec<&str> = string.split(split_at).collect::<Vec<_>>();
    lines.iter().map(|str| String::from_str(str).unwrap()).collect()
}

pub fn as_number_list<T: Integer + FromStr>(string: &str) -> Vec<T> where <T as FromStr>::Err: Debug {
    let values_str: Vec<char> = as_char_list(string);
    let values: Vec<T> = values_str.iter().map(|v| v.to_string().parse().unwrap()).collect();
    values
}

pub fn as_number_list_with_split<T: Integer + FromStr>(string: &str, split_at: &str) -> Vec<T> where <T as FromStr>::Err: Debug {
    let values_str: Vec<String> = as_string_list(string, split_at);
    let values: Vec<T> = values_str.iter().map(|v| v.to_string().parse().unwrap()).collect();
    values
}
