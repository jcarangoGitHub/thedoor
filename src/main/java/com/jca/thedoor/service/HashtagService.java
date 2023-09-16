package com.jca.thedoor.service;

import com.jca.thedoor.entity.mongodb.Hashtag;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HashtagService {
    ResponseEntity<Hashtag> saveHashtag(Hashtag hashTag);

    ResponseEntity<List<Hashtag>> findAllHashtagsByGroup(String user, String group);

    void deleteAllByNameAndUserAndGroup(String names[], String user, String group);
}
