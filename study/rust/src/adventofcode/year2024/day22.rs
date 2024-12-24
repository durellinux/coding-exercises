use std::collections::{HashMap, HashSet, VecDeque};

#[derive(Debug, Clone, Copy, Hash, Eq, PartialEq)]
pub struct Sequence {
    pub v1: i64,
    pub v2: i64,
    pub v3: i64,
    pub v4: i64,
}

pub fn solve1(prices: Vec<u64>) -> u64 {
    let mut result: u64 = 0;
    for i in 0..prices.len() {
        let price = prices[i];
        let buyer_prices = buyer_prices(price);
        result += buyer_prices.last().unwrap();
    }
    result
}

fn buyer_prices(secret: u64) -> Vec<u64> {
    let mut prices: Vec<u64> = Vec::new();
    let mut price = secret;
    prices.push(secret);
    for _ in 0..2000 {
        price = next_secret(price);
        prices.push(price);
    }
    prices
}

fn next_secret(secret: u64) -> u64 {
    let mut next_secret = secret;
    let v1 = secret * 64;
    next_secret = mix(secret, v1);
    next_secret = prune(next_secret);

    let v2 = next_secret / 32;
    next_secret = mix(next_secret, v2);
    next_secret = prune(next_secret);

    let v3 = next_secret * 2048;
    next_secret = mix(next_secret, v3);
    next_secret = prune(next_secret);

    next_secret
}

pub fn solve2(prices: Vec<u64>) -> u64 {
    let mut sequences_maps: Vec<HashMap<Sequence, u64>> = Vec::new();
    for i in 0..prices.len() {
        let price = prices[i];
        let prices_secrets = buyer_prices(price);
        let real_prices: Vec<u64> = prices_secrets.iter().map(|price| price % 10).collect();
        let deltas = price_deltas(&real_prices);

        let (delta_sequence_map, _) = delta_sequence_map(&real_prices, &deltas);

        // for p in 1..prices_secrets.len() {
        //     if p < 4 {
        //         println!("{} => {} - {}: {} ({})", i, p, prices_secrets[p], real_prices[p], deltas[p-1]);
        //     } else {
        //         println!("{} => {} - {}: {} ({}) - {},{},{},{}", i, p, prices_secrets[p], real_prices[p], deltas[p-1], sequences[p-4].v1, sequences[p-4].v2, sequences[p-4].v3, sequences[p-4].v4);
        //     }
        // }

        sequences_maps.push(delta_sequence_map);
    }

    let mut max_banana = 0;
    let mut all_sequences: HashSet<Sequence> = HashSet::new();
    for i in 0..sequences_maps.len() {
        let sequence_map = &sequences_maps[i];
        for sequence in sequence_map.keys() {
            all_sequences.insert(*sequence);
        }
    }

    for sequence in all_sequences {
        let mut banana = compute_sequence_value(sequence, &sequences_maps);
        if banana > max_banana {
            max_banana = banana;
        }
    }

    // println!("Known: {}", compute_sequence_value(Sequence{v1:-2, v2: 1, v3: -1, v4: 3}, &sequences_maps));

    max_banana
}

fn compute_sequence_value(sequence: Sequence, buyer_sequence_maps: &Vec<HashMap<Sequence, u64>>) -> u64 {
    let mut banana = 0;
    for i in 0..buyer_sequence_maps.len() {
        let sequence_map = &buyer_sequence_maps[i];
        if sequence_map.contains_key(&sequence) {
            let buyer_value = sequence_map.get(&sequence).unwrap();
            banana += buyer_value;
        }
    }
    banana
}

fn price_deltas(prices: &Vec<u64>) -> Vec<i64> {
    let mut deltas: Vec<i64> = Vec::new();
    for i in 1..prices.len() {
        deltas.push(prices[i] as i64 - prices[i - 1] as i64);
    }
    deltas
}

fn delta_sequence_map(prices: &Vec<u64>, deltas: &Vec<i64>) -> (HashMap<Sequence, u64>, Vec<Sequence>) {
    let mut delta_sequence_map: HashMap<Sequence, u64> = HashMap::new();
    let mut sequence: Sequence = Sequence{v1: deltas[0], v2: deltas[0], v3: deltas[1], v4: deltas[2]};
    let mut sequences: Vec<Sequence> = Vec::new();
    for i in 3..deltas.len() {
        sequence = Sequence{v1: sequence.v2, v2: sequence.v3, v3: sequence.v4, v4: deltas[i]};
        // sequences.push(sequence);
        let price = prices[i + 1];
        if !delta_sequence_map.contains_key(&sequence) {
            delta_sequence_map.insert(sequence, price);
        }
    }
    (delta_sequence_map, sequences)
}

fn mix(v1: u64, v2: u64) -> u64 {
    v1 ^ v2
}

fn prune(v1: u64) -> u64 {
    v1 % 16777216
}

#[cfg(test)]
mod tests {
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::geometry::{GenericError, TestResult};
    use super::*;

    fn parse_input(file: &str) -> Result<Vec<u64>, GenericError> {
        let prices: Vec<u64> = read_aoc_input_lines(2024, file)
            .map(|lines| lines.iter().map(|line| line.parse().unwrap()).collect())?;
        Ok(prices)
    }

    #[test]
    pub fn test_case_1() -> TestResult {
        let initial_prices: Vec<u64> = parse_input("day22-test")?;
        let result = solve1(initial_prices);
        assert_eq!(result, 37327623);
        Ok(())
    }

    #[test]
    pub fn test_solution_1() -> TestResult {
        let initial_prices: Vec<u64> = parse_input("day22")?;
        let result = solve1(initial_prices);
        assert_eq!(result, 19150344884);
        Ok(())
    }

    #[test]
    pub fn test_case_2() -> TestResult {
        let initial_prices: Vec<u64> = parse_input("day22-test-2")?;
        let result = solve2(initial_prices);
        assert_eq!(result, 23);
        Ok(())
    }

    #[test]
    pub fn test_solution_2() -> TestResult {
        let initial_prices: Vec<u64> = parse_input("day22")?;
        let result = solve2(initial_prices);
        assert_eq!(result, 2121);
        Ok(())
    }
}