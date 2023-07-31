package com.jca.thedoor.controllers;

import com.jca.thedoor.controllers.validators.CoworkerValidation;
import com.jca.thedoor.entity.mongodb.Coworker;
import com.jca.thedoor.repository.mongodb.CoworkerRepository;
import com.jca.thedoor.repository.mongodb.NotebookRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.service.impl.CoworkerMongoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/coworker")
public class CoworkerController {
    private final CoworkerMongoService _coworkerMongoService;

    private final UserRepository _userRepository;
    private final NotebookRepository _notebookRepository;
    private final CoworkerRepository _coworkerRepository;


    public CoworkerController(CoworkerMongoService coworkerMongoService, CoworkerRepository coworkerRepository,
                              UserRepository userRepository, NotebookRepository notebookRepository) {
        this._coworkerMongoService = coworkerMongoService;
        this._userRepository = userRepository;
        this._coworkerRepository = coworkerRepository;
        this._notebookRepository = notebookRepository;
    }

    @PostMapping("/coworker")
    public ResponseEntity<Coworker> saveCoworker(@Valid @RequestBody Coworker coworker) {
        CoworkerValidation validation = new CoworkerValidation(coworker, _userRepository,
                _coworkerRepository, _notebookRepository);
        validation.validateToInsert();
        return _coworkerMongoService.createCoworker(coworker);
    }
}
