package com.application.tasks;

import com.application.model.Ship;

public abstract class JumpShipTask extends BasicShipTask {
    public JumpShipTask() {
        super(ShipTaskType.ON_JUMP);
    }

    public abstract Ship execute(Ship ship);
}
