package com.application.services;

import com.application.controllers.socket.NavigationController;
import com.application.controllers.socket.RoleController;
import com.application.controllers.socket.ShipDataController;
import com.application.dto.ShipDataDto;
import com.application.dto.ShipManualEventDto;
import com.application.model.Ship;
import com.application.repositories.RolesRepository;
import com.application.repositories.ShipRepository;
import com.application.services.gamelogic.*;
import com.application.tasks.ScheduledTask;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Transactional
@Service
public class ShipTasksService {
    private final ShipRepository shipRepository;
    private final MeteorStormService meteorStormService;
    private final ScheduledTaskService jumpShipTaskService;
    private final NavigationCommandsService navigationCommandsService;
    private final EventLoggingService eventLoggingService;
    private final RadarService radarService;
    private final WindService windService;
    private final RolesRepository rolesRepository;
    private final NavigationController navigationController;
    private final ShipDataController shipDataController;
    private final RoleController roleController;

    public ShipTasksService(ShipRepository shipRepository, MeteorStormService meteorStormService,
                            ScheduledTaskService jumpShipTaskService, NavigationCommandsService navigationCommandsService,
                            EventLoggingService eventLoggingService, RadarService radarService, WindService windService,
                            RolesRepository rolesRepository, NavigationController navigationController,
                            ShipDataController shipDataController, RoleController roleController) {
        this.shipRepository = shipRepository;
        this.meteorStormService = meteorStormService;
        this.jumpShipTaskService = jumpShipTaskService;
        this.navigationCommandsService = navigationCommandsService;
        this.eventLoggingService = eventLoggingService;
        this.radarService = radarService;
        this.windService = windService;
        this.rolesRepository = rolesRepository;
        this.navigationController = navigationController;
        this.shipDataController = shipDataController;
        this.roleController = roleController;
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

    @Transactional
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

            shipDataController.onShipDataUpdate(ShipDataDto.fromEntity(ship,
                    MessageFormat.format("Warning, cargo slot {0} been manually ejected!", cargoId)));
        }
    }

    public void switchAnchor(String sender) {
        Ship ship = shipRepository.getShip();
        ship.setAnchorOn(!ship.isAnchorOn());
        shipRepository.updateShip(ship);

        shipDataController.onShipDataUpdate(ShipDataDto.fromEntity(ship,
                "Ship anchor been switched."));
    }

    public void manualEvent(ShipManualEventDto data, String sender) {
        Ship ship = shipRepository.getShip();
        ship.setAir(ship.getAir() + data.getAir());
        ship.setAirUsers(ship.getAirUsers() + data.getAirUsers());
        ship.setEngine(ship.getEngine() + data.getEngine());
        ship.setHull(ship.getHull() + data.getHull());
        if (data.getCargo() != null && !data.getCargo().equals("")) {
            val elements = data.getCargo().split(",");
            IntStream.range(0, elements.length).forEach(idx -> ship.getCargo()[idx] = Boolean.valueOf(elements[idx]));
        }

        ship.setCoordX(ship.getCoordX() + data.getCoordX());
        ship.setCoordY(ship.getCoordY() + data.getCoordY());
        ship.setSpeed(ship.getSpeed() + data.getSpeed());
        if (data.getDirection() != 0) {
            if (data.getDirection() > 0) {
                IntStream.range(0, data.getDirection()).forEach(idx -> ship.turnRight());
            } else {
                IntStream.range(data.getDirection(), 0).forEach(idx -> ship.turnLeft());
            }
        }

        ship.setTransmitterDisabledTurns(ship.getTransmitterDisabledTurns() + data.getTransmitterDisabledTurns());

        if (data.getAnchorSwitch() != null && data.getAnchorSwitch()) {
            ship.setAnchorOn(!ship.isAnchorOn());
        }

        shipRepository.updateShip(ship);

        shipDataController.onShipDataUpdate(ShipDataDto.fromEntity(ship,
                data.getMessage()));
        navigationController.updateNavigationData(ship);
    }

    @Transactional
    public void generatorActivation(String sender) {
        Ship ship = shipRepository.getShip();
        ship.setEngine(ship.getEngine() - 30);
        ship.setAir(ship.getAir() - 10);
        ship.setTransmitterDisabledTurns(ship.getTransmitterDisabledTurns() + 1);
        shipRepository.updateShip(ship);

        rolesRepository.increaseOrSetParameter(sender, "generatorActivation", 1);
        shipDataController.onShipDataUpdate(ShipDataDto.fromEntity(ship,
                "Attention, unexpected energy vortex detected! Significant damage to all ship's circuits!"));
    }

    @Transactional
    public void executeJump() {
        StringBuilder message = new StringBuilder();

        Ship ship = shipRepository.getShip();

        if (ship.isTransmitterDisabled()) {
            ship.setTransmitterDisabledTurns(ship.getTransmitterDisabledTurns() - 1);
        }
        if (!ship.isAnchorOn()) {
            message.append("Ship jump performed! ");

            Collection<ScheduledTask> preparedTasks = jumpShipTaskService.getScheduledTasks();
            preparedTasks.forEach(task -> task.execute(ship, message));

            shipMove(ship, message);

            shipRepository.updateShip(ship);
            navigationController.updateNavigationData(ship);

            meteorStormService.checkForStorm();
            navigationCommandsService.updateNavigationCommands();
            radarService.detectObjects(ship);
            windService.updateWindData();
            jumpShipTaskService.cleanTasks();
        } else {
            message.append("Anchor is on. Jump wasn't performed. ");
        }

        ShipDataDto shipDataDto = ShipDataDto.fromEntity(ship, message.toString());
        shipDataController.onShipDataUpdate(shipDataDto);
        eventLoggingService.logShipJump(shipDataDto);
    }

    private void shipMove(Ship ship, StringBuilder message) {
        boolean slip = false;
        if (ship.getEngine() <= 60) {
            if (ship.getEngine() > 30) {
                slip = ThreadLocalRandom.current().nextInt(101) < 30;
            } else if (ship.getEngine() > 0) {
                slip = ThreadLocalRandom.current().nextInt(101) < 60;
            } else {
                slip = true;
            }
        }
        if (!slip) {
            windService.applyNext(ship);
            consumeAir(ship);
            consumeEngine(ship);
            applyMeteorRain(ship, message);

            if (ship.getSpeed() > 7) {
                ship.setSpeed(7);
            } else if (ship.getSpeed() < -7) {
                ship.setSpeed(-7);
            }
            ship.move(ship.getDirection(), ship.getSpeed());
        } else {
            message.append(" Something went wrong. The ship jumped on the same place!");
        }
    }

    private void consumeAir(Ship ship) {
        ship.setAir(ship.getAir() - (ship.getAirUsers() * 0.3));
    }

    private void consumeEngine(Ship ship) {
        ship.setEngine(ship.getEngine() - ThreadLocalRandom.current().nextInt(2, 4));
    }

    private void applyMeteorRain(Ship ship, StringBuilder message) {
        if (meteorStormService.getIncomingStormLevel() > 0) {
            switch (meteorStormService.getIncomingStormLevel()) {
                case 1:
                    message.append("Minor debris encountered, ");
                    break;
                case 2:
                    message.append("Significant debris encountered, ");
                    break;
                case 3:
                    message.append("Meteor storm raged on, ");
                    break;
            }
            if (navigationCommandsService.isEvasiveManeuverActive()) {
                message.append("but evasive maneuver saved the ship. ");
            } else {
                meteorStormService.applyStormEffects(ship);
                message.append("ship got serious damage! ");
            }
        }
    }

    public void updateNavigationData() {
        Ship ship = shipRepository.getShip();
        navigationController.updateNavigationData(ship);
    }

    public void changePassword(String role, String newPassword) {
        rolesRepository.changePassword(role, newPassword);
        roleController.logoutUser(role);
    }
}
