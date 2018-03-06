package com.application.tasks.immidiate;

import com.application.services.ShipTasksService;
import com.application.tasks.ImmediateShipTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
public class EjectCargo extends ImmediateShipTask {

    private final int cargoId;

    public EjectCargo() {
        cargoId = 0;
    }

    public EjectCargo(int cargoIdx, String sender) {
        super(sender);
        this.cargoId = cargoIdx;
    }

    @Override
    public void execute(ShipTasksService shipTasksService) {
        shipTasksService.ejectCargo(cargoId, sender);
    }
}
