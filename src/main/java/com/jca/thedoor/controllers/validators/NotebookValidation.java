package com.jca.thedoor.controllers.validators;

import com.jca.thedoor.entity.mongodb.Notebook;
import com.jca.thedoor.exception.BadRequestException;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.NotFoundException;
import com.jca.thedoor.repository.mongodb.NotebookRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class NotebookValidation extends Validator {

    private Notebook _notebook;
    private UserRepository _userRepository;
    private NotebookRepository _notebookRepository;

    @Autowired
    public NotebookValidation(Notebook _notebook, UserRepository _userRepository,
                              NotebookRepository notebookRepository) {
        this._notebook = _notebook;
        this._userRepository = _userRepository;
        this._notebookRepository = notebookRepository;
    }

    public void validateToInsert() {
        validateUser();
        validateNameForUser(ValidationOption.INSERT);
    }

    public void validateToDelete(String[] names) {
        validateUser();
        for (String name : names) {
            _notebook.setName(name);
            validateNameForUser(ValidationOption.DELETE);
        }
    }

    private void validateNameForUser(ValidationOption option) {
        String name = _notebook.getName();
        String user = _notebook.getUser();

        switch (option) {
            case INSERT:
                if (_notebookRepository.existsByNameAndUser(name, user)) {
                    throw new FieldAlreadyExistsException("The field 'Name' is already in use: " + name);
                }
                break;
            case DELETE:
                if (! _notebookRepository.existsByNameAndUser(name, user)) {
                    throw new NotFoundException("Impossible to delete the Notebook. The Name '" + name + "' does not exist");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid validation option: " + option);
        }

    }

    private void validateUser() {
        String userId = _notebook.getUser();
        validateId(userId);

        if (!_userRepository.existsById(userId)) {
            throw new NotFoundException("notebook.user - user not found");
        }
    }

    public static void validateId(String id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }
        if (!id.matches("^[0-9a-fA-F]{24}$")) { // The regular expression assumes that the ObjectId is a
            // 24-character hexadecimal string, which is the standard format for MongoDB ObjectIds.
            throw new BadRequestException("The provided ID is not a valid MongoDB ObjectId");
        }
    }
    private enum ValidationOption {
        INSERT,
        DELETE
    }
}


