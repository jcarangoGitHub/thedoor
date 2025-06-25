package com.jca.thedoor.controllers;

import com.jca.thedoor.entity.mongodb.Coworker;
import com.jca.thedoor.entity.mongodb.Thought;
import com.jca.thedoor.service.impl.ThoughtMongoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/thought")
public class ThoughtController {
    private final ThoughtMongoService _thoughtMongoService;

    public ThoughtController(ThoughtMongoService thoughtMongoService) {
        _thoughtMongoService = thoughtMongoService;
    }

    @PostMapping("/thought")
    public ResponseEntity<Thought> saveThought(@Valid @RequestBody Thought thought) {
        return _thoughtMongoService.createThought(thought);
    }

    @PostMapping("/findById")
    public ResponseEntity<Thought> findById(@Valid @RequestBody String thoughtId) {
        thoughtId = thoughtId.replace("\"", "");
        return _thoughtMongoService.findById(thoughtId);
    }

    @PostMapping("/findByNotebook")
    public ResponseEntity<List<Thought>> findByNotebook(@Valid @RequestBody Thought thought) {
        return _thoughtMongoService.findThoughtsByNotebook(thought.getUser(), thought.getNotebook());
    }
}
