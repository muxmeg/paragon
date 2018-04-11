package com.application.tasks.scheduled;

import com.application.model.Ship;
import com.application.tasks.ScheduledTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
public class EnableCoolers extends ScheduledTask {

    public EnableCoolers(String sender) {
        super(sender);
    }

    @Override
    public ScheduledTaskType getScheduledTaskType() {
        return ScheduledTaskType.COOLERS;
    }

    @Override
    public Ship execute(Ship ship, StringBuilder message) {
        ship.setAir(ship.getAir() - 5);
        ship.setEngine(ship.getEngine() + 5);
        message.append("Engine coolers enabled. ");
        return ship;
    }
}
