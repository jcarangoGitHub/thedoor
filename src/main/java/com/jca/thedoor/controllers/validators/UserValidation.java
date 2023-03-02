package com.jca.thedoor.controllers.validators;

import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.exception.*;
import com.jca.thedoor.repository.mongodb.RoleRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;
import org.bson.types.ObjectId;
import org.junit.platform.commons.util.StringUtils;

import java.util.Optional;

public class UserValidation {
    private RoleRepository _roleRepository;
    private UserRepository _userRepository;
    private User _user;
    private User _userFound;

    public UserValidation(User user, RoleRepository roleRepository, UserRepository userRepository) {
        this._user = user;
        this._roleRepository = roleRepository;
        this._userRepository = userRepository;
    }

    //<editor-fold desc="validate to insert">
    public void validateToInsert() {
        validateRoleExists();
    }

    private void validateRoleExists() {
        _user.getRoles().forEach(role -> {
            if (! _roleRepository.existsById(role)) {
                throw new NotFoundException("No es posible guardar el registro. Rol no existe: " + role);
            }
        });
    }

    //</editor-fold>

    //<editor-fold desc="validate to update">
    public void validateToUpdate() {
        validateMandatoryFieldsToUpdate();
        validateIfIdExists();
        validatePasswordNotChanged();
    }

    private void validateMandatoryFieldsToUpdate() {
        ObjectId id = _user.getId();
        if (id == null) {
            throw new BadRequestException("No es posible actualizar el registro. Id: no debe ser nulo");
        }

        if (!isValidObjectId(id)) {
            throw new BadRequestException("No es posible actualizar el registro. Id inválido");
        }
    }

    /**
     * separated by unit test
     * @param id
     * @return
     */
    private boolean isValidObjectId(ObjectId id) {
        return ObjectId.isValid(id.toString());
    }

    private void validateIfIdExists() {
        Optional<User> res = _userRepository.findById(_user.getId().toString());
        if (res.isEmpty()) {
            throw new NotFoundException("Id no encontrado.");
        }

        _userFound = res.get();
    }

    private void validatePasswordNotChanged() {
        if (!_user.getPassword().equals(_userFound.getPassword())) {
            throw new NotAllowedException("No es posible cambiar la contraseña. " +
                    "Dirígase a 'restablecer contraseña'");
        }
    }
    //</editor-fold>

    //TODO unit test
    public static void validateMandatoryFieldsToSearch(User user) {
        if (!(StringUtils.isNotBlank(user.getUsername()) || StringUtils.isNotBlank(user.getEmail())
                || StringUtils.isNotBlank(user.getCellPhoneNumber()))) {
            throw new MissingFieldException("buscar el usuario");
        }
    }

    public User get_userFound() {
        return _userFound;
    }
}
