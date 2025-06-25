package com.jca.thedoor.service.impl;

import com.jca.thedoor.entity.mongodb.Coworker;
import com.jca.thedoor.entity.mongodb.Notebook;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.NotFoundException;
import com.jca.thedoor.exception.ServerException;
import com.jca.thedoor.exception.UtilException;
import com.jca.thedoor.repository.mongodb.CoworkerRepository;
import com.jca.thedoor.service.CoworkerService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CoworkerMongoService implements CoworkerService {
    @Autowired
    private CoworkerRepository coworkerRepository;
    @Override
    public ResponseEntity<Coworker> createCoworker(Coworker coworker) {
        try {
            Coworker created = coworkerRepository.save(coworker);
            return ResponseEntity.ok(created);
        } catch (DuplicateKeyException e) {
            throw new FieldAlreadyExistsException("DuplicateKeyException: " + UtilException.EXTRACT_DUPLICATE_MESSAGE_FROM_EXCEPTION((e.getMessage())));
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Coworker> findById(String id) {
        try {
            Optional<Coworker> optional = coworkerRepository.findById(id);
            Coworker coworkerFound = optional.get();
            coworkerFound.assignStringId();
            return ResponseEntity.ok(coworkerFound);
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Coworker with id " + id + " doesn't exist");
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Coworker>> findAllCoworkersByGroup(Notebook notebook) {
        try {
            List<Coworker> results = coworkerRepository.findByUserAndGroup(notebook.getUser(), notebook.getGroup());
            for (Coworker coworker: results) {
                coworker.assignStringId();
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Coworker>> findReviewersByGroup(Notebook notebook) {
        try {
            List<Coworker> results = coworkerRepository.findByUserAndGroupAndReviewerIsTrue(notebook.getUser(), notebook.getGroup());
            for (Coworker coworker: results) {
                coworker.assignStringId();
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    @Override
    public void deleteAllByNameAndUserAndGroup(String names[], String user, String group) {
        for (String name: names) {
            coworkerRepository.deleteAllByNameAndUserAndGroup(name, user, group);
        }
    }


}
