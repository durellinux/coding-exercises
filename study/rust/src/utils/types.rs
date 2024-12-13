use std::io;

pub type GenericError = Box<dyn std::error::Error + Send + Sync>;