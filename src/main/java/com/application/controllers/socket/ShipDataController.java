package com.application.controllers.socket;

import com.application.dto.ShipDataDto;
import com.application.services.EventLoggingService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ShipDataController {

    public static final String TOPIC_SHIP_DATA = "/topic/shipData";

    private final SimpMessagingTemplate template;
    private final EventLoggingService eventLoggingService;

    public ShipDataController(SimpMessagingTemplate template, EventLoggingService eventLoggingService) {
        this.template = template;
        this.eventLoggingService = eventLoggingService;
    }

    public void onShipDataUpdate(ShipDataDto dto) {
        eventLoggingService.logShipChange(dto);
        template.convertAndSend(TOPIC_SHIP_DATA, dto);
    }
}
