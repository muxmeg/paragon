package com.application.controllers.socket;

import com.application.services.ShipService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ShipDataMappingController {

    public static final String MAPPING_SHIP_REQUEST = "/shipData/request";

    private final SimpMessagingTemplate template;
    private final ShipService shipService;

    public ShipDataMappingController(SimpMessagingTemplate template, ShipService shipService) {
        this.template = template;
        this.shipService = shipService;
    }

    @MessageMapping(MAPPING_SHIP_REQUEST)
    public void shipDataRequest() {
        shipService.requestShipDataUpdate();
    }

}
