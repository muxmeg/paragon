package com.application.services;

import com.application.controllers.socket.NavigationController;
import com.application.controllers.socket.ShipDataController;
import com.application.dto.NavigationDataDto;
import com.application.dto.ShipDataDto;
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
    private final ShipDataController shipDataController;

    public ShipService(ShipRepository shipRepository, MeteorStormService meteorStormService,
                       ScheduledTaskService jumpShipTaskService, NavigationCommandsService navigationCommandsService,
                       WindService windService, RolesRepository rolesRepository, NavigationController navigationController,
                       ShipDataController shipDataController) {
        this.shipRepository = shipRepository;
        this.meteorStormService = meteorStormService;
        this.jumpShipTaskService = jumpShipTaskService;
        this.navigationCommandsService = navigationCommandsService;
        this.windService = windService;
        this.rolesRepository = rolesRepository;
        this.navigationController = navigationController;
        this.shipDataController = shipDataController;
    }

    public Ship getShip() {
        return shipRepository.getShip();
    }

    public void updateShip(Ship ship) {
        shipRepository.updateShip(ship);
    }

    public void requestShipDataUpdate() {
        shipDataController.onShipDataUpdate(ShipDataDto.fromEntity(shipRepository.getShip(),
                "Ship information been updated by request."));
    }

    public void disableRadio(int turns, String sender) {
        Ship ship = shipRepository.getShip();
        ship.setTransmitterDisabledTurns(ship.getTransmitterDisabledTurns() + turns);
        shipRepository.updateShip(ship);

        rolesRepository.increaseOrSetParameter(sender, "disableRadio", 1);
    }

    public void ejectCargo(int cargoId, String sender) {
        Ship ship = shipRepository.getShip();
        if (ship.getCargo()[cargoId]) {
            ship.getCargo()[cargoId] = false;
            shipRepository.updateShip(ship);

            shipDataController.onShipDataUpdate(ShipDataDto.fromEntity(shipRepository.getShip(),
                    "Cargo been manually ejected!"));
        }
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

        navigationController.updateNavigationData(ship);

        navigationCommandsService.updateNavigationCommands();
    }

    public void updateNavigationData() {
        Ship ship = shipRepository.getShip();
        navigationController.updateNavigationData(ship);
    }
}
