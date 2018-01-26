package com.application.model;

import lombok.Data;
import lombok.experimental.Builder;
import org.springframework.beans.BeanUtils;

@Data
@Builder
public class Ship {
    private int hull;
    private int air;
    private int engine;

    private int coordX;
    private int coordY;
    private int speed;
    private Direction direction;

    private int transmitterDisabledTurns;
    private int airUsers;

    public Ship() {
    }

    public Ship(int hull, int air, int engine, int coordX, int coordY, int speed, Direction direction,
                int transmitterDisabledTurns, int airUsers) {
        this.hull = hull;
        this.air = air;
        this.engine = engine;
        this.coordX = coordX;
        this.coordY = coordY;
        this.speed = speed;
        this.direction = direction;
        this.transmitterDisabledTurns = transmitterDisabledTurns;
        this.airUsers = airUsers;
    }

    public Ship copy() {
        return Ship.builder().hull(hull).air(air).engine(engine).coordX(coordX).coordY(coordY).speed(speed)
                .direction(direction).transmitterDisabledTurns(transmitterDisabledTurns).airUsers(airUsers).build();
    }

    public void move(Direction direction, int distance) {
        switch (direction) {
            case D0:
                coordY += distance;
                break;
            case D45:
                coordX += distance;
                coordY += distance;
                break;
            case D90:
                coordX += distance;
                break;
            case D135:
                coordX += distance;
                coordY -= distance;
                break;
            case D180:
                coordY -= distance;
                break;
            case D225:
                coordX -= distance;
                coordY -= distance;
                break;
            case D270:
                coordX -= distance;
                break;
            case D315:
                coordX -= distance;
                coordY += distance;
                break;
        }
    }
}
