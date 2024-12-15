use crate::utils::types::Point;

pub fn valid_point<T>(point: &Point<i32>, matrix: &Vec<Vec<T>>) -> bool {
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


pub fn print_char_matrix(mat: &Vec<Vec<char>>) {
    for i in 0..mat.len() {
        for j in 0..mat[0].len() {
            print!("{}", mat[i][j]);
        }
        print!("\n");
    }
}
