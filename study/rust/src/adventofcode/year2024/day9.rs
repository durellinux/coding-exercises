use std::ops::Deref;

#[derive(PartialEq)]
struct DiscBlock {
    size: i32,
    is_file: bool,
    file_id: i32,
    index: i32,
    start_block_index: usize,
}

pub fn solve1(disk_map: Vec<i32>) -> u64 {
    let mut disk = expand(&disk_map, true);
    print_disk(&disk);
    defrag(&mut disk, true);
    print_disk(&disk);
    evaluate(&disk)
}

pub fn solve2(disk_map: Vec<i32>) -> u64 {
    let mut disk = expand(&disk_map, false);
    print_disk(&disk);
    defrag(&mut disk, false);
    print_disk(&disk);
    evaluate(&disk)
}

fn defrag(disk: &mut Vec<DiscBlock>, fragmented: bool) {
    let mut last_block_pointer: Option<usize> = find_last_block(&disk, disk.len());
    let mut free_space_start = 0;

    while last_block_pointer.is_some() {
        let block_index = last_block_pointer.unwrap();
        let block_size = disk[block_index].size;
        let block_file_id = disk[block_index].file_id;

        let space_pointer: Option<usize> = find_space(&disk, free_space_start, block_size, block_index);
        if space_pointer.is_some() {
            let space_index = space_pointer.unwrap();
            let free_space = disk[space_index].size;
            for i in 0..block_size {
                disk[space_index + i as usize].start_block_index = space_index;
                disk[space_index + i as usize].file_id = block_file_id;
                disk[space_index + i as usize].is_file = true;
                disk[space_index + i as usize].size = block_size;
            }

            for i in block_size..free_space {
                disk[space_index + i as usize].start_block_index = space_index.checked_add_signed(block_size as isize).unwrap();
                disk[space_index + i as usize].file_id = -1;
                disk[space_index + i as usize].is_file = false;
                disk[space_index + i as usize].size = free_space - block_size;
            }

            for i in 0..block_size {
                disk[block_index + i as usize].start_block_index = block_index;
                disk[block_index + i as usize].file_id = -1;
                disk[block_index + i as usize].is_file = false;
                disk[block_index + i as usize].size = block_size;
            }

            free_space_start = if fragmented { space_index } else { 0 }
        }

        last_block_pointer = find_last_block(&disk, block_index);
    }
}

fn find_space(disk: &Vec<DiscBlock>, from: usize, min_size: i32, max_index: usize) -> Option<usize> {
    let mut i = from;
    while i < max_index {
        if disk[i].is_file == false && disk[i].size >= min_size {
            return Some(i)
        }

        i = i.checked_add_signed(disk[i].size as isize).unwrap();
    }

    None
}

fn evaluate(disk: &Vec<DiscBlock>) -> u64 {
    let mut result: u64 = 0;

    for i in 0..disk.len() {
        let block = &disk[i];
        if block.is_file {
            let positive_val: u64 = block.file_id as u64;
            result += positive_val * i as u64;
        }
    }

    result
}


fn find_last_block(disk: &Vec<DiscBlock>, from: usize) -> Option<usize> {
    for i in (0..from).rev() {
        if disk[i].is_file == true {
            let size: isize = disk[i].size as isize;
            return Some(i.checked_add_signed(-size + 1).unwrap())
        }
    }

    None
}

fn expand(disk_map: &Vec<i32>, fragmented: bool) -> Vec<DiscBlock> {
    let mut expanded: Vec<DiscBlock> = vec![];
    let mut current_id = 0;
    let mut is_file = true;

    for block_size in disk_map.iter() {
        let fill_value = if is_file { current_id } else { -1 };

        let start_block_index = expanded.len();

        for _ in 0..*block_size {
            let block_part = DiscBlock {
                start_block_index: if fragmented { expanded.len() } else { start_block_index },
                index: expanded.len() as i32,
                size: if fragmented { 1 } else { *block_size },
                is_file,
                file_id: fill_value,
            };
            expanded.push(block_part)
        }

        current_id = if is_file { current_id + 1 } else { current_id };
        is_file = !is_file;
    }

    expanded
}

fn print_disk(disk: &Vec<DiscBlock>) {
    for i in disk.iter() {
        if i.file_id == -1 {
            print!(".")
        } else {
            print!("{}", i.file_id);
        }
    }
    print!("\n");
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::utils::file_utils::read_aoc_input;
    use crate::utils::types::GenericError;

    fn parse_input(content: String) -> Result<Vec<i32>, GenericError> {
        let mut data: Vec<i32> = vec![];

        let data_str: Vec<&str> = content.split("").collect();
        for idx in 1..data_str.len()-1 {
            let val_str = data_str[idx];
            data.push(val_str.parse().unwrap())
        }
        Ok(data)
    }

    #[test]
    pub fn test_case_1() -> Result<(), GenericError> {
        let content = read_aoc_input(2024, "day9-test")?;
        let disk_map = parse_input(content)?;
        let result = solve1(disk_map);
        assert_eq!(result, 1928);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> Result<(), GenericError> {
        let content = read_aoc_input(2024, "day9")?;
        let disk_map = parse_input(content)?;
        let result = solve1(disk_map);
        assert_eq!(result, 6341711060162);
        Ok(())
    }


    #[test]
    pub fn test_case_2() -> Result<(), GenericError> {
        let content = read_aoc_input(2024, "day9-test")?;
        let disk_map = parse_input(content)?;
        let result = solve2(disk_map);
        assert_eq!(result, 2858);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> Result<(), GenericError> {
        let content = read_aoc_input(2024, "day9")?;
        let disk_map = parse_input(content)?;
        let result = solve2(disk_map);
        assert_eq!(result, 6377400869326);
        Ok(())
    }


}