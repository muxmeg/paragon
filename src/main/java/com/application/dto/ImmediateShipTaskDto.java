package com.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Data for user lobby/chat message.
 */
@Data
@NoArgsConstructor
public class ImmediateShipTaskDto {
    private String type;
    private Map<String, String> parameters;
    private String sender;
}
