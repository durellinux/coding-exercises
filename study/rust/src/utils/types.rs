pub type GenericError = Box<dyn std::error::Error>;

#[derive(Debug, Copy, Clone, PartialEq, Hash, Eq)]
pub struct PointI32 {
    pub(crate) x: i32, pub(crate) y: i32
}

#[derive(Debug, Copy, Clone, PartialEq, Hash, Eq)]
pub struct PointWithDirectionI32 {
    pub(crate) point: PointI32, pub(crate) direction: char
}