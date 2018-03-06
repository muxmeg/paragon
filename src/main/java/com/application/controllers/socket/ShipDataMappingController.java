package com.application.controllers.socket;

import com.application.services.ShipTasksService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ShipDataMappingController {

    public static final String MAPPING_SHIP_REQUEST = "/shipData/request";

    private final SimpMessagingTemplate template;
    private final ShipTasksService shipTasksService;

    public ShipDataMappingController(SimpMessagingTemplate template, ShipTasksService shipTasksService) {
        this.template = template;
        this.shipTasksService = shipTasksService;
    }

    @MessageMapping(MAPPING_SHIP_REQUEST)
    public void shipDataRequest() {
        shipTasksService.requestShipDataUpdate();
    }

}
