package com.application.controllers.rest;

import com.application.dto.LoginDto;
import com.application.dto.RoleDto;
import com.application.model.Role;
import com.application.services.RoleService;
import org.springframework.web.bind.annotation.*;

import static com.application.App.REST_SERVICE_PREFIX;

@CrossOrigin
@RestController
@RequestMapping(value = AuthenticationController.REST_PATH)
public class AuthenticationController {
    static final String REST_PATH =  REST_SERVICE_PREFIX + "security";

    private final RoleService roleService;

    public AuthenticationController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "login")
    public RoleDto loginRole(@RequestBody LoginDto dto) {
        Role role = roleService.verifyPassword(dto.getName(), dto.getPassword());
        return RoleDto.fromEntity(role);
    }
}
