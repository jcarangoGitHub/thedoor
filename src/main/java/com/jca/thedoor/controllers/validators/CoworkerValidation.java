package com.jca.thedoor.controllers.validators;

import com.jca.thedoor.entity.mongodb.Coworker;
import com.jca.thedoor.entity.mongodb.Notebook;
import com.jca.thedoor.repository.mongodb.CoworkerRepository;
import com.jca.thedoor.repository.mongodb.NotebookRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;

import java.util.Optional;

public class CoworkerValidation extends Validator {
    private Coworker _coworker;
    private UserRepository _userRepository;
    private NotebookRepository _notebookRepository;
    private CoworkerRepository _coworkerRepository;

    public CoworkerValidation(Coworker coworker, UserRepository userRepository,
                              CoworkerRepository coworkerRepository, NotebookRepository notebookRepository) {
        this._coworker = coworker;
        this._userRepository = userRepository;
        this._notebookRepository = notebookRepository;
        this._coworkerRepository = coworkerRepository;
    }

    public void validateToInsert() {
        String coworkerUser = _coworker.getUser();
        // String notebook = _coworker.getNotebook();
        validateUser(_userRepository, coworkerUser);
        // validateNotebook(coworkerUser, notebook);
    }

    private void validateNotebook(String coworkerUser, String notebook) {
        validateId(notebook);
        Optional<Notebook> notebookOptional = _notebookRepository.findById(notebook);
        if (notebookOptional.isPresent()) {
            Notebook foundNotebook = notebookOptional.get();
            if (!foundNotebook.getUser().equals(coworkerUser)) {
                throw new IllegalArgumentException("The notebook doesn't belong to the user");
            }
        } else {
            throw new IllegalArgumentException("Unable to create Coworker. Notebook not found: " + notebook);
        }
    }
}
