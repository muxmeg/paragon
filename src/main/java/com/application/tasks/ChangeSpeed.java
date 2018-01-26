package com.application.tasks;

import com.application.model.Ship;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ChangeSpeed extends JumpShipTask {

    private final int amount;

    public ChangeSpeed(int amount) {
        super();
        this.amount = amount;
    }

    @Override
    public Ship execute(Ship ship) {
        ship.setSpeed(ship.getSpeed() + amount);
        return ship;
    }
}
