package com.application.tasks.immidiate;

import com.application.services.ShipTasksService;
import com.application.tasks.ImmediateShipTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
@NoArgsConstructor
public class GeneratorActivation extends ImmediateShipTask {

    public GeneratorActivation(String sender) {
        super(sender);
    }

    @Override
    public void execute(ShipTasksService shipTasksService) {
        shipTasksService.generatorActivation(sender);
    }
}
