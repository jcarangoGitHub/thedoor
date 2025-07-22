package com.jca.thedoor.controllers.validators;

import com.jca.thedoor.exception.NotFoundException;
import com.jca.thedoor.repository.mongodb.RoleRepository;

public class RoleValidation extends Validator {
    private RoleRepository _roleRepository;

    public RoleValidation(RoleRepository _roleRepository) {
        this._roleRepository = _roleRepository;
    }

    public void validateListOfRoleExists(String[] roles) {
        for (String role: roles) {
            roleExists(role);
        }
    }

    public Boolean roleExists(String id) {
        if (!_roleRepository.existsById(id)) {
            throw new NotFoundException("Role. Id no encontrado: " + id);//TODO displaykey
        }
        return true;
    }
}
