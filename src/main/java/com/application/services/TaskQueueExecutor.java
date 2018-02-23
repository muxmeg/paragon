package com.application.services;

import com.application.services.gamelogic.ScheduledTaskService;
import com.application.tasks.BasicShipTask;
import com.application.tasks.ImmediateShipTask;
import com.application.tasks.ScheduledTask;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import static com.application.constants.JMSQueues.JMS_FACTORY;
import static com.application.constants.JMSQueues.SHIP_TASK_QUEUE;

@Service
public class TaskQueueExecutor {

    private final ShipService shipService;
    private final ScheduledTaskService scheduledTaskService;


    public TaskQueueExecutor(ShipService shipService, ScheduledTaskService scheduledTaskService) {
        this.shipService = shipService;
        this.scheduledTaskService = scheduledTaskService;
    }

    @JmsListener(destination = SHIP_TASK_QUEUE, containerFactory = JMS_FACTORY)
    public void receiveTask(BasicShipTask task) {
        switch (task.taskType) {
            case IMMEDIATE:
                ((ImmediateShipTask) task).execute(shipService);
                break;
            case SCHEDULED:
                scheduledTaskService.addTask((ScheduledTask) task);
                break;
        }
    }
}
