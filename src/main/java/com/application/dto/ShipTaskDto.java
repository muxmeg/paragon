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

    public int getIntProperty(String property) {
        return parameters.containsKey(property) ? Integer.parseInt(parameters.get(property)) : 0;
    }

    public double getDoubleProperty(String property) {
        return parameters.containsKey(property) ? Double.parseDouble(parameters.get(property)) : 0;
    }

    public Boolean getBooleanProperty(String property) {
        return parameters.containsKey(property) ? Boolean.parseBoolean(parameters.get(property)) : null;
    }

    @Override
    public String toString() {
        return ", sender='" + sender + '\'' +
                "type='" + type + '\'' +
                ", parameters=" + parameters;
    }
}
