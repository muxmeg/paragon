package com.application.controllers.socket;

import com.application.dto.UserMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PrivateChatController {

    public static final String TOPIC_PRIVATE_CHAT = "/topic/privateChat/";
    public static final String MAPPING_PRIVATE_CHAT = "/privateChat";
    public static final String ANONYMOUS_ROLE = "anonymous";
    private final SimpMessagingTemplate template;

    public PrivateChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping(MAPPING_PRIVATE_CHAT)
    public void messageReceive(UserMessage message) {
        template.convertAndSend(TOPIC_PRIVATE_CHAT + message.getTarget(), message);
    }
}
