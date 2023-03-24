package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AuthenticationRepository extends MongoRepository<Authentication, String> {
    Authentication findByUsername(String username);
}
