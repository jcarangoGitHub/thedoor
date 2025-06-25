package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.Coworker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoworkerRepository extends MongoRepository<Coworker, String> {
    List<Coworker> findByUserAndGroup(String user, String group);

    List<Coworker> findByUserAndGroupAndReviewerIsTrue(String user, String group);

    void deleteAllByNameAndUserAndGroup(String name, String user, String group);
}
