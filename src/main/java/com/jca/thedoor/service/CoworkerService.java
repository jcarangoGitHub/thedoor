package com.jca.thedoor.service;

import com.jca.thedoor.entity.mongodb.Coworker;
import com.jca.thedoor.entity.mongodb.Notebook;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CoworkerService {
    ResponseEntity<Coworker> createCoworker(Coworker coworker);

    ResponseEntity<Coworker> findById(String id);

    ResponseEntity<List<Coworker>> findAllCoworkersByGroup(Notebook notebook);

    ResponseEntity<List<Coworker>> findReviewersByGroup(Notebook notebook);

    void deleteAllByNameAndUserAndGroup(String names[], String user, String group);
}
