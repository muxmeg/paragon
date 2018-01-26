package com.application.services.gamelogic;

import com.application.model.Direction;
import com.application.model.Ship;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class WindService {
    private Direction nextDirection;
    private int nextPower;

    public void applyNext(Ship ship) {
        ship.move(nextDirection, nextPower);

        nextDirection = Direction.values()[ThreadLocalRandom.current().nextInt(0,
                Direction.values().length + 1)];
        nextPower = ThreadLocalRandom.current().nextInt(1, 4);
    }
}
