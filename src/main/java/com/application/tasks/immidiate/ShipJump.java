package com.application.tasks.immidiate;

import com.application.services.ShipTasksService;
import com.application.tasks.ImmediateShipTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ShipJump extends ImmediateShipTask {

    public ShipJump() {
        super();
    }

    @Override
    public void execute(ShipTasksService shipTasksService) {
        shipTasksService.executeJump();
    }
}
