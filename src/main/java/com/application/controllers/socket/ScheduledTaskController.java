package com.application.controllers.socket;

import com.application.dto.ShipTaskDto;
import com.application.services.gamelogic.ScheduledTaskService;
import com.application.tasks.ScheduledTask;
import com.application.tasks.immidiate.DisableRadio;
import com.application.tasks.immidiate.EjectCargo;
import com.application.tasks.immidiate.GeneratorActivation;
import com.application.tasks.immidiate.SwitchAnchor;
import com.application.tasks.scheduled.EnableBoost;
import com.application.tasks.scheduled.EnableCoolers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ScheduledTaskController {
    public static final String MAPPING_IMMEDIATE_TASK = "/scheduledTask";

    private final ScheduledTaskService scheduledTaskService;

    public ScheduledTaskController(ScheduledTaskService scheduledTaskService) {
        this.scheduledTaskService = scheduledTaskService;
    }

    @MessageMapping(MAPPING_IMMEDIATE_TASK)
    public void setMappingImmediateTaskReceive(ShipTaskDto task) {
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
