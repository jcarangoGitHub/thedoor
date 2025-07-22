package com.jca.thedoor.service;

import com.jca.thedoor.entity.mongodb.Role;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<Role> createRole(Role role);
}
