use num::{abs, Integer, Signed};

pub type GenericError = Box<dyn std::error::Error>;

#[cfg(test)]
pub type TestResult = Result<(), GenericError>;

#[derive(Debug, Copy, Clone, PartialEq, Hash, Eq)]
pub struct Point<T> {
    pub(crate) x: T, pub(crate) y: T
}

#[derive(Debug, Copy, Clone, PartialEq, Hash, Eq)]
pub struct PointWithDirectionI32<T> {
    pub(crate) point: Point<T>, pub(crate) direction: char
}

#[derive(Debug, Copy, Clone, PartialEq, Hash, Eq)]
pub struct Segment<T> {
    pub p1: Point<T>, pub p2: Point<T>
}

pub fn cartesian_distance_usize(p1: Point<usize>, p2: Point<usize>) -> i64 {
    cartesian_distance_i64(Point{x: p1.x as i64, y: p1.y as i64}, Point{x: p2.x as i64, y: p2.y as i64})
}

pub fn cartesian_distance_i64(p1: Point<i64>, p2: Point<i64>) -> i64 {
    abs(p1.x - p2.x) + abs(p1.y - p2.y)
}