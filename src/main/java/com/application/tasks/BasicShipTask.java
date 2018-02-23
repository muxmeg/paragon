package com.application.tasks;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize
public class BasicShipTask {
    public final ShipTaskType taskType;
    public final String sender;

    public BasicShipTask(ShipTaskType taskType) {
        this.taskType = taskType;
        sender = "system";
    }

    public BasicShipTask(ShipTaskType taskType, String sender) {
        this.taskType = taskType;
        this.sender = sender;
    }
}
