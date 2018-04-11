package com.application.tasks.immidiate;

import com.application.dto.ShipManualEventDto;
import com.application.services.ShipTasksService;
import com.application.tasks.ImmediateShipTask;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@JsonSerialize
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManualEvent extends ImmediateShipTask {

    private ShipManualEventDto data;

    public ManualEvent(String sender, ShipManualEventDto data) {
        super(sender);
        this.data = data;
    }

    @Override
    public void execute(ShipTasksService shipTasksService) {
        shipTasksService.manualEvent(data, sender);
    }
}
