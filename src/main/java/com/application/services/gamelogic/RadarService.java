package com.application.services.gamelogic;

import com.application.controllers.socket.RadarDataController;
import com.application.controllers.socket.ShipDataController;
import com.application.dto.RadarDataDto;
import com.application.dto.RadarObject;
import com.application.model.Ship;
import com.application.repositories.ShipRepository;
import lombok.Data;
import lombok.experimental.Builder;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RadarService {
    private static final int DETECT_DISTANCE = 15;
    private static final int BOARD_DISTANCE = 7;
    private final ShipRepository shipRepository;
    private final RadarDataController radarDataController;
    private List<RadarObject> objects;

    public RadarService(ShipRepository shipRepository, RadarDataController radarDataController) {
        this.shipRepository = shipRepository;//ship at 20,20, up, speed 1
        this.radarDataController = radarDataController;
        objects = Arrays.asList(RadarObject.builder().coordX(50).coordY(10).name("Probe").build(),
                RadarObject.builder().coordX(80).coordY(40).name("Space station").build(),
                RadarObject.builder().coordX(100).coordY(-10).name("Space station").build(),
                RadarObject.builder().coordX(120).coordY(10).name("Mars orbital station --Destination--")
                        .build());
    }

    public void detectObjects() {
        detectObjects(shipRepository.getShip());
    }

    public void detectObjects(Ship ship) {
        Optional<RadarObject> object = objects.stream().filter(radarObject -> distance(ship.getCoordX(),
                ship.getCoordY(), radarObject.getCoordX(), radarObject.getCoordY()) <= DETECT_DISTANCE).findFirst();
        val result = object.isPresent() ? RadarDataDto.builder().object(object.get())
                .canBeBoarded(distance(ship.getCoordX(), ship.getCoordY(),
                        object.get().getCoordX(), object.get().getCoordY()) <= BOARD_DISTANCE)
                .build()
                : RadarDataDto.builder().object(null).canBeBoarded(false).build();
        radarDataController.updateRadar(result);
    }

    private int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.round(Math.hypot(x1 - x2, y1 - y2));
    }
}


