package com.application.jobs;

import com.application.services.TaskQueueService;
import com.application.tasks.immidiate.ShipJump;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ShipMovementJob {

    private TaskQueueService taskQueueService;
    @Autowired
    public ShipMovementJob(TaskQueueService jmsService) {
        this.taskQueueService = jmsService;
    }

    @Scheduled(fixedRate = 30000)
    private void shipMovement() {
        taskQueueService.sendShipTask(new ShipJump());
    }
}
