package com.application.dto;

import lombok.Data;

@Data
public class ShipDataDto {
    private int hull;
    private int air;
    private int engine;
    private boolean[] cargo;
    private int airUsers;

    private String message;
}
