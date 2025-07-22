package com.jca.thedoor.controllers;
import com.jca.thedoor.entity.mongodb.Role;
import com.jca.thedoor.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/role")
@Tag(name = "Role Management", description = "Endpoints for managing roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role")
    @Operation(
            summary = "Create a new role",
            description = "Saves a new role to the database."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Role successfully created",
            content = @Content(schema = @Schema(implementation = Role.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid input"
    )
    public ResponseEntity<Role> saveRole(
            @RequestBody(
                    description = "Role object to be saved",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Role.class))
            )
            @org.springframework.web.bind.annotation.RequestBody Role role
    ) {
        return roleService.createRole(role);
    }
    //TODO add permission and test this service
}
