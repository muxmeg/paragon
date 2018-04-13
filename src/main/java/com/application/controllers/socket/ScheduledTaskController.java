package com.application.controllers.socket;

import com.application.dto.ShipTaskDto;
import com.application.services.EventLoggingService;
import com.application.services.gamelogic.ScheduledTaskService;
import com.application.tasks.ShipTaskType;
import com.application.tasks.scheduled.EnableBoost;
import com.application.tasks.scheduled.EnableCoolers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ScheduledTaskController {
    public static final String MAPPING_IMMEDIATE_TASK = "/scheduledTask";

    private final ScheduledTaskService scheduledTaskService;
    private final EventLoggingService eventLoggingService;

    public ScheduledTaskController(ScheduledTaskService scheduledTaskService, EventLoggingService eventLoggingService) {
        this.scheduledTaskService = scheduledTaskService;
        this.eventLoggingService = eventLoggingService;
    }

    @MessageMapping(MAPPING_IMMEDIATE_TASK)
    public void setMappingImmediateTaskReceive(ShipTaskDto task) {
        eventLoggingService.logTask(task, ShipTaskType.SCHEDULED);
        switch (task.getType()) {
            case "enableBoost":
                scheduledTaskService.addTask(new EnableBoost(task.getSender()));
                break;
            case "enableCoolers":
                scheduledTaskService.addTask(new EnableCoolers(task.getSender()));
                break;
        }
    }
}
