package com.application.services.gamelogic;

import com.application.controllers.socket.MeteorStormController;
import com.application.model.Ship;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Getter
public class MeteorStormService {
    private final static int MINIMUM_LEVEL_CHANCE = 10;
    private final static int MAXIMUM_LEVEL_CHANCE = 40;

    private int currentStormChance = 0;
    private int incomingStormLevel = 0;

    private final MeteorStormController meteorStormController;

    public MeteorStormService(MeteorStormController meteorStormController) {
        this.meteorStormController = meteorStormController;
    }

    public void checkForStorm() {
        currentStormChance += ThreadLocalRandom.current().nextInt(MINIMUM_LEVEL_CHANCE, MAXIMUM_LEVEL_CHANCE + 1);
        if (currentStormChance >= 100) {
            int tempResult = ThreadLocalRandom.current().nextInt(0, 6);
            if (tempResult <= 2) {
                incomingStormLevel = 1;
            } else if (tempResult <= 4) {
                incomingStormLevel = 2;
            } else {
                incomingStormLevel = 3;
            }
            currentStormChance = 0;
        } else {
            incomingStormLevel = 0;
        }
        updateMeteorStormData();
    }

    public Ship applyStormEffects(Ship ship) {
        switch (incomingStormLevel) {
            case 0:
                break;
            case 1:
                ship.setHull(ship.getHull() - 10);
                ship.setEngine(ship.getEngine() - 5);
                ship.setAir(ship.getAir() - 5);
                break;
            case 2:
                ship.setHull(ship.getHull() - 20);
                ship.setEngine(ship.getEngine() - 10);
                ship.setAir(ship.getAir() - 10);
                break;
            case 3:
                ship.setHull(ship.getHull() - 40);
                ship.setEngine(ship.getEngine() - 20);
                ship.setAir(ship.getAir() - 15);
                break;
        }
        return ship;
    }

    public void updateMeteorStormData() {
        meteorStormController.updateStormPrediction(incomingStormLevel);
    }
}
