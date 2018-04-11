package com.application.controllers.socket;

import com.application.dto.UserMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GMChatController {

    public static final String TOPIC_PRIVATE_CHAT = "/topic/gmChat/";
    public static final String MAPPING_GM_CHAT = "/gmChat";
    private final SimpMessagingTemplate template;

    public GMChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping(MAPPING_GM_CHAT)
    public void messageReceive(UserMessage message) {
        template.convertAndSend(TOPIC_PRIVATE_CHAT + message.getTarget(), message);
    }
}
