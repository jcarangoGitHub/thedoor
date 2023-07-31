package com.jca.thedoor.service;

import com.jca.thedoor.entity.mongodb.Coworker;
import org.springframework.http.ResponseEntity;

public interface CoworkerService {
    ResponseEntity<Coworker> createCoworker(Coworker coworker);
}
