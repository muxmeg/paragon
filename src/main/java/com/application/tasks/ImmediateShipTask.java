package com.application.tasks;

import com.application.services.ShipService;


public abstract class ImmediateShipTask extends BasicShipTask {
    public ImmediateShipTask() {
        super(ShipTaskType.IMMEDIATE);
    }

    public abstract void execute(ShipService shipService);
}
