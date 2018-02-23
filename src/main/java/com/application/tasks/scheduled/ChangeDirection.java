package com.application.tasks.scheduled;

import com.application.model.Ship;
import com.application.tasks.ScheduledTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ChangeDirection extends ScheduledTask {

    private final boolean right;

    public ChangeDirection(boolean right) {
        super();
        this.right = right;
    }

    @Override
    public ScheduledTaskType getScheduledTaskType() {
        return ScheduledTaskType.DIRECTION;
    }

    @Override
    public Ship execute(Ship ship) {
        ship.setDirection(right ? ship.getDirection().toRight() : ship.getDirection().toLeft());
        return ship;
    }
}