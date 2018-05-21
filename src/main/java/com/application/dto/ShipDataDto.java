package com.application.dto;

import com.application.model.Ship;
import lombok.Data;
import lombok.experimental.Builder;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class ShipDataDto {
    private final int hull;
    private final double air;
    private final int engine;
    private final Boolean[] cargo;
    private final int airUsers;
    private final boolean anchorOn;
    private final LocalDateTime date = LocalDateTime.now();

    private String message;

    public static ShipDataDto fromEntity(Ship ship, String message) {
        return ShipDataDto.builder().hull(ship.getHull()).air(ship.getAir()).engine(ship.getEngine())
                .cargo(ship.getCargo()).airUsers(ship.getAirUsers()).message(message).anchorOn(ship.isAnchorOn()).build();
    }
}
