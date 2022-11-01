package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

}
