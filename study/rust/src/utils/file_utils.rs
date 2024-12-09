use std::{fs, io};

pub fn read_file(file_path: String) -> Result<String, io::Error> {
    let content = fs::read_to_string(file_path)?;
    Ok(content)
}