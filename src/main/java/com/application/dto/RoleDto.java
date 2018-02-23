package com.application.dto;

import com.application.model.Role;
import com.application.model.RoleParameter;
import lombok.Data;
import lombok.experimental.Builder;

import java.util.List;

@Data
@Builder
public class RoleDto {
    private String name;
    private boolean secret;
    private String team;
    private List<RoleParameter> roleParameters;

    public static RoleDto fromEntity(Role role) {
        return RoleDto.builder().name(role.getName()).secret(role.isSecret()).team(role.getTeam()).roleParameters
                (role.getRoleParameters()).build();
    }
}
