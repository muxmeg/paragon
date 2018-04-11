package com.application.controllers.rest;

import com.application.services.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.application.App.REST_SERVICE_PREFIX;

@CrossOrigin
@RestController
@RequestMapping(value = RolesController.REST_PATH)
public class RolesController {
    static final String REST_PATH =  REST_SERVICE_PREFIX + "roles";

    private final RoleService roleService;

    public RolesController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/names")
    public List<String> findNames(@RequestParam(required = false) String team) {
        return roleService.findRoleNames(team);
    }
}
