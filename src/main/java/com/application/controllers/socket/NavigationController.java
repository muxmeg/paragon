package com.application.controllers.socket;

import com.application.dto.NavigationCommandsDto;
import com.application.dto.NavigationDataDto;
import com.application.model.Ship;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class NavigationController {

    public static final String TOPIC_NAVIGATION_DATA = "/topic/navigationData";
    public static final String TOPIC_NAVIGATION_STRINGS = "/topic/navigationStrings";
    public static final String TOPIC_NAVIGATION_COMMANDS = "/topic/navigationCommands";
    public static final String TOPIC_STORM_PREDICTION = "/topic/stormPrediction";

    private final SimpMessagingTemplate template;

    public NavigationController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void updateNavigationData(Ship ship) {
        template.convertAndSend(TOPIC_NAVIGATION_DATA, NavigationDataDto.builder().coordX(ship.getCoordX())
                .coordY(ship.getCoordY()).direction(ship.getDirection()).speed(ship.getSpeed()).build());
    }

    public void updateNavigationStrings(Set<String> data) {
        template.convertAndSend(TOPIC_NAVIGATION_STRINGS, data);
    }

    public void updateNavigationCommands(NavigationCommandsDto dto) {
        template.convertAndSend(TOPIC_NAVIGATION_COMMANDS, dto);
    }

    public void updateStormPrediction() {

    }

}
