package com.application.dto;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class RadarDataDto {
    private boolean canBeBoarded;
    private RadarObject object;
    private double distance;
}
