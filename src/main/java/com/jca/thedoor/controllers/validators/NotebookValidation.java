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
        _notebookRepository = notebookRepository;
    }

    public void validateToInsert() {
        validateUser();
        validateNameForUser();
    }

    private void validateNameForUser() {
        String name = _notebook.getName();
        String user = _notebook.getUser();

        if (_notebookRepository.existsByNameAndUser(name, user)) {
            throw new FieldAlreadyExistsException("notebook.name - The name is already in use: " + name);
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
        /*try {
            if (id == null) {
                throw new BadRequestException("notebook.user must not be null");
            }
            if (! isValidObjectId(new ObjectId(id))) {
                throw new BadRequestException("notebook.user must be a valid Mongodb ObjectId");
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("notebook.user must be a valid Mongodb ObjectId");
        }*/
    }
}
