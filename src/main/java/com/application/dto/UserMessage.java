package com.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data for user lobby/chat message.
 */
@Data
@NoArgsConstructor
public class UserMessage {
    private String sender;
    private String target;
    private String body;
}
