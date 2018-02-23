package com.application.services;

import com.application.controllers.socket.NavigationController;
import com.application.dto.NavigationDataDto;
import com.application.model.Ship;
import com.application.repositories.RolesRepository;
import com.application.repositories.ShipRepository;
import com.application.services.gamelogic.MeteorStormService;
import com.application.services.gamelogic.NavigationCommandsService;
import com.application.services.gamelogic.ScheduledTaskService;
import com.application.services.gamelogic.WindService;
import com.application.tasks.ScheduledTask;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final MeteorStormService meteorStormService;
    private final ScheduledTaskService jumpShipTaskService;
    private final NavigationCommandsService navigationCommandsService;
    private final WindService windService;
    private final RolesRepository rolesRepository;
    private final NavigationController navigationController;

    public ShipService(ShipRepository shipRepository, MeteorStormService meteorStormService,
                       ScheduledTaskService jumpShipTaskService, NavigationCommandsService navigationCommandsService,
                       WindService windService, RolesRepository rolesRepository, NavigationController navigationController) {
        this.shipRepository = shipRepository;
        this.meteorStormService = meteorStormService;
        this.jumpShipTaskService = jumpShipTaskService;
        this.navigationCommandsService = navigationCommandsService;
        this.windService = windService;
        this.rolesRepository = rolesRepository;
        this.navigationController = navigationController;
    }

    public Ship getShip() {
        return shipRepository.getShip();
    }

    public void updateShip(Ship ship) {
        shipRepository.updateShip(ship);
    }

    public void disableRadio(int turns, String sender) {
        Ship ship = shipRepository.getShip();
        ship.setTransmitterDisabledTurns(ship.getTransmitterDisabledTurns() + turns);
        shipRepository.updateShip(ship);

        rolesRepository.increaseOrSetParameter(sender, "disableRadio", 1);
    }

    public void switchAnchor(String sender) {
        Ship ship = shipRepository.getShip();
        ship.setAnchorOn(!ship.isAnchorOn());
        shipRepository.updateShip(ship);
    }

    public void generatorActivation(String sender) {
        Ship ship = shipRepository.getShip();
        ship.setEngine(ship.getEngine() - 50);
        ship.setAir(ship.getEngine() - 20);
        ship.setTransmitterDisabledTurns(ship.getTransmitterDisabledTurns() + 1);
        shipRepository.updateShip(ship);

        rolesRepository.increaseOrSetParameter(sender, "generatorActivation", 1);
    }

    public void executeJump() {
        Ship ship = shipRepository.getShip();

        Collection<ScheduledTask> preparedTasks = jumpShipTaskService.getScheduledTasks();
        preparedTasks.forEach(task -> task.execute(ship));
        jumpShipTaskService.cleanTasks();

        meteorStormService.applyStormEffects(ship);
        meteorStormService.checkForStorm();

        windService.applyNext(ship);

        shipRepository.updateShip(ship);


        navigationController.updateNavigationData(NavigationDataDto.builder().coordX(ship.getCoordX())
                .coordY(ship.getCoordY()).direction(ship.getDirection()).speed(ship.getSpeed()).build());
    }

    public void updateNavigationCommands() {
        Map<String, ScheduledTask> activeNavigationCommands = navigationCommandsService.generateNavigationCommands();

        navigationController.updateNavigationStrings(activeNavigationCommands.keySet());

        Map<String, Map<Integer, String>> navigationCommandsDto = new HashMap<>();
        for (Map.Entry<String, ScheduledTask> entry : activeNavigationCommands.entrySet()) {
            switch (entry.getValue().getScheduledTaskType()) {
                case DIRECTION:
                    //TODO ----
            }
        }
    }
}
