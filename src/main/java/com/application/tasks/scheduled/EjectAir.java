package com.application.tasks.scheduled;

import com.application.model.Ship;
import com.application.tasks.ScheduledTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
public class EjectAir extends ScheduledTask {

    public EjectAir(String sender) {
        super(sender);
    }

    @Override
    public ScheduledTaskType getScheduledTaskType() {
        return ScheduledTaskType.EJECT_AIR;
    }

    @Override
    public Ship execute(Ship ship, StringBuilder message) {
        ship.setAir(ship.getAir() - 5);
        message.append("Unexpected air leak detected! ");
        return ship;
    }

    @Override
    public String toString() {
        return "sender='" + sender + '\'' +
                ", taskType=EjectAir"
                ;
    }
}
