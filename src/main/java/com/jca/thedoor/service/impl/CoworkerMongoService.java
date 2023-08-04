package com.jca.thedoor.service.impl;

import com.jca.thedoor.entity.mongodb.Coworker;
import com.jca.thedoor.entity.mongodb.Notebook;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.ServerException;
import com.jca.thedoor.repository.mongodb.CoworkerRepository;
import com.jca.thedoor.service.CoworkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new FieldAlreadyExistsException("DuplicateKeyException: " + extractDuplicateExceptionMessage(e.getMessage()));
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
    public void deleteAllByNameAndUserAndGroup(String names[], String user, String group) {
        for (String name: names) {
            coworkerRepository.deleteAllByNameAndUserAndGroup(name, user, group);
        }
    }

    private static String extractDuplicateExceptionMessage(String exceptionMessage) {
        String startText = "index: ";
        int startIndex = exceptionMessage.indexOf(startText);

        if (startIndex != -1) {
            int endIndex = exceptionMessage.indexOf("\" }", startIndex + startText.length());
            if (endIndex != -1) {
                String keyPart = exceptionMessage.substring(startIndex, endIndex + 3);
                return keyPart;
            }
        }

        return null; // Return null if the desired part is not found in the exception message.
    }


}
