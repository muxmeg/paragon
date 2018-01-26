package com.application.services;

import com.application.tasks.BasicShipTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import static com.application.constants.JMSQueues.JMS_FACTORY;
import static com.application.constants.JMSQueues.SHIP_TASK_QUEUE;

@Service
public class ShipTaskListener {

    private ShipService shipService;

    @Autowired
    public ShipTaskListener(ShipService shipService) {
        this.shipService = shipService;
    }

    @JmsListener(destination = SHIP_TASK_QUEUE, containerFactory = JMS_FACTORY)
    public void receiveTask(BasicShipTask task) {
        task.execute(shipService);
    }
}
