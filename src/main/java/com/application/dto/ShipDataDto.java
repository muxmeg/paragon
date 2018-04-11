package com.application.dto;

import com.application.model.Ship;
import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class ShipDataDto {
    private int hull;
    private double air;
    private int engine;
    private Boolean[] cargo;
    private int airUsers;
    private boolean anchorOn;

    private String message;

    public static ShipDataDto fromEntity(Ship ship, String message) {
        return ShipDataDto.builder().hull(ship.getHull()).air(ship.getAir()).engine(ship.getEngine())
                .cargo(ship.getCargo()).airUsers(ship.getAirUsers()).message(message).anchorOn(ship.isAnchorOn()).build();
    }
}
