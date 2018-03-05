package com.application.dto;

import lombok.Data;
import lombok.experimental.Builder;

import java.util.List;

@Data
@Builder
public class NavigationCommandsDto {
    private List<EncryptedCommandDto> commands;

    public NavigationCommandsDto(List<EncryptedCommandDto> commands) {
        this.commands = commands;
    }
}

