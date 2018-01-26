package com.application.tasks;

import com.application.services.ShipService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ShipJump extends ImmediateShipTask {

    @Override
    public void execute(ShipService shipService) {
        shipService.executeJump();
    }
}
