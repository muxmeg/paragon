package com.application.controllers.socket;

import com.application.dto.UserMessage;
import com.application.services.ShipTasksService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PrivateChatController {

    public static final String TOPIC_PRIVATE_CHAT = "/topic/privateChat/";
    public static final String MAPPING_PRIVATE_CHAT = "/privateChat";
    public static final String ANONYMOUS_ROLE = "anonymous";
    private final SimpMessagingTemplate template;
    private final ShipTasksService shipTasksService;

    public PrivateChatController(SimpMessagingTemplate template, ShipTasksService shipTasksService) {
        this.template = template;
        this.shipTasksService = shipTasksService;
    }

    @MessageMapping(MAPPING_PRIVATE_CHAT)
    public void messageReceive(UserMessage message) {
        if (!shipTasksService.getShip().isTransmitterDisabled() || (!message.getTarget().equals(ANONYMOUS_ROLE) &&
                !message.getSender().equals(ANONYMOUS_ROLE))) {
            template.convertAndSend(TOPIC_PRIVATE_CHAT + message.getTarget(), message);
        }
    }
}
