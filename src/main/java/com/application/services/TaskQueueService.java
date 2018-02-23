package com.application.services;

import com.application.constants.JMSQueues;
import com.application.tasks.BasicShipTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import static com.application.constants.JMSQueues.SHIP_TASK_QUEUE;


@Service
public class TaskQueueService {

    private JmsTemplate jmsTemplate;
    @Autowired
    public TaskQueueService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendShipTask(BasicShipTask shipTask) {
        jmsTemplate.convertAndSend(SHIP_TASK_QUEUE, shipTask);

    }
}
