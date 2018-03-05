package com.application.dto;

import com.application.tasks.ScheduledTask;
import lombok.Data;
import lombok.experimental.Builder;

import java.util.Map;

@Data
@Builder
public class EncryptedCommandDto {
    private Map<Integer, Character> command;
    private ScheduledTask task;

    public EncryptedCommandDto(Map<Integer, Character> command, ScheduledTask task) {
        this.command = command;
        this.task = task;
    }
}

