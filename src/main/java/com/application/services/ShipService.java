package com.application.services;

import com.application.model.Ship;
import com.application.repositories.ShipRepository;
import com.application.services.gamelogic.JumpShipTaskService;
import com.application.services.gamelogic.MeteorStormService;
import com.application.services.gamelogic.NavigationCommandsService;
import com.application.services.gamelogic.WindService;
import com.application.tasks.JumpShipTask;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final MeteorStormService meteorStormService;
    private final JumpShipTaskService jumpShipTaskService;
    private final NavigationCommandsService navigationCommandsService;
    private final WindService windService;

    public ShipService(ShipRepository shipRepository, MeteorStormService meteorStormService,
                       JumpShipTaskService jumpShipTaskService, NavigationCommandsService navigationCommandsService,
                       WindService windService) {
        this.shipRepository = shipRepository;
        this.meteorStormService = meteorStormService;
        this.jumpShipTaskService = jumpShipTaskService;
        this.navigationCommandsService = navigationCommandsService;
        this.windService = windService;
    }

    public Ship getShip() {
        return shipRepository.getShip();
    }

    public void updateShip(Ship ship) {
        shipRepository.updateShip(ship);
    }

    public void executeJump() {
        Ship ship = shipRepository.getShip();

        Collection<JumpShipTask> preparedTasks = jumpShipTaskService.getPreparedTasks();
        preparedTasks.forEach(task -> task.execute(ship));
        jumpShipTaskService.cleanTasks();

        meteorStormService.applyStormEffects(ship);
        meteorStormService.checkForStorm();

        Map<String, JumpShipTask> activeNavigationCommands = navigationCommandsService.generateNavigationCommands();

        windService.applyNext(ship);

        shipRepository.updateShip(ship);
    }
}
