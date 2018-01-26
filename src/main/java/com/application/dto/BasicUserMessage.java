package com.application.dto;

import lombok.Data;

/**
 * Common data for user messages.
 */
@Data
public abstract class BasicUserMessage extends BasicUserAction {
    private String message;
    private String target;
}
