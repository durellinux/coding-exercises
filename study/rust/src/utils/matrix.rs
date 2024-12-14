use crate::utils::types::PointI32;

pub fn valid_point<T>(point: &PointI32, matrix: &Vec<Vec<T>>) -> bool {
    let maxX = matrix.len() as i32;
    let maxY = matrix[0].len() as i32;
    let x = point.x;
    let y = point.y;

    x >= 0 && x < maxX && y >= 0 && y < maxY
}