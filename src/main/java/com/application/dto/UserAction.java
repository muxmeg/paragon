package com.application.dto;

import lombok.Data;

import java.util.Map;

/**
 * Common data for user actions.
 */
@Data
public class UserAction {
    private String commandName;
    private Map<String, String> parameters;
}