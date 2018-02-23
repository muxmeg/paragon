package com.application.controllers.socket;

import com.application.dto.NavigationDataDto;
import com.application.services.gamelogic.NavigationCommandsService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Controller
public class NavigationController {

    public static final String TOPIC_NAVIGATION_DATA = "/topic/navigationData";
    public static final String TOPIC_NAVIGATION_STRINGS = "/topic/navigationStrings";
    public static final String MAPPING_NAVIGATION_COMMANDS= "/navigationCommands";

    private final NavigationCommandsService navigationCommandsService;
    private final SimpMessagingTemplate template;

    public NavigationController(NavigationCommandsService navigationCommandsService, SimpMessagingTemplate template) {
        this.navigationCommandsService = navigationCommandsService;
        this.template = template;
    }

    @MessageMapping(MAPPING_NAVIGATION_COMMANDS)
    public void navigationCommandReceive(String command) {
        navigationCommandsService.addNavigationCommand(command);
    }

    public void updateNavigationData(NavigationDataDto dto) {
        template.convertAndSend(TOPIC_NAVIGATION_DATA, dto);
    }

    public void updateNavigationStrings(Set<String> data) {
        template.convertAndSend(TOPIC_NAVIGATION_STRINGS, data);
    }


}
