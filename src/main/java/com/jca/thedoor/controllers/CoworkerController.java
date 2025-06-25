package com.jca.thedoor.controllers;

import com.jca.thedoor.controllers.validators.CoworkerValidation;
import com.jca.thedoor.entity.mongodb.Coworker;
import com.jca.thedoor.entity.mongodb.Notebook;
import com.jca.thedoor.payload.DeleteObjectsRequest;
import com.jca.thedoor.repository.mongodb.CoworkerRepository;
import com.jca.thedoor.repository.mongodb.NotebookRepository;
import com.jca.thedoor.repository.mongodb.UserRepository;
import com.jca.thedoor.service.impl.CoworkerMongoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/findById")
    public ResponseEntity<Coworker> findById(@Valid @RequestBody String coworkerId) {
        coworkerId = coworkerId.replace("\"", "");
        return _coworkerMongoService.findById(coworkerId);
    }

    @PostMapping("/findAll")
    public ResponseEntity<List<Coworker>> findByGroup(@Valid @RequestBody Notebook notebook) {
        return _coworkerMongoService.findAllCoworkersByGroup(notebook);
    }

    @PostMapping("/findReviewers")
    public ResponseEntity<List<Coworker>> findReviewersByGroup(@Valid @RequestBody Notebook notebook) {
        return _coworkerMongoService.findReviewersByGroup(notebook);
    }

    @DeleteMapping("coworker")
    public ResponseEntity<Integer> deleteByNamesAndUserAndGroup(@Valid @RequestBody DeleteObjectsRequest request) {
        _coworkerMongoService.deleteAllByNameAndUserAndGroup(request.getObjects(), request.getUser(), request.getGroup());
        return ResponseEntity.ok(request.getObjects().length);
    }
}
