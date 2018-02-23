package com.application.controllers.socket;

import com.application.dto.ImmediateShipTaskDto;
import com.application.services.TaskQueueService;
import com.application.tasks.immidiate.DisableRadio;
import com.application.tasks.immidiate.GeneratorActivation;
import com.application.tasks.immidiate.SwitchAnchor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ImmediateTaskController {
    public static final String MAPPING_IMMEDIATE_TASK = "/immediateTask";

    private final TaskQueueService taskQueueService;
    public ImmediateTaskController(TaskQueueService taskQueueService) {
        this.taskQueueService = taskQueueService;
    }

    @MessageMapping(MAPPING_IMMEDIATE_TASK)
    public void setMappingImmediateTaskReceive(ImmediateShipTaskDto task) {
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
        }
    }
}
