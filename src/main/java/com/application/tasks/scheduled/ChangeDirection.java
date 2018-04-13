package com.application.tasks.scheduled;

import com.application.model.Ship;
import com.application.tasks.ScheduledTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
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
    public Ship execute(Ship ship, StringBuilder message) {
        if (right) {
            ship.turnRight();
        } else {
            ship.turnLeft();
        }
        message.append("Direction turned ").append(right ? "right" : "left").append(". ");
        return ship;
    }

    @Override
    public String toString() {
        return "sender='" + sender + '\'' +
                ", taskType=ChangeDirection" +
                ", right=" + right
                ;
    }
}
