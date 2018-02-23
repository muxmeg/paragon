package com.application.tasks.immidiate;

import com.application.services.ShipService;
import com.application.tasks.ImmediateShipTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
@NoArgsConstructor
public class SwitchAnchor extends ImmediateShipTask {

    public SwitchAnchor(String sender) {
        super(sender);
    }

    @Override
    public void execute(ShipService shipService) {
        shipService.switchAnchor(sender);
    }
}
