package com.application.tasks.scheduled;

import com.application.model.Ship;
import com.application.tasks.ScheduledTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ChangeSpeed extends ScheduledTask {

    private final int amount;

    public ChangeSpeed(int amount) {
        super();
        this.amount = amount;
    }

    @Override
    public ScheduledTaskType getScheduledTaskType() {
        return ScheduledTaskType.SPEED;
    }

    @Override
    public Ship execute(Ship ship) {
        ship.setSpeed(ship.getSpeed() + amount);
        return ship;
    }
}
