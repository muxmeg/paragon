package com.application.jobs;

import com.application.services.JMSService;
import com.application.tasks.ShipJump;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ShipMovementJob {

    private JMSService jmsService;
    @Autowired
    public ShipMovementJob(JMSService jmsService) {
        this.jmsService = jmsService;
    }

    @Scheduled(fixedRate = 10000)
    private void shipMovement() {
        jmsService.sendShipTask(new ShipJump());
    }
}
