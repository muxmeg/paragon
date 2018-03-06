package com.application.tasks;

import com.application.services.ShipTasksService;


public abstract class ImmediateShipTask extends BasicShipTask {

    public ImmediateShipTask() {
        super(ShipTaskType.IMMEDIATE);
    }

    public ImmediateShipTask(String sender) {
        super(ShipTaskType.IMMEDIATE, sender);
    }

    public abstract void execute(ShipTasksService shipTasksService);
}
