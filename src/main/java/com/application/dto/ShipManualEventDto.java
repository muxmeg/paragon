package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipManualEventDto {
    private int hull;
    private double air;
    private int engine;

    private int coordX;
    private int coordY;
    private int speed;
    private int direction;

    private int transmitterDisabledTurns;
    private int airUsers;
    private Boolean anchorSwitch;
    private String cargo;
    private String message;
}
