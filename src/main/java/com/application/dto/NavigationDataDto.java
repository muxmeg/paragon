package com.application.dto;

import com.application.model.Direction;
import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class NavigationDataDto {
    private int coordX;
    private int coordY;
    private int speed;
    private Direction direction;
}
