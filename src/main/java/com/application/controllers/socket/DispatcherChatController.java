package com.application.controllers.socket;

import com.application.dto.UserMessage;
import com.application.services.EventLoggingService;
import com.application.services.ShipTasksService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DispatcherChatController {

    private static final String TOPIC_DISPATCHER_CHAT = "/topic/dispatcherChat";
    private static final String MAPPING_DISPATCHER_CHAT = "/dispatcherChat";

    private final ShipTasksService shipTasksService;
    private final EventLoggingService eventLoggingService;

    public DispatcherChatController(ShipTasksService shipTasksService, EventLoggingService eventLoggingService) {
        this.shipTasksService = shipTasksService;
        this.eventLoggingService = eventLoggingService;
    }

    @MessageMapping(MAPPING_DISPATCHER_CHAT)
    @SendTo(TOPIC_DISPATCHER_CHAT)
    public UserMessage sendMessage(UserMessage message) {
        eventLoggingService.logChat(message);
        if (!shipTasksService.getShip().isTransmitterDisabled()) {
            return message;
        }
        return null;
    }
}
