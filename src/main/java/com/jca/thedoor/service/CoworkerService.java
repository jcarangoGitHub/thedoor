package com.jca.thedoor.service;

import com.jca.thedoor.entity.mongodb.Coworker;
import com.jca.thedoor.entity.mongodb.Notebook;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CoworkerService {
    ResponseEntity<Coworker> createCoworker(Coworker coworker);

    ResponseEntity<List<Coworker>> findAllCoworkersByGroup(Notebook notebook);

    void deleteAllByNameAndUserAndGroup(String names[], String user, String group);
}
