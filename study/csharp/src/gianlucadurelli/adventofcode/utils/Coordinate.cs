namespace src.gianlucadurelli.adventofcode.utils;

public record Coordinate(int Row, int Col);
public record Segment<T>(T Start, T End);