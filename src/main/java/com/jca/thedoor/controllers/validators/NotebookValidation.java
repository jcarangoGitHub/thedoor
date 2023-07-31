package com.jca.thedoor.controllers.validators;

import com.jca.thedoor.entity.mongodb.Notebook;
import com.jca.thedoor.exception.BadRequestException;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.NotFoundException;
import com.jca.thedoor.repository.mongodb.NotebookRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Map;

public class NotebookValidation extends Validator {

    private Notebook _notebook;
    private UserRepository _userRepository;
    private NotebookRepository _notebookRepository;

    @Autowired
    public NotebookValidation(Notebook notebook, UserRepository userRepository,
                              NotebookRepository notebookRepository) {
        this._notebook = notebook;
        this._userRepository = userRepository;
        this._notebookRepository = notebookRepository;
    }

    public NotebookValidation(UserRepository _userRepository) {
        this._notebook = new Notebook();
        this._userRepository = _userRepository;
    }

    public void validateToInsert() {
        validateUser(_userRepository, _notebook.getUser());
        validateNameForUser(ValidationOption.INSERT);
    }

    public void validateToDelete(String[] names) {
        validateUser(_userRepository, _notebook.getUser());
        for (String name : names) {
            _notebook.setName(name);
            validateNameForUser(ValidationOption.DELETE);
        }
    }

    public void validateToFindByUserAndName(Map<String, String> requestBody) {
        if (requestBody.size() != 2) {
           throw new BadRequestException("Invalid parameters. Two parameters are required");
        }
        _notebook.setUser(requestBody.get("user"));
        validateUser(_userRepository, _notebook.getUser());
        String name = requestBody.get("name");
        if (! StringUtils.hasLength(name)) {
            throw new BadRequestException("name must not be null or empty");
        }
    }

    private void validateNameForUser(ValidationOption option) {
        String name = _notebook.getName();
        String user = _notebook.getUser();

        switch (option) {
            case INSERT:
                if (_notebookRepository.existsByNameAndUser(name, user)) {
                    throw new FieldAlreadyExistsException("The field 'Name' is already in use for the current user: " + name);
                }
                break;
            case DELETE:
                if (! _notebookRepository.existsByNameAndUser(name, user)) {
                    throw new NotFoundException("Unable to delete the Notebook. The Name '" + name +
                            "' does not exist for the current user");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid validation option: " + option);
        }

    }

    /*private void validateUser() {
        String userId = _notebook.getUser();
        validateId(userId);

        if (!_userRepository.existsById(userId)) {
            throw new NotFoundException("notebook.user - user not found");
        }
    }*/

    private enum ValidationOption {
        INSERT,
        DELETE
    }
}


