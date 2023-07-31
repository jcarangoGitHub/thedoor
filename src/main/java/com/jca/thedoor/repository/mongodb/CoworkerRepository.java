package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.Coworker;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoworkerRepository extends MongoRepository<Coworker, String> {

}
