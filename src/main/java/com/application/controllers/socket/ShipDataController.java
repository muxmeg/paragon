package com.application.controllers.socket;

import com.application.dto.ShipDataDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class ShipDataController {

    public static final String TOPIC_SHIP_DATA = "/topic/shipData";

    private final SimpMessagingTemplate template;

    public ShipDataController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void onShipDataUpdate(ShipDataDto dto) {
        template.convertAndSend(TOPIC_SHIP_DATA, dto);
    }

}
