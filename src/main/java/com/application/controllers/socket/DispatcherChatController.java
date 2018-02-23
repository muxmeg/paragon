package com.application.controllers.socket;

import com.application.dto.UserMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DispatcherChatController {

    private static final String TOPIC_DISPATCHER_CHAT = "/topic/dispatcherChat";
    private static final String MAPPING_DISPATCHER_CHAT = "/dispatcherChat";

    public DispatcherChatController() {
    }

    @MessageMapping(MAPPING_DISPATCHER_CHAT)
    @SendTo(TOPIC_DISPATCHER_CHAT)
    public UserMessage sendMessage(UserMessage message) {
        return message;
    }
}
