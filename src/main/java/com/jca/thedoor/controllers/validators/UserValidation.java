package com.jca.thedoor.controllers.validators;

import com.jca.thedoor.entity.mongodb.User;
import com.jca.thedoor.exception.*;
import com.jca.thedoor.repository.mongodb.RoleRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;
import org.bson.types.ObjectId;
import org.junit.platform.commons.util.StringUtils;

import java.util.Optional;

public class UserValidation extends Validator {
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
        /*_user.getRoles().forEach(role -> {
            if (! _roleRepository.existsById(role)) {
                throw new NotFoundException("No es posible guardar el registro. Rol no existe: " + role);
            }
        });*/
    }

    //</editor-fold>

    //<editor-fold desc="validate to update">
    public void validateToUpdate() {
        validateMandatoryFieldsToUpdate();
        validateIfIdExists();
        validatePasswordNotChanged();
    }

    public void validateIdUserExists() {
        validateIfIdExists();
    }

    private void validateMandatoryFieldsToUpdate() {
        ObjectId id = new ObjectId(_user.getId());
        if (id == null) {
            throw new BadRequestException("user.id must not be null");
        }

        if (!isValidObjectId(id)) {
            throw new BadRequestException("user.id must be a valid Mongodb ObjectId");
        }
    }

    private void validateIfIdExists() {
        Optional<User> res = _userRepository.findById(_user.getId());
        if (res.isEmpty()) {
            throw new NotFoundException("Id no encontrado.");
        }

        _userFound = res.get();
    }

    private void validatePasswordNotChanged() {
        //TODO fix model authenticate
        /*if (_user.getPassword() != null && _userFound.getPassword() != null &&
                !_user.getPassword().equals(_userFound.getPassword())) {
            throw new NotAllowedException("No es posible cambiar la contraseña. " +
                    "Dirígase a 'restablecer contraseña'");
        }*/
    }
    //</editor-fold>

    //TODO unit test
    public static void validateMandatoryFieldsToSearch(User user) {
        if (!(StringUtils.isNotBlank(user.getEmail()) || StringUtils.isNotBlank(user.getCellPhoneNumber()))) {
            throw new MissingFieldException("buscar el usuario");
        }
    }

    public User get_userFound() {
        return _userFound;
    }
}
