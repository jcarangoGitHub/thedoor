package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.Hashtag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HashtagRepository extends MongoRepository<Hashtag, String> {

    List<Hashtag> findByUserAndGroup(String user, String group);
    void deleteAllByNameAndUserAndGroup(String name, String user, String group);
}
