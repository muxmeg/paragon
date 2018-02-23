package com.application.tasks.immidiate;

import com.application.services.ShipService;
import com.application.tasks.ImmediateShipTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;

@JsonSerialize
public class ShipJump extends ImmediateShipTask {

    public ShipJump() {
        super();
    }

    @Override
    public void execute(ShipService shipService) {
        shipService.executeJump();
    }
}
