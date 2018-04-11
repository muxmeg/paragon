package com.application.model;

public enum Direction {
    D0, D45, D90, D135, D180, D225, D270, D315;

    public Direction toLeft() {
        if (ordinal() == 0) {
            return values()[values().length - 1];
        }
        return Direction.values()[ordinal() - 1];
    }

    public Direction toRight() {
        if (ordinal() == values()[values().length - 1].ordinal()) {
            return values()[0];
        }
        return Direction.values()[this.ordinal() + 1];
    }
}
