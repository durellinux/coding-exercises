use std::collections::{HashMap, HashSet};
use crate::utils::types::GenericError;

pub fn solve1(rules: HashMap<i32, HashSet<i32>>, updates: Vec<Vec<i32>>) -> Result<i32, GenericError> {
    let mut result = 0;

    for update in updates {
        if is_in_order(&rules, &update) {
            let middle = update.len() / 2;
            result += update.get(middle).unwrap();
        }
    }

    Ok(result)
}


pub fn solve2(rules: HashMap<i32, HashSet<i32>>, updates: &mut Vec<Vec<i32>>) -> Result<i32, GenericError> {
    let mut result = 0;

    for update in updates {
        if !is_in_order(&rules, &update) {
            sort(&rules, update);
            let middle = update.len() / 2;
            result += update.get(middle).unwrap();
        }
    }

    Ok(result)
}

fn sort(rules: &HashMap<i32, HashSet<i32>>, update: &mut Vec<i32>){
    while !is_in_order(rules, update) {
        for i in 0..update.len() - 1 {
            for j in i + 1..update.len() {
                let i_set: i32 = update[i];
                let j_map: i32 = update[j];
                if rules.contains_key(&j_map) && rules.get(&j_map).unwrap().contains(&i_set) {
                    update[i] = j_map;
                    update[j] = i_set;
                }
            }
        }
    }
}

fn is_in_order(rules: &HashMap<i32, HashSet<i32>>, update: &Vec<i32>) -> bool {
    for i in 0..update.len() - 1 {
        for j in i+1..update.len() {
            let i_set: &i32 = update.get(i).unwrap();
            let j_map: &i32 = update.get(j).unwrap();
            if rules.contains_key(j_map) && rules.get(j_map).unwrap().contains(i_set) {
                return false;
            }
        }
    }

    true
}

#[cfg(test)]
mod tests {
    use std::collections::{HashMap, HashSet};
    use crate::utils::file_utils::read_file;
    use crate::utils::types::GenericError;
    use super::*;

    fn parse_input(content: String) -> Result<(HashMap<i32, HashSet<i32>>, Vec<Vec<i32>>), GenericError> {
        let mut rules: HashMap<i32, HashSet<i32>> = HashMap::new();
        let mut updates: Vec<Vec<i32>> = vec![];

        let mut parsing_rules = true;

        let lines: Vec<&str> = content.split("\n").collect();
        for line in lines {
            if line == "" {
                parsing_rules = false;
                continue;
            }

            if parsing_rules {
                let values: Vec<&str> = line.split("|").collect();
                let first: i32 = values.get(0).unwrap().parse()?;
                let second: i32 = values.get(1).unwrap().parse()?;
                rules.entry(first).and_modify(|v| {v.insert(second); ()}).or_insert(vec![second].into_iter().collect());
            } else {
                let values: Vec<&str> = line.split(",").collect();
                let update: Vec<i32> = values.iter().map(|val| {val.parse().unwrap()}).collect();
                updates.push(update);
            }
        }

        Ok((rules, updates))
    }

    #[test]
    pub fn test_case_1() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day5-test.txt".parse()?)?;
        let (rules, updates) = parse_input(content)?;
        let result = solve1(rules, updates)?;
        assert_eq!(result, 143);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day5.txt".parse()?)?;
        let (rules, updates) = parse_input(content)?;
        let result = solve1(rules, updates)?;
        assert_eq!(result, 4790);
        Ok(())
    }

    #[test]
    pub fn test_case_2() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day5-test.txt".parse()?)?;
        let (rules, mut updates) = parse_input(content)?;
        let result = solve2(rules, &mut updates)?;
        assert_eq!(result, 123);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> Result<(), GenericError> {
        let content = read_file("resources/adventofcode/2024/day5.txt".parse()?)?;
        let (rules, mut updates) = parse_input(content)?;
        let result = solve2(rules, &mut updates)?;
        assert_eq!(result, 6319);
        Ok(())
    }
}