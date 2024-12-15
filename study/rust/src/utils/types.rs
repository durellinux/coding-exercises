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