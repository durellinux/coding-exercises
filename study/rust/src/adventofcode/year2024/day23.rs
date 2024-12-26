use std::collections::{HashMap, HashSet};

pub fn solve1(connections: HashMap<String, HashSet<String>>) -> u64 {
    let mut groups: u64 = 0;
    let computers: Vec<String> = connections.keys().map(|x| x.clone()).collect();

    let computers_start_with_t: HashSet<String> = computers.iter().filter(|x| x.starts_with("t")).map(|x| x.clone()).collect();

    let mut computer_to_id: HashMap<String, usize> = HashMap::new();
    for (i, computer) in computers.iter().enumerate() {
        computer_to_id.insert(computer.clone(), i);
    }

    for c1 in 0..computers.len()-2 {
        for c2 in c1+1..computers.len()-1 {
            let pc1 = computers.get(c1).unwrap();
            let pc2 = computers.get(c2).unwrap();
            if !connections.get(pc1).unwrap().contains(pc2) {
                continue;
            }
            let c1_neighbors = connections.get(pc1).unwrap();
            let c2_neighbors = connections.get(pc2).unwrap();
            let common_neighbors = c1_neighbors.intersection(c2_neighbors).collect::<HashSet<_>>();
            for pc3 in common_neighbors {
                if computers_start_with_t.contains(pc1) || computers_start_with_t.contains(pc2) || computers_start_with_t.contains(pc3)
                    {
                    let c3: usize = *computer_to_id.get(pc3).unwrap();
                    if c3 > c2 {
                        groups += 1;
                    }
                }
            }
        }
    }

    groups
}

pub fn bron_kerbosch(connections: &HashMap<String, HashSet<String>>) -> HashSet<String> {
    let mut r: HashSet<String> = HashSet::new();
    let mut p: HashSet<String> = connections.keys().map(|x| x.clone()).collect();
    let mut x: HashSet<String> = HashSet::new();
    let mut max_clique: HashSet<String> = HashSet::new();
    bron_kerbosch_recursive(connections, &mut r, &mut p, &mut x, &mut max_clique);
    max_clique
}

fn bron_kerbosch_recursive(connections: &HashMap<String, HashSet<String>>, r: &mut HashSet<String>, p: &mut HashSet<String>, x: &mut HashSet<String>, max_clique: &mut HashSet<String>) {
    if p.is_empty() && x.is_empty() {
        if r.len() > max_clique.len() {
            *max_clique = r.clone();
        }
        return;
    }

    let mut p_copy = p.clone();
    for vertex in p_copy.iter() {
        let vertex_neighbors = connections.get(vertex).unwrap();
        let mut r_copy = r.clone();
        r_copy.insert(vertex.clone());
        let mut p_copy = p.clone();
        let mut x_copy = x.clone();
        let vertex_neighbors: HashSet<String> = vertex_neighbors.iter().map(|x| x.clone()).collect();
        bron_kerbosch_recursive(connections, &mut r_copy, &mut p_copy.intersection(&vertex_neighbors).map(|x| x.clone()).collect(), &mut x_copy.intersection(&vertex_neighbors).map(|x| x.clone()).collect(), max_clique);
        p.remove(vertex);
        x.insert(vertex.clone());
    }
}

pub fn solve2(connections: HashMap<String, HashSet<String>>) -> String {
    let min_cliques = bron_kerbosch(&connections);
    let mut min_cliques: Vec<String> = min_cliques.iter().map(|x| x.clone()).collect();
    min_cliques.sort();
    min_cliques.join(",")
}

#[cfg(test)]
mod tests {
    use std::collections::HashMap;
    use crate::utils::file_utils::read_aoc_input_lines;
    use crate::utils::geometry::{GenericError, TestResult};
    use super::*;

    fn parse_input(name: &str) -> Result<HashMap<String, HashSet<String>>, GenericError> {
        let mut connections: HashMap<String, HashSet<String>> = HashMap::new();
        let lines = read_aoc_input_lines(2024, name)?;
        for line in lines {
            let values: Vec<String> = line.split("-").map(|x| x.to_string()).collect();
            connections.entry(values[0].clone()).and_modify(|x| {x.insert(values[1].clone());}).or_insert(HashSet::from([values[1].clone()]));
            connections.entry(values[1].clone()).and_modify(|x| {x.insert(values[0].clone());}).or_insert(HashSet::from([values[0].clone()]));
        }

        Ok(connections)
    }

    #[test]
    fn test_case_1() -> TestResult {
        let connections = parse_input("day23-test")?;
        let result = solve1(connections);
        assert_eq!(result, 7);
        Ok(())
    }


    #[test]
    fn test_solution_1() -> TestResult {
        let connections = parse_input("day23")?;
        let result = solve1(connections);
        assert_eq!(result, 1330);
        Ok(())
    }


    #[test]
    fn test_case_2() -> TestResult {
        let connections = parse_input("day23-test")?;
        let result = solve2(connections);
        assert_eq!(result, "co,de,ka,ta");
        Ok(())
    }


    #[test]
    fn test_solution_2() -> TestResult {
        let connections = parse_input("day23")?;
        let result = solve2(connections);
        assert_eq!(result, "hl,io,ku,pk,ps,qq,sh,tx,ty,wq,xi,xj,yp");
        Ok(())
    }

}