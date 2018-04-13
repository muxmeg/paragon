package com.application.controllers.socket;

import com.application.dto.ShipManualEventDto;
import com.application.dto.ShipTaskDto;
import com.application.services.EventLoggingService;
import com.application.services.TaskQueueService;
import com.application.tasks.ShipTaskType;
import com.application.tasks.immidiate.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ImmediateTaskController {
    public static final String MAPPING_IMMEDIATE_TASK = "/immediateTask";

    private final TaskQueueService taskQueueService;
    private final EventLoggingService eventLoggingService;

    public ImmediateTaskController(TaskQueueService taskQueueService, EventLoggingService eventLoggingService) {
        this.taskQueueService = taskQueueService;
        this.eventLoggingService = eventLoggingService;
    }

    @MessageMapping(MAPPING_IMMEDIATE_TASK)
    public void setMappingImmediateTaskReceive(ShipTaskDto task) {
        eventLoggingService.logTask(task, ShipTaskType.IMMEDIATE);
        switch (task.getType()) {
            case "disableRadio":
                taskQueueService.sendShipTask(new DisableRadio(Integer.parseInt(task.getParameters().get("turns")),
                        task.getSender()));
                break;
            case "switchAnchor":
                taskQueueService.sendShipTask(new SwitchAnchor(task.getSender()));
                break;
            case "generatorActivation":
                taskQueueService.sendShipTask(new GeneratorActivation(task.getSender()));
                break;
            case "ejectCargo":
                taskQueueService.sendShipTask(new EjectCargo(Integer.parseInt(task.getParameters().get("cargoId")),
                    task.getSender()));
                break;
            case "manualEvent":
                taskQueueService.sendShipTask(new ManualEvent(task.getSender(),
                        ShipManualEventDto.builder()
                                .air(task.getDoubleProperty("air"))
                                .airUsers(task.getIntProperty("airUsers"))
                                .anchorSwitch(task.getBooleanProperty("anchorSwitch"))
                                .cargo(task.getParameters().get("cargo"))
                                .coordX(task.getIntProperty("coordX"))
                                .coordY(task.getIntProperty("coordY"))
                                .direction(task.getIntProperty("direction"))
                                .engine(task.getIntProperty("engine"))
                                .hull(task.getIntProperty("hull"))
                                .message(task.getParameters().get("message"))
                                .transmitterDisabledTurns(task.getIntProperty("transmitterDisabledTurns"))
                                .speed(task.getIntProperty("speed"))
                                .build()));
                break;
        }
    }
}
