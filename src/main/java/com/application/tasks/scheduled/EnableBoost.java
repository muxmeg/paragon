package com.application.tasks.scheduled;

import com.application.model.Ship;
import com.application.tasks.ScheduledTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
public class EnableBoost extends ScheduledTask {

    public EnableBoost(String sender) {
        super(sender);
    }

    @Override
    public ScheduledTaskType getScheduledTaskType() {
        return ScheduledTaskType.BOOST;
    }

    @Override
    public Ship execute(Ship ship, StringBuilder message) {
        ship.setSpeed(ship.getSpeed() + 2);
        ship.setEngine(ship.getEngine() - 5);
        message.append("Boosters enabled. ");
        return ship;
    }

    @Override
    public String toString() {
        return "sender='" + sender + '\'' +
                ", taskType=EnableBoost";
    }
}
