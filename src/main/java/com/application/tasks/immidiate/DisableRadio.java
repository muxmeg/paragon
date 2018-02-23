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
public class DisableRadio extends ImmediateShipTask {

    private final int turns;

    public DisableRadio() {
        turns = 0;
    }

    public DisableRadio(int turns, String sender) {
        super(sender);
        this.turns = turns;
    }

    @Override
    public void execute(ShipService shipService) {
        shipService.disableRadio(turns, sender);
    }
}
