use crate::utils::geometry::Point;

pub fn valid_point<T>(point: &Point<i32>, matrix: &Vec<Vec<T>>) -> bool {
    let x = point.x;
    let y = point.y;
    valid_coordinates_i64(x as i64, y as i64, matrix)
}

pub fn valid_point_with_size(point: &Point<i32>, matrix_size: Point<usize>) -> bool {
    let x = point.x;
    let y = point.y;
    valid_coordinates_i64_with_size(x as i64, y as i64, matrix_size)
}

pub fn valid_coordinates_i64<T>(x: i64, y: i64, matrix: &Vec<Vec<T>>) -> bool {
    let max_x = matrix.len();
    let max_y = matrix[0].len();
    valid_coordinates_i64_with_size(x, y, Point{x: max_x, y: max_y})
}

pub fn valid_coordinates_i64_with_size(x: i64, y: i64, matrix_size: Point<usize>) -> bool {
    let max_x = matrix_size.x as i64;
    let max_y = matrix_size.y as i64;
    let x64 = x;
    let y64 = y;
    x64  >= 0 && x64 < max_x && y64 >= 0 && y64 < max_y
}

pub fn print_char_matrix(mat: &Vec<Vec<char>>) {
    for i in 0..mat.len() {
        for j in 0..mat[0].len() {
            print!("{}", mat[i][j]);
        }
        print!("\n");
    }
}
