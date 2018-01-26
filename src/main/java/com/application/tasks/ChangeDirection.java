package com.application.tasks;

import com.application.model.Ship;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ChangeDirection extends JumpShipTask {

    private final boolean right;

    public ChangeDirection(boolean right) {
        super();
        this.right = right;
    }

    @Override
    public Ship execute(Ship ship) {
        ship.setDirection(right ? ship.getDirection().toRight() : ship.getDirection().toLeft());
        return ship;
    }
}
