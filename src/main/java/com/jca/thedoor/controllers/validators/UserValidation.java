package com.jca.thedoor.controllers.validators;

import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.MissingFieldException;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.security.payload.RegisterRequest;
import com.jca.thedoor.util.ArrayUtil;
import com.jca.thedoor.util.MessageUtil;
import org.junit.platform.commons.util.StringUtils;
import org.yaml.snakeyaml.util.ArrayUtils;

public class UserValidation {
    private UserRepository _userRepository;
    private RegisterRequest _signUpRequest;
    private String _userName;
    private String _email;
    private String _cellPhoneNumber;
    private String _password;
    private String[] _roles;


    public UserValidation(UserRepository userRepository, RegisterRequest signUpRequest) {
        this._userRepository = userRepository;
        this._signUpRequest = signUpRequest;

        _userName = signUpRequest.getUserName();
        _email = signUpRequest.getEmail();
        _cellPhoneNumber = signUpRequest.getCellPhoneNumber();
        _password = signUpRequest.getPassword();
        _roles = signUpRequest.getRoles();
    }

    public void validateAll() {
        validateMandatoryFields();
        validateUserNameExists();
        validateEmailExists();
        validateCellPhoneNumberExists();
    }

    private void validateMandatoryFields() {
        if (StringUtils.isBlank(_userName) || StringUtils.isBlank(_email) || StringUtils.isBlank(_cellPhoneNumber) ||
                StringUtils.isBlank(_password) || ArrayUtil.isArrayNullOrEmpty(_signUpRequest.getRoles())) {
            throw new MissingFieldException("Faltan campos necesarios para registrar el usuario");
        }
    }

    private void validateUserNameExists() {
        if (_userRepository.findFirstByUserNameExists(_userName)) {
            throw new FieldAlreadyExistsException(
                    MessageUtil.getMessageFieldsAlreadyExists(
                            new String[]{"Nombre de usuario"}));
        }
    }

    private void validateEmailExists() {
        if (_userRepository.existsByEmail(_email)) {
            throw new FieldAlreadyExistsException(
                    MessageUtil.getMessageFieldsAlreadyExists(
                            new String[]{"Email"}));
        }
    }

    private void validateCellPhoneNumberExists() {
        if (_userRepository.existsByCellPhoneNumber(_cellPhoneNumber)) {
            throw new FieldAlreadyExistsException(
                    MessageUtil.getMessageFieldsAlreadyExists(
                            new String[]{"Número celular"}));
        }
    }
}
