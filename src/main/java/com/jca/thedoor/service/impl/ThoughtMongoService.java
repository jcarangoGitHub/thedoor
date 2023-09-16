package com.jca.thedoor.service.impl;

import com.jca.thedoor.entity.mongodb.Coworker;
import com.jca.thedoor.entity.mongodb.Hashtag;
import com.jca.thedoor.entity.mongodb.ObjectSummarized;
import com.jca.thedoor.entity.mongodb.Thought;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.ServerException;
import com.jca.thedoor.exception.UtilException;
import com.jca.thedoor.repository.mongodb.ThoughtRepository;
import com.jca.thedoor.service.ThoughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThoughtMongoService implements ThoughtService {

    @Autowired
    private ThoughtRepository _thoughtRepository;

    @Override
    public ResponseEntity<Thought> createThought(Thought thought) {
        try {
            Thought created = _thoughtRepository.save(thought);
            return ResponseEntity.ok(created);
        } catch (DuplicateKeyException e) {
            throw new FieldAlreadyExistsException("DuplicateKeyException: " + UtilException.EXTRACT_DUPLICATE_MESSAGE_FROM_EXCEPTION(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<List<Thought>> findThoughtsByNotebook(String user, String notebook) {
        try {
            List<Thought> result = _thoughtRepository.findByUserAndNotebook(user, notebook);
            for (Thought thought: result) {
                thought.assignStringId();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }
}
