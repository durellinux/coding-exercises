package com.gianlucadurelli.coding.libraries.math;

import java.util.Optional;

public record Interval(Number start, Number end) {
    public boolean isBefore(Interval otherInterval) {
        return this.end.compareTo(otherInterval.start) < 0;
    }

    public boolean isAfter(Interval otherInterval) {
        return this.start.compareTo(otherInterval.end) > 0;
    }

    public boolean areOverlapping(Interval otherInterval) {
        return !isBefore(otherInterval) && !isAfter(otherInterval);
    }

    public Optional<Interval> intersect(Interval otherInterval) {
        if (!areOverlapping(otherInterval)) {
            return Optional.empty();
        }

        if (this.equals(otherInterval)) {
            return Optional.of(new Interval(this.start, this.end));
        }

        if (otherInterval.start.compareTo(this.start) <= 0 && otherInterval.end.compareTo(this.end) >= 0) {
            return Optional.of(new Interval(this.start, this.end));
        }

        if (this.start.compareTo(otherInterval.start) <= 0 && otherInterval.start.compareTo(this.end) <= 0 && otherInterval.end.compareTo(this.end) >= 0) {
            return Optional.of(new Interval(otherInterval.start, this.end));
        }

        if (this.start.compareTo(otherInterval.start) <= 0 && otherInterval.end.compareTo(this.end) < 0) {
            return Optional.of(new Interval(otherInterval.start, otherInterval.end));
        }

        if (otherInterval.start().compareTo(this.start) < 0 && otherInterval.end.compareTo(this.start) >= 0) {
            return Optional.of(new Interval(this.start, otherInterval.end));
        }

        throw new UnsupportedOperationException("Cannot compute intersection " + this + " - " + otherInterval);
    }

    public Number length() {
        return end.subtract(start).add(1);
    }
}
