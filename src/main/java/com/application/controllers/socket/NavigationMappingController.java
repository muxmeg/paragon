package com.application.controllers.socket;

import com.application.services.ShipTasksService;
import com.application.services.gamelogic.NavigationCommandsService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NavigationMappingController {

    public static final String MAPPING_NAVIGATION_COMMANDS = "/navigationCommands";
    public static final String MAPPING_NAVIGATION_COMMANDS_REQUEST = "/navigationCommands/request";
    public static final String MAPPING_NAVIGATION_STRINGS_REQUEST = "/navigationStrings/request";
    public static final String MAPPING_NAVIGATION_DATA_REQUEST = "/navigationData/request";

    private final NavigationCommandsService navigationCommandsService;
    private final ShipTasksService shipTasksService;

    public NavigationMappingController(NavigationCommandsService navigationCommandsService, ShipTasksService shipTasksService) {
        this.navigationCommandsService = navigationCommandsService;
        this.shipTasksService = shipTasksService;
    }

    @MessageMapping(MAPPING_NAVIGATION_COMMANDS)
    public void navigationCommandReceive(String command) {
        navigationCommandsService.addNavigationCommand(command);
    }

    @MessageMapping(MAPPING_NAVIGATION_COMMANDS_REQUEST)
    public void navigationCommandRequest(String request) {
        navigationCommandsService.updateNavigationCommands();
    }

    @MessageMapping(MAPPING_NAVIGATION_STRINGS_REQUEST)
    public void navigationStringsRequest(String request) {
        navigationCommandsService.updateNavigationStrings();
    }

    @MessageMapping(MAPPING_NAVIGATION_DATA_REQUEST)
    public void navigationDataRequest(String request) {
        shipTasksService.updateNavigationData();
    }
}
