pub type GenericError = Box<dyn std::error::Error>;

#[derive(Debug, Copy, Clone, PartialEq, Hash, Eq)]
pub struct PointI32<T> {
    pub(crate) x: T, pub(crate) y: T
}

#[derive(Debug, Copy, Clone, PartialEq, Hash, Eq)]
pub struct PointWithDirectionI32<T> {
    pub(crate) point: PointI32<T>, pub(crate) direction: char
}