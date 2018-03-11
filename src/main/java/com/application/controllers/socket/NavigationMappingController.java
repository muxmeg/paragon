package com.application.controllers.socket;

import com.application.services.ShipTasksService;
import com.application.services.gamelogic.NavigationCommandsService;
import com.application.services.gamelogic.WindService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NavigationMappingController {

    public static final String MAPPING_NAVIGATION_COMMANDS = "/navigationCommands";
    public static final String MAPPING_NAVIGATION_COMMANDS_REQUEST = "/navigationCommands/request";
    public static final String MAPPING_NAVIGATION_STRINGS_REQUEST = "/navigationStrings/request";
    public static final String MAPPING_NAVIGATION_DATA_REQUEST = "/navigationData/request";
    public static final String WIND_DATA_REQUEST = "/windData/request";

    private final NavigationCommandsService navigationCommandsService;
    private final ShipTasksService shipTasksService;
    private final WindService windService;

    public NavigationMappingController(NavigationCommandsService navigationCommandsService,
                                       ShipTasksService shipTasksService, WindService windService) {
        this.navigationCommandsService = navigationCommandsService;
        this.shipTasksService = shipTasksService;
        this.windService = windService;
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

    @MessageMapping(WIND_DATA_REQUEST)
    public void windDataRequest(String request) {
        windService.updateWindData();
    }
}
