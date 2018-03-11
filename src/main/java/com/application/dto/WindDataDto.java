package com.application.dto;

import com.application.model.Direction;
import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class WindDataDto {
    private int speed;
    private Direction direction;
}
