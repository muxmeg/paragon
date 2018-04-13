package com.application.tasks.scheduled;

import com.application.model.Ship;
import com.application.tasks.ScheduledTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
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
    public Ship execute(Ship ship, StringBuilder message) {
        ship.setSpeed(ship.getSpeed() + amount);
        message.append("Speed adjusted for ").append(amount).append(". ");
        return ship;
    }

    @Override
    public String toString() {
        return ", sender='" + sender + '\'' +
                ", taskType=ChangeSpeed" +
                ", amount=" + amount;
    }
}
