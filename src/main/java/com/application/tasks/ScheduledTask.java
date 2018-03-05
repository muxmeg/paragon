package com.application.tasks;

import com.application.model.Ship;
import com.application.tasks.scheduled.ScheduledTaskType;

public abstract class ScheduledTask extends BasicShipTask {
    public abstract ScheduledTaskType getScheduledTaskType();
    public ScheduledTask() {
        super(ShipTaskType.SCHEDULED);
    }
    public ScheduledTask(String sender) {
        super(ShipTaskType.SCHEDULED, sender);
    }

    public abstract Ship execute(Ship ship);
}
