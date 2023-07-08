package com.jca.thedoor.controllers;

import com.jca.thedoor.controllers.validators.NotebookValidation;
import com.jca.thedoor.entity.mongodb.Notebook;
import com.jca.thedoor.repository.mongodb.NotebookRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.service.impl.NotebookMongoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notebook")
public class NotebookController {
    private final NotebookMongoService _notebookMongoService;
    private final UserRepository _userRepository;
    private final NotebookRepository _notebookRepository;

    public NotebookController(NotebookMongoService notebookMongoService, UserRepository userRepository,
                              NotebookRepository notebookRepository) {
        this._notebookMongoService = notebookMongoService;
        _userRepository = userRepository;
        _notebookRepository = notebookRepository;
    }

    @PostMapping("/notebook")
    public ResponseEntity<Notebook> createNotebook(@Valid @RequestBody Notebook notebook) {
        NotebookValidation validation = new NotebookValidation(notebook, _userRepository, _notebookRepository);
        validation.validateToInsert();
        return _notebookMongoService.createNotebook(notebook);
    }

    @PostMapping("/findAllByUser")
    public ResponseEntity<List<Notebook>> findAllByUser(@Valid @RequestBody String idUser) {
        idUser = idUser.replace("\"", "");
        NotebookValidation.validateId(idUser);
        return _notebookMongoService.findAllNotebooksByUser(idUser);
    }
}