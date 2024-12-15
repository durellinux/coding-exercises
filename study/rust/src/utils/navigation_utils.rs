use crate::utils::matrix::valid_point;
use crate::utils::types::Point;

pub fn get_4_directions() -> Vec<Point<isize>> {
    vec![
        get_top_direction(),
        get_bottom_direction(),
        get_left_direction(),
        get_right_direction()
    ]
}

pub fn get_top_direction() -> Point<isize> {
    Point {x: -1, y: 0}
}

pub fn get_bottom_direction() -> Point<isize> {
    Point {x: 1, y: 0}
}

pub fn get_left_direction() -> Point<isize> {
    Point {x: 0, y: -1}
}

pub fn get_right_direction() -> Point<isize> {
    Point {x: 0, y: 1}
}


pub fn navigate_in_matrix<T>(position: &Point<usize>, directions: &Vec<Point<isize>>, matrix: &Vec<Vec<T>>) -> Vec<Point<usize>> {
    let mut reacheable: Vec<Point<usize>> = vec![];

    for direction in directions {
        let neighbor = get_direction_in_matrix(position, direction, matrix);
        if neighbor.is_some() {
            reacheable.push(neighbor.unwrap())
        }
    }

    reacheable
}

pub fn get_direction_in_matrix<T>(position: &Point<usize>, direction: &Point<isize>, matrix: &Vec<Vec<T>>) -> Option<Point<usize>> {
    let new_x = position.x as isize + direction.x;
    let new_y = position.y as isize + direction.y;
    if valid_point(&Point {x: new_x as i32, y: new_y as i32}, matrix) {
        return Some(Point {x: new_x as usize, y: new_y as usize});
    }

    None
}