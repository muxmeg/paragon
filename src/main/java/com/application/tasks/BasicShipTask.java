package com.application.tasks;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize
public class BasicShipTask {
    public final ShipTaskType taskType;

    public BasicShipTask(ShipTaskType taskType) {
        this.taskType = taskType;
    }
}
