package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Builder;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
public class Ship {
    private int hull;
    private double air;
    private int engine;

    private int coordX;
    private int coordY;
    private int speed;
    private Direction direction;

    private int transmitterDisabledTurns;
    private int airUsers;
    private boolean anchorOn;
    private Boolean[] cargo;

    public Ship() {
    }

    public Ship copy() {
        return Ship.builder().hull(hull).air(air).engine(engine).coordX(coordX).coordY(coordY).speed(speed)
                .direction(direction).transmitterDisabledTurns(transmitterDisabledTurns).airUsers(airUsers)
                .anchorOn(anchorOn).cargo(cargo).build();
    }

    public void move(Direction direction, int speed) {
        switch (direction) {
            case D0:
                coordY += speed;
                break;
            case D45:
                coordX += speed;
                coordY += speed;
                break;
            case D90:
                coordX += speed;
                break;
            case D135:
                coordX += speed;
                coordY -= speed;
                break;
            case D180:
                coordY -= speed;
                break;
            case D225:
                coordX -= speed;
                coordY -= speed;
                break;
            case D270:
                coordX -= speed;
                break;
            case D315:
                coordX -= speed;
                coordY += speed;
                break;
        }
    }

    public boolean isTransmitterDisabled() {
        return transmitterDisabledTurns > 0;
    }

    public void turnLeft() {
        direction = direction.toLeft();
    }

    public void turnRight() {
        direction = direction.toRight();
    }
}
