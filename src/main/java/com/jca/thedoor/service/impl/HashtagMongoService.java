package com.jca.thedoor.service.impl;

import com.jca.thedoor.entity.mongodb.Hashtag;
import com.jca.thedoor.exception.FieldAlreadyExistsException;
import com.jca.thedoor.exception.ServerException;
import com.jca.thedoor.exception.UtilException;
import com.jca.thedoor.repository.mongodb.HashtagRepository;
import com.jca.thedoor.service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashtagMongoService implements HashtagService {
    @Autowired
    private HashtagRepository _hashTagRepository;

    @Override
    public ResponseEntity<Hashtag> saveHashtag(Hashtag hashTag) {
        try {
            Hashtag saved = _hashTagRepository.save(hashTag);
            return ResponseEntity.ok(saved);
        } catch (DuplicateKeyException e) {
            throw new FieldAlreadyExistsException("DuplicateKeyException: " + UtilException.EXTRACT_DUPLICATE_MESSAGE_FROM_EXCEPTION(e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<List<Hashtag>> findAllHashtagsByGroup(String user, String group) {
        try {
            List<Hashtag> result = _hashTagRepository.findByUserAndGroup(user, group);
            for (Hashtag hashtag: result) {
                hashtag.assignStringId();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }


    @Override
    public void deleteAllByNameAndUserAndGroup(String names[], String user, String group) {
        for (String name: names) {
            _hashTagRepository.deleteAllByNameAndUserAndGroup(name, user, group);
        }
    }
}
