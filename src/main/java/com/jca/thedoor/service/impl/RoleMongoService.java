package com.jca.thedoor.service.impl;

import com.jca.thedoor.entity.mongodb.Role;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.UtilException;
import com.jca.thedoor.repository.mongodb.RoleRepository;
import com.jca.thedoor.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleMongoService implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<Role> createRole(Role role) {
        try {
            Role created = roleRepository.save(role);
            return ResponseEntity.ok(created);
        } catch (DuplicateKeyException e) {
            throw new FieldAlreadyExistsException("DuplicateKeyException: " + UtilException.EXTRACT_DUPLICATE_MESSAGE_FROM_EXCEPTION(e.getMessage()));
        }
    }
}
