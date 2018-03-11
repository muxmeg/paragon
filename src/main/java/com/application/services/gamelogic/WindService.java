package com.application.services.gamelogic;

import com.application.controllers.socket.NavigationController;
import com.application.dto.WindDataDto;
import com.application.model.Direction;
import com.application.model.Ship;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class WindService {

    private final NavigationController navigationController;

    private Direction nextDirection = Direction.D0;
    private int nextSpeed = 0;

    public WindService(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public void applyNext(Ship ship) {
        ship.move(nextDirection, nextSpeed);

        nextDirection = Direction.values()[ThreadLocalRandom.current().nextInt(0,
                Direction.values().length + 1)];
        nextSpeed = ThreadLocalRandom.current().nextInt(1, 4);
    }

    public void updateWindData() {
        navigationController.updateWindPrediction(WindDataDto.builder().direction(nextDirection).speed(nextSpeed).build());
    }
}
