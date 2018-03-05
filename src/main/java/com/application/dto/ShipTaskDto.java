package com.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ShipTaskDto {
    private String type;
    private Map<String, String> parameters;
    private String sender;
}
