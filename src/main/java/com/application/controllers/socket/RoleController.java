package com.application.controllers.socket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.NotNull;

@Controller
public class RoleController {

    public static final String TOPIC_ROLE = "/topic/role";

    private final SimpMessagingTemplate template;

    public RoleController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void logoutUser(@NotNull String role) {
        template.convertAndSend(TOPIC_ROLE + "/logout/" + role, "");
    }
}
