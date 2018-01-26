package com.application.services.gamelogic;

import com.application.model.Ship;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class MeteorStormService {
    private final static int MINIMUM_LEVEL_CHANCE = 10;
    private final static int MAXIMUM_LEVEL_CHANCE = 40;

    private int currentStormChance = 0;
    private int nextStormLevel = 0;

    public int checkForStorm() {
        currentStormChance += ThreadLocalRandom.current().nextInt(MINIMUM_LEVEL_CHANCE, MAXIMUM_LEVEL_CHANCE + 1);
        if (currentStormChance >= 100) {
            int tempResult = ThreadLocalRandom.current().nextInt(0, 6);
            if (tempResult <= 2) {
                nextStormLevel = 1;
            } else if (tempResult <= 4) {
                nextStormLevel = 2;
            } else {
                nextStormLevel = 3;
            }
            currentStormChance = 0;
        } else {
            nextStormLevel = 0;
        }
        return nextStormLevel;
    }

    public Ship applyStormEffects(Ship ship) {
        switch (nextStormLevel) {

        }
        return ship;
    }
}
