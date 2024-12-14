use num::Integer;
use crate::utils::types::PointI32;

pub fn valid_point<T>(point: &PointI32<i32>, matrix: &Vec<Vec<T>>) -> bool {
    let x = point.x;
    let y = point.y;
    valid_coordinates_i64(x as i64, y as i64, matrix)
}

pub fn valid_coordinates_i64<T>(x: i64, y: i64, matrix: &Vec<Vec<T>>) -> bool {
    let max_x = matrix.len() as i64;
    let max_y = matrix[0].len() as i64;
    let x64 = x as i64;
    let y64 = y as i64;
    x64  >= 0 && x64 < max_x && y64 >= 0 && y64 < max_y
}