package com.application.dto;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class RadarObject {
    private String name;
    private int coordX;
    private int coordY;
}