package com.application.model;

public enum Direction {
    D0, D45, D90, D135, D180, D225, D270, D315;

    public Direction toLeft() {
        return Direction.values()[this.ordinal() - 1];
    }

    public Direction toRight() {
        return Direction.values()[this.ordinal() + 1];
    }
}
